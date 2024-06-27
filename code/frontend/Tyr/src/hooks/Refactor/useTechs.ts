import {useEffect, useState} from 'react';
import {TechService} from '../../services/tech/TechService';
import {Tech} from "../../services/tech/models/Tech";
import {Failure, Success} from "../../services/_utils/Either";

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
                if (data instanceof Success) {
                    setTechs(data.value.techs);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
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
                        started: tech.started,
                        ended: tech.ended,
                        summary: tech.summary,
                        missTech: tech.missTech?.map(student => student.id)

                    });
                } else {
                    return await TechService.save({
                        section: tech.section?.id,
                        started: tech.started,
                        ended: tech.ended,
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
                await TechService.deleteById(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useTechs;