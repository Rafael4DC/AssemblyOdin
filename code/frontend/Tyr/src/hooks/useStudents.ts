import {useEffect, useState} from 'react';
import {TechService} from '../services/TechService';
import {Tech} from "../model/Tech";
import {UserService} from "../services/UserService";
import {Student} from "../model/Student";
import {StudentService} from "../services/StudentService";

/**
 * Hook to get the tech classes
 *
 * @returns the tech classes, error and handles to save and delete a tech class
 */
const useStudents = () => {
    const [students, setStudents] = useState<Student[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        StudentService.getAll()
            .then(data => {
                setStudents(data);
            })
            .catch(err => {
                setError(err);
            });
    }, []);



    return {
        students,
        error
    };
};

export default useStudents;