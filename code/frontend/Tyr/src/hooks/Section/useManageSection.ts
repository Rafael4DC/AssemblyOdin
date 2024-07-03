import {ChangeEvent, SetStateAction, useEffect, useState} from 'react';
import {SectionService} from "../../services/section/SectionService";
import useModules from "../useModules";
import useStudents from "../User/useStudents";
import {WebUris} from "../../utils/WebUris";
import {Failure, Success} from "../../services/_utils/Either";

const SECTION = WebUris.SECTION;

type ManageSectionsState =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };

const useManageSections = () => {
    const [state, setState] = useState<ManageSectionsState>({type: 'loading'});
    const [sections, setSections] = useState([]);
    const [selectedSection, setSelectedSection] = useState(null);
    const [sectionData, setSectionData] =
        useState({
                name: "",
                module: {id: 1},
                students: []
            }
        );
    const [searchQuery, setSearchQuery] = useState("");
    const {modules, error: modulesError} = useModules();
    const {students, error: studentsError} = useStudents();

    useEffect(() => {
        const fetchSections = async () => {
            try {
                const sections = await SectionService.getAll();
                if (sections instanceof Success) {
                    setSections(sections.value.sections);
                } else if (sections instanceof Failure) {
                    console.error('Error fetching data:', sections.value);
                }
                setState({type: 'success', message: ''});
            } catch (error) {
                setState({type: 'error', message: handleError(error)});
            }
        };

        fetchSections();
    }, []);

    useEffect(() => {
        if (modulesError || studentsError) {
            setState({type: 'error', message: handleError(modulesError || studentsError)});
        }
    }, [modulesError, studentsError]);

    const handleError = (error: any) => {
        if (error instanceof Error) {
            return error.message;
        } else {
            return 'An unexpected error occurred';
        }
    };

    const handleSectionClick = (section: { name: any; module: { id: any; }; students: any[]; }) => {
        setSelectedSection(section);
        setSectionData({
            name: section.name,
            module: {id: section.module.id},
            students: section.students.map(student => student.id)
        });
    };

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setSectionData((prevSectionData) => ({
            ...prevSectionData,
            [name]: value,
        }));
    };

    const handleModuleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setSectionData((prevSectionData) => ({
            ...prevSectionData,
            module: {id: Number(e.target.value)},
        }));
    };

    const handleStudentSelection = (studentId: number) => {
        setSectionData((prevSectionData) => ({
            ...prevSectionData,
            students: prevSectionData.students.includes(studentId)
                ? prevSectionData.students.filter(id => id !== studentId)
                : [...prevSectionData.students, studentId]
        }));
    };

    const handleSearchChange = (event: { target: { value: SetStateAction<string>; }; }) => {
        setSearchQuery(event.target.value);
    };

    const handleSubmit = async () => {
        setState({type: 'loading'});
        try {
            await SectionService.update({
                id: selectedSection.id,
                name: sectionData.name,
                module: sectionData.module.id,
                students: sectionData.students
            });
            const updatedSections = await SectionService.getAll();
            if (updatedSections instanceof Success) {
                setSections(updatedSections.value.sections);
            } else if (updatedSections instanceof Failure) {
                console.error('Error fetching data:', updatedSections.value);
            }
            setSelectedSection(null);
            setState({type: 'success', message: ''});
        } catch (error) {
            setState({type: 'error', message: handleError(error)});
        }
    };

    const handleClose = () => {
        setSelectedSection(null);
    };

    return {
        sections,
        modules,
        students,
        state,
        sectionData,
        selectedSection,
        handleSectionClick,
        handleInputChange,
        handleModuleChange,
        handleStudentSelection,
        handleSearchChange,
        searchQuery,
        handleSubmit,
        handleClose
    };
};

export default useManageSections;
