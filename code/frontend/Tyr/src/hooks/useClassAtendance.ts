import {useEffect, useState} from 'react';
import {ClassAttendanceService} from '../services/ClassAttendanceService';
import {ClassAttendance} from "../model/ClassAtendance";

/**
 * Hook to get the class attendances
 *
 * @returns the class attendances, error and handles to save and delete a class attendance
 */
const useClassAttendances = () => {
    const [classAttendances, setClassAttendances] = useState<ClassAttendance[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        ClassAttendanceService.getAll()
            .then(data => {
                setClassAttendances(data);
            })
            .catch(err => {
                setError(err);
            });
    }, []);


    return {
        classAttendances,
        error,
        handleSaveClassAttendance: async (classAttendance: ClassAttendance) => {
            setError(null);
            try {
                if (classAttendance.id) {
                    return await ClassAttendanceService.update(classAttendance);
                } else {
                    return await ClassAttendanceService.save(classAttendance);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteClassAttendance: async (id: number) => {
            setError(null);
            try {
                await ClassAttendanceService.delete(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useClassAttendances;