import {useEffect, useState} from "react";
import {Failure, Success} from "../services/_utils/Either";
import {UserService} from "../services/user/UserService";
import {GetUserWithLogsOutputModel} from "../services/user/models/GetUserWithLogsOutputModel";

const useUserWithLogs = () => {
    const [userWithLogs, setUserWithLogs] = useState<GetUserWithLogsOutputModel | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        UserService.getSessionWithLogs()
            .then(data => {
                if (data instanceof Success) {
                    setUserWithLogs(data.value);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {userWithLogs, error};
};

export default useUserWithLogs;