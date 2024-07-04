import {useEffect, useState} from "react";
import {User} from "../model/User";
import {UserService} from "../services/UserService";

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