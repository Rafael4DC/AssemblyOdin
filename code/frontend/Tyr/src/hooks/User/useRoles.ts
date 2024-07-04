import {useEffect, useState} from "react";
import {UserService} from "../../services/user/UserService";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";
import {Role} from "../../services/user/models/Role";

const useRoles = () => {
    const [state, setState] = useState<RolesState>({type: 'loading'});

    useEffect(() => {
        UserService.getRoles()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', roles: data.value.roles});
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

type RolesState =
    | { type: 'loading' }
    | { type: 'success'; roles: Role[] }
    | { type: 'error'; message: string };

export default useRoles;