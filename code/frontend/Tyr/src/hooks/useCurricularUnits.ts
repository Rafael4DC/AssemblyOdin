import { useEffect, useState } from 'react';
import {CurricularUnitService} from "../services/CurricularUnitService";
import {CurricularUnit} from "../model/CurricularUnit";


const useCurricularUnits = () => {
    const [curricularUnits, setCurricularUnits] = useState<CurricularUnit[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        CurricularUnitService.getAll()
            .then(data => {
                setCurricularUnits(data);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {
        curricularUnits,
        error,
        handleSaveCurricularUnit: async (curricularUnit: CurricularUnit) => {
            setError(null);
            try {
                if (curricularUnit.id) {
                    return await CurricularUnitService.update(curricularUnit);
                } else {
                    return await CurricularUnitService.save(curricularUnit);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteCurricularUnit: async (id: number) => {
            setError(null);
            try {
                await CurricularUnitService.delete(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useCurricularUnits;