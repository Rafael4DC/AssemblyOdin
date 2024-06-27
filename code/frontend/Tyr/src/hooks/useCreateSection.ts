import * as React from 'react';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { SectionService } from "../services/section/SectionService";
import useModules from "./useModules";
import useStudents from "./useStudents";
import { WebUris } from "../utils/WebUris";
import SECTION = WebUris.SECTION;
const PROFILE = WebUris.PROFILE;

type CreateSectionState =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };

const useCreateSection = () => {
    const navigate = useNavigate();
    const [state, setState] = useState<CreateSectionState>({ type: 'loading' });

    const [sectionData, setSectionData] = useState({
        name: "",
        summary: "",
        module: { id: 1 },
        students: []
    });

    const [selectedStudents, setSelectedStudents] = useState<number[]>([]);

    const { modules, error: modulesError } = useModules();
    const { students, error: studentsError } = useStudents();

    useEffect(() => {
        if (state.type === 'loading' && modules !== null && students !== null) {
            setState({ type: 'success', message: '' });
        } else if (modulesError || studentsError) {
            setState({ type: 'error', message: handleError(modulesError || studentsError) });
        }
    }, [state.type, modules, students]);

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
        try {
            await SectionService.save({
                name: sectionData.name,
                module: sectionData.module.id,
                students: selectedStudents
            });
            navigate(SECTION);
        } catch (error) {
            setState({ type: 'error', message: handleError(error) });
        }
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setSectionData((prevSectionData) => ({
            ...prevSectionData,
            [name]: value,
        }));
    };

    const handleModuleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setSectionData((prevSectionData) => ({
            ...prevSectionData,
            module: { id: Number(e.target.value) },
        }));
    };

    const handleStudentSelection = (studentId: number) => {
        setSelectedStudents((prevSelectedStudents) =>
            prevSelectedStudents.includes(studentId)
                ? prevSelectedStudents.filter(id => id !== studentId)
                : [...prevSelectedStudents, studentId]
        );
    };

    return {
        sectionData,
        state,
        modules,
        students,
        selectedStudents,
        handleSubmit,
        handleInputChange,
        handleModuleChange,
        handleStudentSelection
    };
};

export default useCreateSection;
