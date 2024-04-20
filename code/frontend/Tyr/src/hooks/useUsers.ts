import { useEffect, useState } from 'react';
import { UserService } from '../services/UserService';
import {User} from "../model/User";

const useUsers = () => {
    const [users, setUsers] = useState<User[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        UserService.getAll()
            .then(data => {
                setUsers(data);
            })
            .catch(err => {
                setError(err);
            });
    }, []);


    return {
        users,
        error,
        handleSaveUser1: async (user: User) => {
            setError(null);
            try {
                if (user.id) {
                    return await UserService.update(user);
                } else {
                    return await UserService.save(user);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteUser: async (id: number) => {
            setError(null);
            try {
                await UserService.delete(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useUsers;