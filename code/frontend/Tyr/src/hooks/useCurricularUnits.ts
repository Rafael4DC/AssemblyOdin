import { useEffect, useState } from 'react';
import {CurricularUnitService} from "../services/CurricularUnitService";


const useCurricularUnits = () => {
    const [curricularUnits, setCurricularUnits] = useState<Course[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        CurricularUnitService.getAll()
            .then(data => {
                setCurricularUnits(data);
                setIsLoading(false);
            })
            .catch(err => {
                setError(err);
                setIsLoading(false);
            });
    }, []);

    return { courses: curricularUnits, isLoading, error };
};

export default useCurricularUnits;