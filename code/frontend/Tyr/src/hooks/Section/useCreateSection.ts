import * as React from 'react';
import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {SectionService} from "../../services/section/SectionService";
import useModules from "../Module/useModules";
import useStudents from "../User/useStudents";
import {WebUris} from "../../utils/WebUris";
import useSectionForm from "./useSectionForm";
import {initSection, sectionToInput} from "../../services/section/models/Section";
import {User} from "../../services/user/models/User";
import {Module} from "../../services/module/models/Module";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";
import SECTION = WebUris.SECTION;

type CreateSectionState =
    | { type: 'loading' }
    | { type: 'success'; modules: Module[]; filteredStudents: User[]; loading: boolean }
    | { type: 'error'; message: string };

const useCreateSection = () => {
    const navigate = useNavigate();

    const [state, setState] = useState<CreateSectionState>({type: 'loading'});
    const [searchQuery, setSearchQuery] = useState("");
    const [open, setOpen] = useState(false);
    const {
        selectedSection,
        handleInputChange,
        handleModuleChange,
        handleStudentSelect
    } = useSectionForm(initSection())

    const {state: modulesState} = useModules();
    const {state: studentsState} = useStudents();

    useEffect(() => {
        switch (true) {
            case modulesState.type === 'success' && studentsState.type === 'success':
                setState({
                    type: 'success',
                    modules: modulesState.modules,
                    filteredStudents: filteredStudents(studentsState.students),
                    loading: false
                });
                break;
            case modulesState.type === 'error':
                setState({type: 'error', message: modulesState.message});
                break;
            case studentsState.type === 'error':
                setState({type: 'error', message: studentsState.message});
                break;
        }
    }, [searchQuery, modulesState, studentsState]);

    const filteredStudents = (students: User[]) => students.filter(student =>
        student.username.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setState(prevState => ({...prevState, loading: true}));
        SectionService.save(sectionToInput(selectedSection))
            .then(data => {
                if (data instanceof Success) {
                    navigate(SECTION);
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            })
    };

    return {
        state,
        selectedSection,
        open,
        setOpen,
        searchQuery,
        setSearchQuery,
        handleSubmit,
        handleInputChange,
        handleModuleChange,
        handleStudentSelect
    };
};

export default useCreateSection;
