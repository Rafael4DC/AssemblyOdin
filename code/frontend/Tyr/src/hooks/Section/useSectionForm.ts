import {ChangeEvent, useState} from 'react';
import {Section} from "../../services/section/models/Section";
import {User} from "../../services/user/models/User";

const useSectionForm = (initSection?: Section) => {
    const [selectedSection, setSelectedSection] = useState<Section | null>(initSection || null);

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setSelectedSection((prevSection) => ({
            ...prevSection,
            [name]: value,
        }));
    };

    const handleModuleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setSelectedSection((prevSection) => ({
            ...prevSection,
            module: {id: Number(e.target.value)},
        }));
    };

    const handleStudentSelect = (student: User) => {
        setSelectedSection((prevSection) => ({
            ...prevSection,
            students: prevSection.students.some(stu => stu.id === student.id)
                ? prevSection.students.filter(stu => stu.id !== student.id)
                : [...prevSection.students, {id: student.id}]
        }));
    };

    return {
        selectedSection,
        setSelectedSection,
        handleInputChange,
        handleModuleChange,
        handleStudentSelect
    };
};

export default useSectionForm;
