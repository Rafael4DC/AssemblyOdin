import {useEffect, useState} from "react";
import {User} from "../../services/user/models/User";
import {UserService} from "../../services/user/UserService";
import {Failure, Success} from "../../services/_utils/Either";

const useStudents = () => {
    const [students, setStudents] = useState<User[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        UserService.getStudents()
            .then(data => {
                if (data instanceof Success) {
                    setStudents(data.value.users);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {students, error};
};

export default useStudents;