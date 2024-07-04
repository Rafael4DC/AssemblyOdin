import {useEffect, useState} from 'react';
import {TechService} from "../../services/tech/TechService";
import {Failure, Success} from "../../services/_utils/Either";
import {Tech} from "../../services/tech/models/Tech";
import {handleError} from "../../utils/Utils";

/**
 * Hook to get the tech classes
 *
 * @returns the tech classes, error and handles to save and delete a tech class
 */
const useMyTechs = () => {
    const [state, setState] = useState<MyTechsState>({type: 'loading'});

    useEffect(() => {
        TechService.getTechsByUser()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', techs: data.value.techs})
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            });
    }, []);

    return {
        state
    };
};

type MyTechsState =
    | { type: 'loading' }
    | { type: 'success'; techs: Tech[] }
    | { type: 'error'; message: string };


export default useMyTechs;