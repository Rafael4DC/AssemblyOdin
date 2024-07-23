import {useEffect, useState} from "react";
import {VocService} from "../../services/voc/VocService";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";
import {Voc} from "../../services/voc/models/Voc";

/**
 * State for the vocs
 */
type VocsState =
    | { type: 'loading' }
    | { type: 'success'; vocs: Voc[] }
    | { type: 'error'; message: string };

/**
 * Hook to get the vocs
 *
 * @returns the state with the vocs
 */
const useVocs = () => {
    const [state, setState] = useState<VocsState>({type: 'loading'});

    const getVocs = async () => {
        VocService.getAll()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', vocs: data.value.vocs})
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            });
    };

    useEffect(() => {
        getVocs();
    }, []);

    return {
        state,
        setState,
        getVocs
    };
};

export default useVocs;
