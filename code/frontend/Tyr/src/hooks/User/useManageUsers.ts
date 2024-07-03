import {useEffect, useState} from 'react';
import {UserService} from "../../services/user/UserService";
import {Failure, Success} from "../../services/_utils/Either";
import {User} from "../../services/user/models/User";
import {Role} from "../../services/user/models/Role";

type ManageUsersState =
    | { type: 'loading' }
    | { type: 'success'; users: User[] }
    | { type: 'error'; message: string };

const useManageUsers = () => {
    const [state, setState] = useState<ManageUsersState>({type: 'loading'});
    const [users, setUsers] = useState<User[]>([]);
    const [roles, setRoles] = useState<Role[]>([]);
    const [searchQuery, setSearchQuery] = useState<string>('');

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const data = await UserService.getAll();
                if (data instanceof Success) {
                    setUsers(data.value.users);
                    const dataRoles = await UserService.getRoles();
                    if (dataRoles instanceof Success) {
                        setRoles(dataRoles.value.roles);
                        setState({type: 'success', users: data.value.users});
                    } else if (dataRoles instanceof Failure) {
                        console.error('Error fetching roles:', dataRoles.value);
                    }
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            } catch (error) {
                setState({type: 'error', message: 'Failed to fetch users'});
            }
        };

        fetchUsers();
    }, []);

    const handleSearchChange = (query: string) => {
        setSearchQuery(query);
    };

    const handleUserUpdate = async (user: User) => {
        try {
            await UserService.update({
                id: user.id,
                username: user.username,
                email: user.email,
                credits: user.credits,
                role: user.role.id
            });
            setUsers((prevUsers) =>
                prevUsers.map((u) => (u.id === user.id ? {...u, ...user} : u))
            );
        } catch (error) {
            setState({type: 'error', message: 'Failed to update user'});
        }
    };

    const handleUserDelete = async (userId: number) => {
        try {
            await UserService.deleteById(userId);
            setUsers((prevUsers) => prevUsers.filter((user) => user.id !== userId));
        } catch (error) {
            setState({type: 'error', message: 'Failed to delete user'});
        }
    };

    const filteredUsers = users.filter((user) =>
        user.username.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return {
        state,
        roles,
        users: filteredUsers,
        searchQuery,
        handleSearchChange,
        handleUserUpdate,
        handleUserDelete
    };
};

export default useManageUsers;
