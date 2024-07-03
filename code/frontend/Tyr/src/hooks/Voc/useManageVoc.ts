import {ChangeEvent, useEffect, useState} from 'react';
import {VocService} from '../../services/voc/VocService';
import useSections from '../Section/useSections';
import useStudents from '../User/useStudents';
import {Failure, Success} from '../../services/_utils/Either';
import {Voc} from "../../services/voc/models/Voc";
import {addWeeks, endOfWeek, isAfter, isBefore, isWithinInterval, parseISO, startOfWeek, subWeeks} from 'date-fns';

type ManageVocState =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };

const useManageVoc = () => {
    const [state, setState] = useState<ManageVocState>({type: 'loading'});
    const [vocs, setVocs] = useState<Voc[]>([]);
    const [filteredVocs, setFilteredVocs] = useState<Voc[]>([]);
    const [selectedVoc, setSelectedVoc] = useState<Voc>(null);
    const [vocData, setVocData] = useState<Voc>({
        description: '',
        started: '',
        ended: '',
        section: {id: 1}
    });
    const [searchQuery, setSearchQuery] = useState('');
    const [filter, setFilter] = useState('all');
    const {sections, error: sectionsError} = useSections();
    const {students, error: studentsError} = useStudents();

    useEffect(() => {
        const fetchVocs = async () => {
            try {
                const vocs = await VocService.getAll();
                if (vocs instanceof Success) {
                    setVocs(vocs.value.vocs);
                } else if (vocs instanceof Failure) {
                    console.error('Error fetching data:', vocs.value);
                }
                setState({type: 'success', message: ''});
            } catch (error) {
                setState({type: 'error', message: handleError(error)});
            }
        };

        fetchVocs();
    }, []);

    useEffect(() => {
        if (sectionsError || studentsError) {
            setState({type: 'error', message: handleError(sectionsError || studentsError)});
        }
    }, [sectionsError, studentsError]);

    useEffect(() => {
        filterVocs();
    }, [vocs, searchQuery, filter]);

    const filterVocs = () => {
        let result = vocs;

        if (searchQuery) {
            result = result.filter(voc =>
                voc.user.username.toLowerCase().includes(searchQuery.toLowerCase())
            );
        }

        const now = new Date();
        const nextWeek = addWeeks(now, 1);
        const pastWeek = subWeeks(now, 1);
        const startOfThisWeek = startOfWeek(now);
        const endOfThisWeek = endOfWeek(now);

        if (filter === 'past') {
            result = result.filter(voc => isBefore(parseISO(voc.started), now));
        } else if (filter === 'future') {
            result = result.filter(voc => isAfter(parseISO(voc.started), now));
        } else if (filter === 'nextWeek') {
            result = result.filter(voc => isWithinInterval(parseISO(voc.started), {start: now, end: nextWeek}));
        } else if (filter === 'pastWeek') {
            result = result.filter(voc => isWithinInterval(parseISO(voc.started), {start: pastWeek, end: now}));
        }

        setFilteredVocs(result);
    };

    const handleError = (error: any) => {
        if (error instanceof Error) {
            return error.message;
        } else {
            return 'An unexpected error occurred';
        }
    };

    const handleVocClick = (voc: Voc) => {
        setSelectedVoc(voc);
        setVocData({
            description: voc.description,
            started: voc.started,
            ended: voc.ended,
            section: {id: voc.section.id},
        });
    };

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setVocData((prevVocData) => ({
            ...prevVocData,
            [name]: value,
        }));
    };

    const handleSectionChange = (e: ChangeEvent<HTMLInputElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            section: {id: Number(e.target.value)},
        }));
    };

    const handleDateChange = (e: ChangeEvent<HTMLInputElement>) => {
        const date = e.target.value;
        setVocData((prevVocData) => ({
            ...prevVocData,
            started: `${date}T${prevVocData.started.split('T')[1]}`,
            ended: `${date}T${prevVocData.ended.split('T')[1]}`,
        }));
    };

    const handleTimeChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: 'started' | 'ended') => {
        const time = e.target.value;
        setVocData((prevVocData) => ({
            ...prevVocData,
            [key]: `${prevVocData[key].split('T')[0]}T${time}`,
        }));
    };

    const handleSubmit = async () => {
        setState({type: 'loading'});
        try {
            await VocService.update({
                id: selectedVoc.id,
                description: vocData.description,
                started: vocData.started,
                ended: vocData.ended,
                section: vocData.section.id,
                approved: selectedVoc.approved,
                user: selectedVoc.user.id
            });
            const updatedVocs = await VocService.getAll();
            if (updatedVocs instanceof Success) {
                setVocs(updatedVocs.value.vocs);
            } else if (updatedVocs instanceof Failure) {
                console.error('Error fetching data:', updatedVocs.value);
            }
            setSelectedVoc(null);
            setState({type: 'success', message: ''});
        } catch (error) {
            setState({type: 'error', message: handleError(error)});
        }
    };

    const handleClose = () => {
        setSelectedVoc(null);
    };

    const handleDeleteVoc = async (id: number) => {
        setState({type: 'loading'});
        try {
            await VocService.deleteById(id);
            const updatedVocs = await VocService.getAll();
            if (updatedVocs instanceof Success) {
                setVocs(updatedVocs.value.vocs);
            } else if (updatedVocs instanceof Failure) {
                console.error('Error fetching data:', updatedVocs.value);
            }
            setState({type: 'success', message: ''});
        } catch (error) {
            setState({type: 'error', message: handleError(error)});
        }
    };

    const handleApprovedChange = async (voc: Voc) => {
        const updatedVoc = {...voc, approved: !voc.approved};
        setState({type: 'loading'});
        try {
            await VocService.update({
                id: updatedVoc.id,
                description: updatedVoc.description,
                started: updatedVoc.started,
                ended: updatedVoc.ended,
                section: updatedVoc.section.id,
                approved: updatedVoc.approved,
                user: updatedVoc.user.id
            });
            const updatedVocs = await VocService.getAll();
            if (updatedVocs instanceof Success) {
                setVocs(updatedVocs.value.vocs);
            } else if (updatedVocs instanceof Failure) {
                console.error('Error fetching data:', updatedVocs.value);
            }
            setState({type: 'success', message: ''});
        } catch (error) {
            setState({type: 'error', message: handleError(error)});
        }
    };

    return {
        vocs,
        sections,
        students,
        state,
        vocData,
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
        filteredVocs,
        handleSubmit,
        handleClose,
        handleDeleteVoc,
        handleApprovedChange
    };
};

export default useManageVoc;
