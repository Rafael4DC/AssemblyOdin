import * as React from 'react';
import {useEffect, useState} from 'react';
import {UserService} from "../../services/user/UserService";
import {User, userToInput} from "../../services/user/models/User";
import {Role} from "../../services/user/models/Role";
import UseRoles from "./useRoles";
import UseUsers from "./useUsers";
import useUserForm from "./useUserForm";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";

/**
 * State for the manage users
 */
type ManageUsersState =
    | { type: 'loading' }
    | { type: 'success'; filteredUsers: User[]; roles: Role[]; loading: boolean }
    | { type: 'error'; message: string };

/**
 * Hook to manage users
 *
 * @returns the state and functions to manage users
 */
const useManageUsers = () => {
    const [state, setState] = useState<ManageUsersState>({type: 'loading'});
    const [searchQuery, setSearchQuery] = useState<string>('');
    const {
        selectedUser,
        setSelectedUser,
        handleInputChange,
        handleRoleChange
    } = useUserForm();

    const {state: usersState, getUsers} = UseUsers()
    const {state: rolesState} = UseRoles()

    useEffect(() => {
        switch (true) {
            case usersState.type === 'success' && rolesState.type === 'success':
                setState({
                    type: 'success',
                    filteredUsers: filteredUsers(usersState.users),
                    roles: rolesState.roles,
                    loading: false
                });
                break;
            case usersState.type === 'error':
                setState({type: 'error', message: usersState.message});
                break;
            case rolesState.type === 'error':
                setState({type: 'error', message: rolesState.message});
                break;
        }
    }, [usersState, rolesState, searchQuery]);

    const filteredUsers = (users: User[]) => users.filter((user) =>
        user.username.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const handleOpenEdit = (user: User) => setSelectedUser(user);
    const handleCloseEdit = () => {
        setSelectedUser(null)
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setState(prevState => ({...prevState, loading: true}));
        UserService.update(userToInput(selectedUser))
            .then(async data => {
                if (data instanceof Success) {
                    getUsers()
                        .then(() => {
                            handleCloseEdit();
                        })
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            })
    };

    return {
        state,
        selectedUser,
        handleInputChange,
        handleRoleChange,
        searchQuery,
        setSearchQuery,
        handleSubmit,
        handleOpenEdit,
        handleCloseEdit
    };
};

export default useManageUsers;
