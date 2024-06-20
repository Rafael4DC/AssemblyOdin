import {useEffect, useState} from "react";
import {User} from "../services/user/models/User";
import {UserService} from "../services/user/UserService";

const useStudents = () => {
    const [students, setStudents] = useState<User[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        UserService.getStudents()
            .then(data => {
                setStudents(data.users);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {students, error};
};

export default useStudents;