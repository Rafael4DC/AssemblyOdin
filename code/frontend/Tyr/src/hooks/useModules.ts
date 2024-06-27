import {useEffect, useState} from "react";
import {Module} from "../services/module/models/Module";
import {ModuleService} from "../services/module/ModuleService";
import {Failure, Success} from "../services/_utils/Either";

const useModules = () => {
    const [modules, setModules] = useState<Module[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        ModuleService.getAll()
            .then(data => {
                if (data instanceof Success) {
                    setModules(data.value.modules);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return { modules, error };
};

export default useModules;