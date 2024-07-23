import * as React from 'react';
import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Success} from '../../services/_utils/Either';
import useSections from "../Section/useSections";
import {TechService} from "../../services/tech/TechService";
import {WebUris} from "../../utils/WebUris";
import {initTech, techToInput, techToTechMultiInput} from "../../services/tech/models/Tech";
import useTechForm from "./useTechForm";
import {Section} from "../../services/section/models/Section";
import {handleError} from "../../utils/Utils";
import TIMETABLE = WebUris.TIMETABLE;

/**
 * State for the creation tech
 */
type CreateTechClassState =
    | { type: 'loading' }
    | { type: 'success'; sections: Section[]; loading: boolean }
    | { type: 'error'; message: string };

/**
 * Hook to create a tech
 *
 * @returns the state and functions to create a tech
 */
const useCreateTech = () => {
    const navigate = useNavigate();

    const [state, setState] = useState<CreateTechClassState>({type: 'loading'});
    const [isMultiple, setIsMultiple] = useState(false);
    const {
        selectedTech,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange
    } = useTechForm(initTech());

    const {state: sectionsState} = useSections();

    useEffect(() => {
        switch (true) {
            case sectionsState.type === 'success':
                setState({type: 'success', sections: sectionsState.sections, loading: false});
                break;
            case sectionsState.type === 'error':
                setState({type: 'error', message: sectionsState.message});
                break;
        }
    }, [sectionsState]);

    const toggleMultiple = () => {
        setIsMultiple(!isMultiple);
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setState(prevState => ({...prevState, loading: true}));
        try {
            debugger;
            const data = isMultiple ?
                await TechService.saveMultiple(techToTechMultiInput(selectedTech)) :
                await TechService.save(techToInput(selectedTech));
            if (data instanceof Success) {
                navigate(TIMETABLE);
            } else {
                setState({type: 'error', message: handleError(data.value)});
            }
        } catch (err) {
            setState({type: 'error', message: err.message || err});
        }
    };

    return {
        state,
        selectedTech,
        handleSubmit,
        handleSectionChange,
        handleInputChange,
        handleDateChange,
        handleTimeChange,
        isMultiple,
        toggleMultiple
    };
};

export default useCreateTech;
