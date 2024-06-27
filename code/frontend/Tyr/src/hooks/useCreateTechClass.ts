import * as React from 'react';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Success } from '../services/_utils/Either';
import useSections from "./useSections";
import { TechService } from "../services/tech/TechService";
import { WebUris } from "../utils/WebUris";

type CreateTechClassState =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };

const useCreateTechClass = () => {
    const navigate = useNavigate();
    const [state, setState] = useState<CreateTechClassState>({ type: 'loading' });
    const [isMultiple, setIsMultiple] = useState(false);

    const [techData, setTechData] = useState({
        section: { id: 1 },
        started: '',
        ended: '',
        summary: '',
        startDate: '',
        endDate: '',
        classTime: '',
        classLengthHours: '',
        dayOfWeek: ''
    });

    const { sections, error } = useSections();

    useEffect(() => {
        if (state.type === 'loading' && sections !== null) {
            setState({ type: 'success', message: '' });
        } else if (error) {
            setState({ type: 'error', message: handleError(error) });
        }
    }, [error, state.type, sections]);

    const handleError = (error: any) => {
        if (error instanceof Error) {
            return error.message;
        } else {
            return 'An unexpected error occurred';
        }
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setState({ type: 'loading' });
        debugger;
        try {
            const res = isMultiple ?
                await TechService.saveMultiple({
                    section: techData.section.id,
                    startDate: techData.startDate,
                    endDate: techData.endDate,
                    classTime: techData.classTime,
                    classLengthHours: Number(techData.classLengthHours),
                    dayOfWeek: techData.dayOfWeek.toUpperCase(),
                }) :
                await TechService.save({
                    section: techData.section.id,
                    started: techData.started,
                    ended: techData.ended,
                    summary: techData.summary,
                });
            if (res instanceof Success) {
                navigate(WebUris.MANAGE_CLASS);
            } else {
                const errorMessage = handleError(res);
                setState({ type: 'error', message: errorMessage });
            }
        } catch (error) {
            setState({ type: 'error', message: handleError(error) });
        }
    };

    const handleSectionChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setTechData(prevTechData => ({
            ...prevTechData,
            section: { id: Number(e.target.value) }
        }));
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setTechData(prevTechData => ({
            ...prevTechData,
            [name]: value
        }));
    };

    const handleMultiChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setTechData(prevTechData => ({
            ...prevTechData,
            [name]: value
        }));
    };

    const toggleMultiple = () => {
        setIsMultiple(!isMultiple);
    };

    return {
        techData,
        setTechData,
        state,
        sections,
        handleSubmit,
        handleSectionChange,
        handleChange,
        handleMultiChange,
        isMultiple,
        toggleMultiple
    };
};

export default useCreateTechClass;
