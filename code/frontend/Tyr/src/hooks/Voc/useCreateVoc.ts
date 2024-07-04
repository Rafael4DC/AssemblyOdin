import * as React from 'react';
import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import useSections from "../Section/useSections";
import useStudents from "../User/useStudents";
import {initVoc, vocToInput} from "../../services/voc/models/Voc";
import useUserInfo from "../User/useUserInfo";
import {Success} from "../../services/_utils/Either";
import {WebUris} from "../../utils/WebUris";
import {User} from "../../services/user/models/User";
import {VocService} from "../../services/voc/VocService";
import {handleError} from "../../utils/Utils";
import {Section} from "../../services/section/models/Section";
import useVocForm from "./useVocForm";
import TIMETABLE = WebUris.TIMETABLE;

type CreateVocClassState =
    | { type: 'loading' }
    | { type: 'success'; sections: Section[]; userInfo: User; filteredStudents: User[]; loading: boolean }
    | { type: 'error'; message: string };

const useCreateVoc = () => {
    const navigate = useNavigate();

    const [state, setState] = useState<CreateVocClassState>({type: 'loading'});
    const [searchQuery, setSearchQuery] = useState("");
    const [open, setOpen] = useState(false);
    const {
        selectedVoc,
        setSelectedVoc,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
    } = useVocForm(initVoc())

    const {state: userInfoState} = useUserInfo();
    const {state: sectionsState} = useSections();
    const {state: studentsState} = useStudents();

    useEffect(() => {
        switch (true) {
            case userInfoState.type === 'success' && sectionsState.type === 'success' && studentsState.type === 'success':
                setState({
                    type: 'success',
                    sections: sectionsState.sections,
                    userInfo: userInfoState.userInfo,
                    filteredStudents: filteredStudents(studentsState.students),
                    loading: false
                });
                break;
            case userInfoState.type === 'error':
                setState({type: 'error', message: userInfoState.message});
                break;
            case sectionsState.type === 'error':
                setState({type: 'error', message: sectionsState.message});
                break;
            case studentsState.type === 'error':
                setState({type: 'error', message: studentsState.message});
                break;
        }
    }, [searchQuery, userInfoState, sectionsState, studentsState]);

    const filteredStudents = (students: User[]) => students.filter(student =>
        student.username.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const handleStudentSelect = (student: User) => {
        setSelectedVoc({...selectedVoc, user: student});
        setOpen(false);
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setState(prevState => ({...prevState, loading: true}));
        try {
            let data;
            if (selectedVoc.id) {
                data = await VocService.update(vocToInput(selectedVoc));
            } else {
                data = await VocService.save(vocToInput(selectedVoc));
            }
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
        selectedVoc,
        open,
        setOpen,
        searchQuery,
        setSearchQuery,
        handleSubmit,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        handleStudentSelect
    };
};

export default useCreateVoc;
