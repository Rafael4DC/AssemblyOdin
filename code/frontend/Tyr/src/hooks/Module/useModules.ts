import {useEffect, useState} from "react";
import {Module} from "../../services/module/models/Module";
import {ModuleService} from "../../services/module/ModuleService";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";

/**
 * State for the modules
 */
type ModulesState =
    | { type: 'loading' }
    | { type: 'success'; modules: Module[] }
    | { type: 'error'; message: string };

/**
 * Hook to get the modules
 *
 * @returns the sate with the modules
 */
const useModules = () => {
    const [state, setState] = useState<ModulesState>({type: 'loading'});

    useEffect(() => {
        ModuleService.getAll()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', modules: data.value.modules})
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

export default useModules;