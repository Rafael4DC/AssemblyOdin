import {useEffect, useState} from 'react';
import {TechService} from "../services/tech/TechService";
import {Failure, Success} from "../services/_utils/Either";
import {Tech} from "../services/tech/models/Tech";

/**
 * Hook to get the tech classes
 *
 * @returns the tech classes, error and handles to save and delete a tech class
 */
const useMyTechs = () => {
    const [techs, setTechs] = useState<Tech[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        TechService.getTechsByUser()
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
        error
    };
};

export default useMyTechs;