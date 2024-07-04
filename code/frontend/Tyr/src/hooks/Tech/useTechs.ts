import {useEffect, useState} from "react";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";
import {Tech} from "../../services/tech/models/Tech";
import {TechService} from "../../services/tech/TechService";

type TechsState =
    | { type: 'loading' }
    | { type: 'success'; techs: Tech[] }
    | { type: 'error'; message: string };

const useTechs = () => {
    const [state, setState] = useState<TechsState>({type: 'loading'});

    const getTechs = async () => {
        TechService.getAll()
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
    };

    useEffect(() => {
        getTechs();
    }, []);

    return {
        state,
        setState,
        getTechs
    };
};

export default useTechs;
