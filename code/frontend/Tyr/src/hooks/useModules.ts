import {useEffect, useState} from "react";
import {Section} from "../model/Section";
import {SectionService} from "../services/SectionService";
import {Module} from "../model/Module";
import {ModuleService} from "../services/ModuleService";

const useModules = () => {
    const [modules, setModules] = useState<Module[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        ModuleService.getAll()
            .then(data => {
                setModules(data.modules);
            })
            .catch(err => {
                setError(err);
            });
    }, []);



    return {modules, error};
};

export default useModules;