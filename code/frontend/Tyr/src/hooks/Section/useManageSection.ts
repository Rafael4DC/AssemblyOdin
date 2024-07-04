import * as React from 'react';
import {useEffect, useState} from 'react';
import {SectionService} from "../../services/section/SectionService";
import useModules from "../Module/useModules";
import useStudents from "../User/useStudents";
import {Failure, Success} from "../../services/_utils/Either";
import useSectionForm from "./useSectionForm";
import {Section, sectionToInput} from "../../services/section/models/Section";
import useSections from "./useSections";
import {User} from "../../services/user/models/User";
import {Module} from "../../services/module/models/Module";
import {handleError} from "../../utils/Utils";

type ManageSectionsState =
    | { type: 'loading' }
    | { type: 'success'; modules: Module[]; filteredStudents: User[]; sections: Section[], loading: boolean }
    | { type: 'error'; message: string };

const useManageSections = () => {
    const [state, setState] = useState<ManageSectionsState>({type: 'loading'});
    const [searchQuery, setSearchQuery] = useState("");
    const {
        selectedSection,
        setSelectedSection,
        handleInputChange,
        handleModuleChange,
        handleStudentSelect
    } = useSectionForm()

    const {state: modulesState} = useModules();
    const {state: studentsState} = useStudents();
    const {state: sectionsState, getSections} = useSections();

    useEffect(() => {
        switch (true) {
            case modulesState.type === 'success' && studentsState.type === 'success' && sectionsState.type === 'success':
                setState({
                    type: 'success',
                    modules: modulesState.modules,
                    filteredStudents: filteredStudents(studentsState.students),
                    sections: sectionsState.sections,
                    loading: false
                });
                break;
            case modulesState.type === 'error':
                setState({type: 'error', message: modulesState.message});
                break;
            case studentsState.type === 'error':
                setState({type: 'error', message: studentsState.message});
                break;
            case sectionsState.type === 'error':
                setState({type: 'error', message: sectionsState.message});
                break;
        }
    }, [searchQuery, modulesState, studentsState, sectionsState]);

    const filteredStudents = (students: User[]) => students.filter(student =>
        student.username.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const handleSectionClick = (section: Section) => {
        setSelectedSection(section);
    };

    const handleClose = () => {
        setSelectedSection(null);
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setState(prevState => ({...prevState, loading: true}));
        SectionService.update(sectionToInput(selectedSection))
            .then(async data => {
                if (data instanceof Success) {
                    getSections()
                        .then(() => {
                            setSelectedSection(null)
                        });
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
        handleStudentSelect,
        handleSectionClick,
        handleInputChange,
        handleModuleChange,
        searchQuery,
        setSearchQuery,
        handleSubmit,
        handleClose
    };
};

export default useManageSections;
