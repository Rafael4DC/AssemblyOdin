import {useEffect, useState} from 'react';
import {UserService} from '../services/user/UserService';
import {User} from "../services/user/models/User";
import {Failure, Success} from "../services/_utils/Either";
import {UserInputModel} from "../services/user/models/UserInputModel";


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

    const handleSaveUser = async (user: UserInputModel) => {
        setError(null);
        try {
            setIsSubmitting(true);
            let result = await UserService.save(user)
            let data: User
            if (result instanceof Failure) {
                throw new Error();
            }
            if (result instanceof Success) {
                data = result.value;
            }

            setUsers(prevUsers => {
                const updatedUsers = prevUsers ? [...prevUsers] : [];
                const userIndex = updatedUsers.findIndex(u => u.id === user.id);
                if (userIndex > -1) {
                    updatedUsers[userIndex] = data;
                } else {
                    updatedUsers.push(data);
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
            await UserService.deleteById(id);
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
                if (data instanceof Success) {
                    setUsers(data.value.users);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
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
