import {useEffect, useState} from "react";
import {Failure, Success} from "../../services/_utils/Either";
import {UserService} from "../../services/user/UserService";
import {GetUserWithLogsOutputModel} from "../../services/user/models/GetUserWithLogsOutputModel";
import {handleError} from "../../utils/Utils";

const useUserWithLogs = () => {
    const [state, setState] = useState<UserWithLogsState>({type: 'loading'});

    useEffect(() => {
        UserService.getSessionWithLogs()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', userWithLogs: data.value});
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

type UserWithLogsState =
    | { type: 'loading' }
    | { type: 'success'; userWithLogs: GetUserWithLogsOutputModel }
    | { type: 'error'; message: string };

export default useUserWithLogs;