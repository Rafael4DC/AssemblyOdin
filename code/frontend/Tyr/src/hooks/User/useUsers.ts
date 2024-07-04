import {useEffect, useState} from "react";
import {UserService} from "../../services/user/UserService";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";
import {User} from "../../services/user/models/User";

const useUsers = () => {
    const [state, setState] = useState<UsersState>({type: 'loading'});

    const getUsers = async () => {
        UserService.getAll()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', users: data.value.users});
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            });
    }

    useEffect(() => {
        getUsers();
    }, []);

    return {
        state,
        setState,
        getUsers
    };
};

type UsersState =
    | { type: 'loading' }
    | { type: 'success'; users: User[] }
    | { type: 'error'; message: string };

export default useUsers;