import {useEffect, useState} from "react";
import {Section} from "../../services/section/models/Section";
import {SectionService} from "../../services/section/SectionService";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";

/**
 * State for the sections
 */
type SectionsState =
    | { type: 'loading' }
    | { type: 'success'; sections: Section[] }
    | { type: 'error'; message: string };

/**
 * Hook to get the sections
 *
 * @returns the state with the sections
 */
const useSections = () => {
    const [state, setState] = useState<SectionsState>({type: 'loading'});

    const getSections = async () => {
        SectionService.getAll()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', sections: data.value.sections})
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            });
    }

    useEffect(() => {
        getSections()
    }, []);

    return {
        state,
        getSections
    };
};

export default useSections;