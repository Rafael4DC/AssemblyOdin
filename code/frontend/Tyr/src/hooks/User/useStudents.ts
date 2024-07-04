import {useEffect, useState} from "react";
import {User} from "../../services/user/models/User";
import {UserService} from "../../services/user/UserService";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";

const useStudents = () => {
    const [state, setState] = useState<StudentsState>({type: 'loading'});

    useEffect(() => {
        UserService.getStudents()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', students: data.value.users})
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            });
    }, []);

    return {
        state
    };
};

type StudentsState =
    | { type: 'loading' }
    | { type: 'success'; students: User[] }
    | { type: 'error'; message: string };

export default useStudents;