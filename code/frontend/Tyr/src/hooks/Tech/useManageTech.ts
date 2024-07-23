import * as React from 'react';
import {useEffect, useState} from 'react';
import {TechService} from '../../services/tech/TechService';
import useSections from '../Section/useSections';
import {Failure, Success} from '../../services/_utils/Either';
import {Tech, techToInput} from "../../services/tech/models/Tech";
import {addWeeks, isAfter, isBefore, isWithinInterval, parseISO, subWeeks} from 'date-fns';
import useTechForm from "./useTechForm";
import useTechs from "./useTechs";
import {Section} from "../../services/section/models/Section";
import {handleError} from "../../utils/Utils";

/**
 * State for the manage tech
 */
type ManageTechState =
    | { type: 'loading' }
    | { type: 'success'; filteredTechs: Tech[]; sections: Section[]; loading: boolean }
    | { type: 'error'; message: string };

/**
 * Hook to manage tech
 *
 * @returns the state and functions to manage tech
 */
const useManageTech = () => {
    const [state, setState] = useState<ManageTechState>({type: 'loading'});
    const [filter, setFilter] = useState('all');
    const [sectionFilter, setSectionFilter] = useState('all');
    const {
        selectedTech,
        setSelectedTech,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange
    } = useTechForm();

    const {state: sectionsState} = useSections();
    const {state: techsState, getTechs} = useTechs()

    useEffect(() => {
        switch (true) {
            case techsState.type === 'success' && sectionsState.type === 'success':
                setState({
                    type: 'success',
                    filteredTechs: filterTechs(techsState.techs),
                    sections: sectionsState.sections,
                    loading: false
                });
                break;
            case techsState.type === 'error':
                setState({type: 'error', message: techsState.message});
                break;
            case sectionsState.type === 'error':
                setState({type: 'error', message: sectionsState.message});
                break;
        }
    }, [techsState, sectionsState, filter, sectionFilter]);

    const filterTechs = (techs: Tech[]) => {
        let result = techs;

        if (sectionFilter !== 'all') {
            result = result.filter(tech => tech.section?.id === parseInt(sectionFilter));
        }

        const now = new Date();
        const nextWeek = addWeeks(now, 1);
        const pastWeek = subWeeks(now, 1);

        if (filter === 'past') {
            result = result.filter(tech => isBefore(parseISO(tech.started), now));
        } else if (filter === 'future') {
            result = result.filter(tech => isAfter(parseISO(tech.started), now));
        } else if (filter === 'nextWeek') {
            result = result.filter(tech => isWithinInterval(parseISO(tech.started), {start: now, end: nextWeek}));
        } else if (filter === 'pastWeek') {
            result = result.filter(tech => isWithinInterval(parseISO(tech.started), {start: pastWeek, end: now}));
        }

        return result;
    };

    const handleTechClick = (tech: Tech) => {
        setSelectedTech(tech);
    };

    const handleClose = () => {
        setSelectedTech(null);
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setState(prevState => ({...prevState, loading: true}));
        TechService.update(techToInput(selectedTech))
            .then(async data => {
                if (data instanceof Success) {
                    getTechs()
                        .then(() => {
                                setSelectedTech(null);
                            }
                        )
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: handleError(err)});
            });
    };

    const handleDeleteTech = async (id: number) => {
        setState(prevState => ({...prevState, loading: true}));
        TechService.deleteById(id)
            .then(async () => {
                getTechs()
                    .then(() => {
                        setSelectedTech(null);
                    })
            });
    };

    return {
        state,
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
        handleSubmit,
        handleClose,
        handleDeleteTech
    };
};

export default useManageTech;
