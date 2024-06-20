import {useEffect, useState} from "react";
import {Section} from "../services/section/models/Section";
import {SectionService} from "../services/section/SectionService";
import {Module} from "../services/module/models/Module";
import {ModuleService} from "../services/module/ModuleService";

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