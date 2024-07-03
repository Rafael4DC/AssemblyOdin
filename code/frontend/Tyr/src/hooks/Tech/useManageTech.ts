import {ChangeEvent, useEffect, useState} from 'react';
import {TechService} from '../../services/tech/TechService';
import useSections from '../Section/useSections';
import {Failure, Success} from '../../services/_utils/Either';
import {Tech} from "../../services/tech/models/Tech";
import {addWeeks, endOfWeek, isAfter, isBefore, isWithinInterval, parseISO, startOfWeek, subWeeks} from 'date-fns';

type ManageTechState =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };

const useManageTech = () => {
    const [state, setState] = useState<ManageTechState>({type: 'loading'});
    const [techs, setTechs] = useState<Tech[]>([]);
    const [filteredTechs, setFilteredTechs] = useState<Tech[]>([]);
    const [selectedTech, setSelectedTech] = useState<Tech | null>(null);
    const [techData, setTechData] = useState({
        summary: '',
        started: '',
        ended: '',
        section: {id: 0}
    });
    const [filter, setFilter] = useState('all');
    const [sectionFilter, setSectionFilter] = useState('all');
    const {sections, error: sectionsError} = useSections();

    useEffect(() => {
        const fetchTechs = async () => {
            try {
                const techs = await TechService.getAll();
                if (techs instanceof Success) {
                    setTechs(techs.value.techs);
                } else if (techs instanceof Failure) {
                    console.error('Error fetching data:', techs.value);
                }
                setState({type: 'success', message: ''});
            } catch (error) {
                setState({type: 'error', message: handleError(error)});
            }
        };

        fetchTechs();
    }, []);

    useEffect(() => {
        if (sectionsError) {
            setState({type: 'error', message: handleError(sectionsError)});
        }
    }, [sectionsError]);

    useEffect(() => {
        filterTechs();
    }, [techs, filter, sectionFilter]);

    const filterTechs = () => {
        let result = techs;

        if (sectionFilter !== 'all') {
            result = result.filter(tech => tech.section?.id === parseInt(sectionFilter));
        }

        const now = new Date();
        const nextWeek = addWeeks(now, 1);
        const pastWeek = subWeeks(now, 1);
        const startOfThisWeek = startOfWeek(now);
        const endOfThisWeek = endOfWeek(now);

        if (filter === 'past') {
            result = result.filter(tech => isBefore(parseISO(tech.started), now));
        } else if (filter === 'future') {
            result = result.filter(tech => isAfter(parseISO(tech.started), now));
        } else if (filter === 'nextWeek') {
            result = result.filter(tech => isWithinInterval(parseISO(tech.started), {start: now, end: nextWeek}));
        } else if (filter === 'pastWeek') {
            result = result.filter(tech => isWithinInterval(parseISO(tech.started), {start: pastWeek, end: now}));
        }

        setFilteredTechs(result);
    };

    const handleError = (error: any) => {
        if (error instanceof Error) {
            return error.message;
        } else {
            return 'An unexpected error occurred';
        }
    };

    const handleTechClick = (tech: Tech) => {
        setSelectedTech(tech);
        setTechData({
            summary: tech.summary,
            started: tech.started,
            ended: tech.ended,
            section: {id: tech.section?.id ?? 0}
        });
    };

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setTechData((prevTechData) => ({
            ...prevTechData,
            [name]: value,
        }));
    };

    const handleSectionChange = (e: ChangeEvent<HTMLInputElement>) => {
        setTechData((prevTechData) => ({
            ...prevTechData,
            section: {id: Number(e.target.value)},
        }));
    };

    const handleDateChange = (e: ChangeEvent<HTMLInputElement>) => {
        const date = e.target.value;
        setTechData((prevTechData) => ({
            ...prevTechData,
            started: `${date}T${prevTechData.started.split('T')[1]}`,
            ended: `${date}T${prevTechData.ended.split('T')[1]}`,
        }));
    };

    const handleTimeChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: 'started' | 'ended') => {
        const time = e.target.value;
        setTechData((prevTechData) => ({
            ...prevTechData,
            [key]: `${prevTechData[key].split('T')[0]}T${time}`,
        }));
    };

    const handleSubmit = async () => {
        setState({type: 'loading'});
        try {
            await TechService.update({
                id: selectedTech!.id!,
                summary: techData.summary,
                started: techData.started,
                ended: techData.ended,
                section: techData.section.id,
            });
            const updatedTechs = await TechService.getAll();
            if (updatedTechs instanceof Success) {
                setTechs(updatedTechs.value.techs);
            } else if (updatedTechs instanceof Failure) {
                console.error('Error fetching data:', updatedTechs.value);
            }
            setSelectedTech(null);
            setState({type: 'success', message: ''});
        } catch (error) {
            setState({type: 'error', message: handleError(error)});
        }
    };

    const handleClose = () => {
        setSelectedTech(null);
    };

    const handleDeleteTech = async (id: number) => {
        setState({type: 'loading'});
        try {
            await TechService.deleteById(id);
            const updatedTechs = await TechService.getAll();
            if (updatedTechs instanceof Success) {
                setTechs(updatedTechs.value.techs);
            } else if (updatedTechs instanceof Failure) {
                console.error('Error fetching data:', updatedTechs.value);
            }
            setState({type: 'success', message: ''});
        } catch (error) {
            setState({type: 'error', message: handleError(error)});
        }
    };

    return {
        techs,
        sections,
        state,
        techData,
        selectedTech,
        handleTechClick,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        filter,
        setFilter,
        sectionFilter,
        setSectionFilter,
        filteredTechs,
        handleSubmit,
        handleClose,
        handleDeleteTech
    };
};

export default useManageTech;
