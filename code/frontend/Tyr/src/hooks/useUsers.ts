import { useEffect, useState } from 'react';
import { UserService } from '../services/UserService';
import {User} from "../model/User";

const useUsers = () => {
    const [users, setUsers] = useState<User[] | null>(null);
    const [selectedUser, setSelectedUser] = useState<User | null>(null);
    const [showEditModal, setShowEditModal] = useState(false);
    const [searchTerm, setSearchTerm] = useState('');

    const [error, setError] = useState<Error | null>(null);

    const handleEditUserClick = (user: User) => {
        setSelectedUser(user);
        setShowEditModal(true);
    };

    const filteredUsers = searchTerm
      ? users.filter(user => user.username.toLowerCase().includes(searchTerm.toLowerCase()))
      : users;

    const handleSaveUser = async (user: User) => {
        setError(null);
        try {
            let result;
            if (user.id) {
                result = await UserService.update(user);
            } else {
                result = await UserService.save(user);
            }
            setShowEditModal(false);
            return result;
        } catch (err) {
            setError(err);
        }
    }

    const handleDeleteUser= async (id: number) => {
        setError(null);
        try {
            await UserService.delete(id);
        } catch (err) {
            setError(err);
        }
    }



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
        filteredUsers,
        error,
        searchTerm,
        selectedUser,
        showEditModal,
        setSearchTerm,
        setShowEditModal,
        handleSaveUser,
        handleDeleteUser,
        handleEditUserClick,
        setSelectedUser
    };
};

export default useUsers;