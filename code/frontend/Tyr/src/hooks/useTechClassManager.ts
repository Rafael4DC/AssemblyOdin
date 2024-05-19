import {useState} from 'react';
import {TechService} from '../services/TechService';
import {ClassAttendanceService} from '../services/ClassAttendanceService';
import {TechsAttendance} from "./useClassManager";

/**
 * Hook to manage the tech classes
 *
 * @param initialClasses - the initial classes
 *
 * @returns the selected class, show edit modal, loading and handles to edit the class
 */
export const useTechClassManager = (initialClasses: TechsAttendance[]) => {
    const [selectedClass, setSelectedClass] = useState<TechsAttendance | null>(null);
    const [showEditModal, setShowEditModal] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleEditClassClick = (cls: TechsAttendance) => {
        setSelectedClass(cls);
        setShowEditModal(true);
    };

    const handleSaveClass = async () => {
        if (selectedClass) {
            setLoading(true);
            try {
                await TechService.save(selectedClass.tech);
                const originalClass = initialClasses.find(cls => cls.tech.id === selectedClass.tech.id);
                if (!originalClass) throw new Error('Original class not found.');

                for (const student of selectedClass.attendedStudents) {
                    const originalStudent = originalClass.attendedStudents.find(st => st.student.id === student.student.id);
                    if (!originalStudent) throw new Error('Original student not found.');
                    if (student.attended !== originalStudent.attended) {
                        await ClassAttendanceService.save(student);
                    }
                }
                setShowEditModal(false);
            } catch (error) {
                console.error('Error saving class information:', error);
            } finally {
                setLoading(false);
            }
        }
    };

    const handleAttendanceChange = (studentId: number, attendance: boolean) => {
        setSelectedClass(prevSelectedClass => {
            if (!prevSelectedClass) return prevSelectedClass;
            const updatedStudents = prevSelectedClass.attendedStudents.map(student =>
                student.student.id === studentId ? {...student, attended: attendance} : student
            );
            return {...prevSelectedClass, attendedStudents: updatedStudents};
        });
    };

    const handleSummaryChange = (text: string) => {
        setSelectedClass(prevSelectedClass =>
            prevSelectedClass ? {
                ...prevSelectedClass,
                tech: {...prevSelectedClass.tech, summary: text}
            } : prevSelectedClass
        );
    };

    const handleDateChange = (newDate: string) => {
        setSelectedClass(prevSelectedClass =>
            prevSelectedClass ? {
                ...prevSelectedClass,
                tech: {...prevSelectedClass.tech, date: newDate}
            } : prevSelectedClass
        );
    };

    return {
        selectedClass,
        showEditModal,
        loading,
        handleEditClassClick,
        handleSaveClass,
        handleAttendanceChange,
        handleSummaryChange,
        handleDateChange,
        setShowEditModal,
    };
};
