import {useEffect, useState} from "react";
import {Section} from "../services/section/models/Section";
import {SectionService} from "../services/section/SectionService";
import {Failure, Success} from "../services/_utils/Either";

const useSections = () => {
    const [sections, setSections] = useState<Section[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        SectionService.getAll()
            .then(data => {
                if (data instanceof Success) {
                    setSections(data.value.sections);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {sections, error};
};

export default useSections;