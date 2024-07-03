import * as React from 'react';
import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Success} from '../../services/_utils/Either';
import useSections from "../Section/useSections";
import {TechService} from "../../services/tech/TechService";
import {WebUris} from "../../utils/WebUris";

type CreateTechClassState =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };

const useCreateTech = () => {
    const navigate = useNavigate();
    const [state, setState] = useState<CreateTechClassState>({type: 'loading'});
    const [isMultiple, setIsMultiple] = useState(false);

    const [techData, setTechData] = useState({
        section: {id: 0},
        started: '',
        ended: '',
        summary: '',
        startDate: '',
        endDate: '',
        classTime: '',
        classLengthHours: '',
        dayOfWeek: ''
    });

    const {sections, error} = useSections();

    useEffect(() => {
        if (state.type === 'loading' && sections !== null) {
            setState({type: 'success', message: ''});
        } else if (error) {
            setState({type: 'error', message: handleError(error)});
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
        setState({type: 'loading'});
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
                navigate(WebUris.TIMETABLE);
            } else {
                const errorMessage = handleError(res);
                setState({type: 'error', message: errorMessage});
            }
        } catch (error) {
            setState({type: 'error', message: handleError(error)});
        }
    };

    const handleSectionChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setTechData(prevTechData => ({
            ...prevTechData,
            section: {id: Number(e.target.value)}
        }));
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target;
        setTechData(prevTechData => ({
            ...prevTechData,
            [name]: value
        }));
    };

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const date = e.target.value;
        setTechData(prevTechData => ({
            ...prevTechData,
            started: `${date}T${prevTechData.started.split('T')[1]}`,
            ended: `${date}T${prevTechData.ended.split('T')[1]}`,
        }));
    };

    const handleTimeChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: "started" | "ended") => {
        const time = e.target.value;
        setTechData(prevTechData => ({
            ...prevTechData,
            [key]: `${prevTechData[key].split('T')[0]}T${time}`,
        }));
    };

    const handleMultiChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target;
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
        handleDateChange,
        handleTimeChange,
        isMultiple,
        toggleMultiple
    };
};

export default useCreateTech;
