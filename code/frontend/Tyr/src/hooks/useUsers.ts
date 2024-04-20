import { useEffect, useState } from 'react';
import { User } from '../model/GetUserInfoOutputModel';
import { UserService } from '../services/UserService';

const useUsers = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        UserService.getAll()
            .then(data => {
                setUsers(data);
                setIsLoading(false);
            })
            .catch(err => {
                setError(err);
                setIsLoading(false);
            });
    }, []);

    return { users, isLoading, error };
};

export default useUsers;