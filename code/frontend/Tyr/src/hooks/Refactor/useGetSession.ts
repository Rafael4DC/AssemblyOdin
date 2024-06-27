import {useEffect, useState} from "react";
import {User} from "../../services/user/models/User";
import {UserService} from "../../services/user/UserService";
import {Failure, Success} from "../../services/_utils/Either";

const useGetSession = () => {
    const [userInfo, setUserInfo] = useState<User | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        UserService.getSession()
            .then(data => {
                if (data instanceof Success) {
                    setUserInfo(data.value);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {userInfo, error};
};

export default useGetSession;