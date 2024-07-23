import {ChangeEvent, useState} from 'react';
import {User} from "../../services/user/models/User";
import {SelectChangeEvent} from "@mui/material";

/**
 * Hook to manage the user form
 *
 * @param initUser the initial user
 * @returns the state and functions to manage the user form
 */
const useUserForm = (initUser?: User) => {
    const [selectedUser, setSelectedUser] = useState<User | null>(initUser || null);

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setSelectedUser((prevUser) => ({
            ...prevUser,
            [name]: value,
        }));
    };
    const handleRoleChange = (e: SelectChangeEvent<number>) => {
        setSelectedUser((prevUser) => ({
            ...prevUser,
            role: {id: Number(e.target.value)},
        }));
    };


    return {
        selectedUser,
        setSelectedUser,
        handleInputChange,
        handleRoleChange
    };
};

export default useUserForm;
