import {useEffect, useState} from 'react';
import {TechService} from '../services/TechService';
import {Tech} from "../model/Tech";

/**
 * Hook to get the tech classes
 *
 * @returns the tech classes, error and handles to save and delete a tech class
 */
const useTechs = () => {
    const [techs, setTechs] = useState<Tech[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        TechService.getAll()
            .then(data => {
                setTechs(data);
            })
            .catch(err => {
                setError(err);
            });
    }, []);


    return {
        techs,
        error,
        handleSaveTech: async (tech: Tech) => {
            setError(null);
            try {
                if (tech.id) {
                    return await TechService.update({
                        id: tech.id,
                        section: tech.section?.id,
                        date: tech.date,
                        summary: tech.summary,
                        missTech: tech.missTech?.map(student => student.id)

                    });
                } else {
                    return await TechService.save({
                        section: tech.section?.id,
                        date: tech.date,
                        summary: tech.summary,
                        missTech: tech.missTech?.map(student => student.id)
                    });
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteTech: async (id: number) => {
            setError(null);
            try {
                await TechService.delete(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useTechs;