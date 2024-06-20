import {useEffect, useState} from "react";
import {User} from "../services/user/models/User";
import {UserService} from "../services/user/UserService";

const useGetSession = () => {
    const [userInfo, setUserInfo] = useState<User | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        UserService.getSession()
            .then(data => {
                setUserInfo(data);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {userInfo, error};
};

export default useGetSession;