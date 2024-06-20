import {useEffect, useState} from "react";
import {Section} from "../services/section/models/Section";
import {SectionService} from "../services/section/SectionService";

const useSections = () => {
    const [sections, setSections] = useState<Section[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        SectionService.getAll()
            .then(data => {
                setSections(data.sections);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {sections, error};
};

export default useSections;