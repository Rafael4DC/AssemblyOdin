import {useEffect, useState} from 'react';
import {UserService} from '../../services/user/UserService';
import {User} from '../../services/user/models/User';
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";

/**
 * State for the user info
 */
type UserInfoState =
    | { type: 'loading' }
    | { type: 'success'; userInfo: User }
    | { type: 'error'; message: string };

/**
 * Hook to get the user info
 *
 * @returns the satate with the user info
 */
const useUserInfo = () => {
    const [state, setState] = useState<UserInfoState>({type: 'loading'});

    useEffect(() => {
        UserService.getSession()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', userInfo: data.value})
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


export default useUserInfo;
