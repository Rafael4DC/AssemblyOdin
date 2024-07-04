import * as React from 'react';
import {useEffect, useState} from 'react';
import {VocService} from '../../services/voc/VocService';
import useSections from '../Section/useSections';
import {Voc, vocToInput} from "../../services/voc/models/Voc";
import {addWeeks, isAfter, isBefore, isWithinInterval, parseISO, subWeeks} from 'date-fns';
import {handleError} from "../../utils/Utils";
import useVocs from "./useVocs";
import {Section} from "../../services/section/models/Section";
import useVocForm from "./useVocForm";
import {Failure, Success} from "../../services/_utils/Either";

type ManageVocState =
    | { type: 'loading' }
    | { type: 'success'; filteredVocs: Voc[]; sections: Section[]; loading: boolean }
    | { type: 'error'; message: string };

const useManageVoc = () => {
    const [state, setState] = useState<ManageVocState>({type: 'loading'});
    const [searchQuery, setSearchQuery] = useState('');
    const [filter, setFilter] = useState('all');
    const {
        selectedVoc,
        setSelectedVoc,
        handleInputChange,
        handleSectionChange,
        handleTimeChange,
        handleDateChange
    } = useVocForm()

    const {state: sectionsState} = useSections();
    const {state: vocsState, getVocs} = useVocs();

    useEffect(() => {
        switch (true) {
            case sectionsState.type == 'success' && vocsState.type == 'success':
                setState({
                    type: 'success',
                    filteredVocs: filterVocs(vocsState.vocs),
                    sections: sectionsState.sections,
                    loading: false
                });
                break;
            case sectionsState.type == 'error':
                setState({type: 'error', message: sectionsState.message});
                break;
            case vocsState.type == 'error':
                setState({type: 'error', message: vocsState.message});
                break;
        }
    }, [sectionsState, vocsState, searchQuery, filter]);

    const filterVocs = (vocs: Voc[]) => {
        let result = vocs;

        if (searchQuery) {
            result = result.filter(voc =>
                voc.user.username.toLowerCase().includes(searchQuery.toLowerCase())
            );
        }

        const now = new Date();
        const nextWeek = addWeeks(now, 1);
        const pastWeek = subWeeks(now, 1);

        if (filter === 'past') {
            result = result.filter(voc => isBefore(parseISO(voc.started), now));
        } else if (filter === 'future') {
            result = result.filter(voc => isAfter(parseISO(voc.started), now));
        } else if (filter === 'nextWeek') {
            result = result.filter(voc => isWithinInterval(parseISO(voc.started), {start: now, end: nextWeek}));
        } else if (filter === 'pastWeek') {
            result = result.filter(voc => isWithinInterval(parseISO(voc.started), {start: pastWeek, end: now}));
        }

        return result
    };

    const handleVocClick = (voc: Voc) => {
        setSelectedVoc(voc);
    };

    const handleClose = () => {
        setSelectedVoc(null);
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setState(prevState => ({...prevState, loading: true}));
        VocService.update(vocToInput(selectedVoc))
            .then(async data => {
                if (data instanceof Success) {
                    getVocs()
                        .then(() => {
                            setSelectedVoc(null);
                        })
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            });
    };

    const handleDeleteVoc = async (id: number) => {
        setState(prevState => ({...prevState, loading: true}));
        VocService.deleteById(id)
            .then(async () => {
                getVocs()
                    .then(() => {
                        setSelectedVoc(null);
                    })
            });
    };

    const handleApprovedChange = async (voc: Voc) => {
        const updatedVoc = {...voc, approved: !voc.approved};
        setState(prevState => ({...prevState, loading: true}));
        VocService.update(vocToInput(updatedVoc))
            .then(async data => {
                if (data instanceof Success) {
                    await getVocs()
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            });
    };

    return {
        state,
        selectedVoc,
        handleVocClick,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        searchQuery,
        setSearchQuery,
        filter,
        setFilter,
        handleSubmit,
        handleClose,
        handleDeleteVoc,
        handleApprovedChange
    };
};

export default useManageVoc;
