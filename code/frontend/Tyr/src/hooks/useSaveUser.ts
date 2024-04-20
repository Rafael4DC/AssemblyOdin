import { useState } from 'react';
import { User } from '../model/GetUserInfoOutputModel';
import { UserService } from '../services/UserService';

const useSaveUser = () => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [error, setError] = useState<Error | null>(null);

    const saveUser = async (user: User) => {
        setIsLoading(true);
        setError(null);
        try {
            let savedUser;
            if (user.id) {
                savedUser = await UserService.update(user);
            } else {
                savedUser = await UserService.save(user);
            }
            setIsLoading(false);
            return savedUser;
        } catch (err) {
            setError(err);
            setIsLoading(false);
        }
    };

    return { saveUser/*, isLoading*/, errorSave: error};
};

export default useSaveUser;