import {useEffect, useState} from 'react';
import {UserService} from '../services/UserService';
import {User} from "../model/User";


/**
 * Hook to get the users
 *
 * @returns the users, error and handles to save and delete a user
 */
const useUsers = () => {
    const [users, setUsers] = useState<User[] | null>(null);
    const [selectedUser, setSelectedUser] = useState<User | null>(null);
    const [showEditModal, setShowEditModal] = useState(false);
    const [searchTerm, setSearchTerm] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [error, setError] = useState<Error | null>(null);

    const handleEditUserClick = (user: User) => {
        setSelectedUser(user);
        setShowEditModal(true);
    };

    const filteredUsers = searchTerm
        ? users?.filter(user => user.username.toLowerCase().includes(searchTerm.toLowerCase()))
        : users;

    const handleSaveUser = async (user: User) => {
        setError(null);
        try {
            setIsSubmitting(true);
            const result = await UserService.save(user)

            setUsers(prevUsers => {
                const updatedUsers = prevUsers ? [...prevUsers] : [];
                const userIndex = updatedUsers.findIndex(u => u.id === user.id);
                if (userIndex > -1) {
                    updatedUsers[userIndex] = result;
                } else {
                    updatedUsers.push(result);
                }
                return updatedUsers;
            });

            setIsSubmitting(false);
            setShowEditModal(false);
        } catch (err) {
            setIsSubmitting(false);
            setError(err);
        }
    };

    const handleDeleteUser = async (id: number) => {
        setError(null);
        try {
            setIsSubmitting(true);
            await UserService.delete(id);
            setUsers(prevUsers => prevUsers?.filter(user => user.id !== id) || null);
            setIsSubmitting(false);
            setShowEditModal(false);
        } catch (err) {
            setIsSubmitting(false);
            setError(err);
        }
    };

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
        setSelectedUser,
        isSubmitting,
        setIsSubmitting
    };
};

export default useUsers;
