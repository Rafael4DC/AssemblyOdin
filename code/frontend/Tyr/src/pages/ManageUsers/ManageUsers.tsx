import * as React from "react";
import {useState} from "react";
import {
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    Grid,
    IconButton,
    InputBase,
    InputLabel,
    List,
    ListItem,
    MenuItem,
    Select,
    SelectChangeEvent,
    TextField,
    Typography
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import useManageUsers from "../../hooks/User/useManageUsers";
import {User} from "../../services/user/models/User";
import {useTheme} from "@mui/material/styles";

/**
 * Page to manage users
 */
const ManageUsers = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        roles,
        users,
        searchQuery,
        handleSearchChange,
        handleUserUpdate,
        handleUserDelete
    } = useManageUsers();

    const [selectedUser, setSelectedUser] = useState<User | null>(null);

    const handleOpenEdit = (user: User) => setSelectedUser(user);
    const handleCloseEdit = () => setSelectedUser(null);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (selectedUser) {
            const {name, value} = e.target;
            setSelectedUser({...selectedUser, [name]: value});
        }
    };

    const handleRoleChange = (e: SelectChangeEvent<number>) => {
        if (selectedUser) {
            const roleId = e.target.value as number;
            const selectedRole = roles.find(role => role.id === roleId);
            if (selectedRole) {
                setSelectedUser({...selectedUser, role: selectedRole});
            }
        }
    };

    const handleSave = () => {
        if (selectedUser) {
            handleUserUpdate(selectedUser);
            handleCloseEdit();
        }
    };

    switch (state.type) {
        case 'loading':
            return <div>Loading...</div>;

        case 'error':
            return <div>Error: {state.message}</div>;

        case 'success':
            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color:customColor}}>
                        Manage Users
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2, color: '#000'}}>
                        <Box sx={{display: 'flex', alignItems: 'center', marginBottom: 2}}>
                            <InputBase
                                placeholder="Search users"
                                value={searchQuery}
                                onChange={(e) => handleSearchChange(e.target.value)}
                                sx={{flex: 1, paddingLeft: 1, color: '#000'}}
                            />
                            <IconButton sx={{p: '10px', color: '#000'}}>
                                <SearchIcon/>
                            </IconButton>
                        </Box>
                        <Grid container spacing={2} sx={{paddingLeft: 2, marginBottom: 2, fontWeight: 'bold'}}>
                            <Grid item xs={3}>Name</Grid>
                            <Grid item xs={3}>Email</Grid>
                            <Grid item xs={2}>Credits</Grid>
                            <Grid item xs={2}>Role</Grid>
                            <Grid item xs={2}>Actions</Grid>
                        </Grid>
                        <List>
                            {users.map(user => (
                                <ListItem key={user.id} sx={{color: '#000'}}>
                                    <Grid container spacing={2} alignItems="center">
                                        <Grid item xs={3}>
                                            <Typography noWrap>{user.username}</Typography>
                                        </Grid>
                                        <Grid item xs={3}>
                                            <Typography noWrap>{user.email}</Typography>
                                        </Grid>
                                        <Grid item xs={2}>
                                            <Typography noWrap
                                                        sx={{paddingLeft: 4}}>{user.credits?.toString() || ''}</Typography>
                                        </Grid>
                                        <Grid item xs={2}>
                                            <Typography noWrap>{user.role?.name || ''}</Typography>
                                        </Grid>
                                        <Grid item xs={1}>
                                            <Button onClick={() => handleOpenEdit(user)}>Edit</Button>
                                        </Grid>
                                        <Grid item xs={1}>
                                            <Button onClick={() => handleUserDelete(user.id)}>Delete</Button>
                                        </Grid>
                                    </Grid>
                                </ListItem>
                            ))}
                        </List>
                    </Box>
                    {selectedUser && (
                        <Dialog open={Boolean(selectedUser)} onClose={handleCloseEdit} fullWidth maxWidth="sm">
                            <DialogTitle sx={{color: '#000'}}>Edit User</DialogTitle>
                            <DialogContent sx={{padding: 3}}>
                                <TextField
                                    label="Username"
                                    type="text"
                                    name="username"
                                    value={selectedUser.username}
                                    onChange={handleInputChange}
                                    fullWidth
                                    margin="dense"
                                    sx={{color: '#000'}}
                                    InputLabelProps={{sx: {color: '#000'}}}
                                    InputProps={{sx: {color: '#000'}}}
                                />
                                <TextField
                                    label="Email"
                                    type="email"
                                    name="email"
                                    value={selectedUser.email}
                                    onChange={handleInputChange}
                                    fullWidth
                                    margin="dense"
                                    sx={{color: '#000'}}
                                    InputLabelProps={{sx: {color: '#000'}}}
                                    InputProps={{sx: {color: '#000'}}}
                                />
                                <TextField
                                    label="Credits"
                                    type="number"
                                    name="credits"
                                    value={selectedUser.credits}
                                    onChange={handleInputChange}
                                    fullWidth
                                    margin="dense"
                                    sx={{color: '#000'}}
                                    InputLabelProps={{sx: {color: '#000'}}}
                                    InputProps={{sx: {color: '#000'}}}
                                />
                                <FormControl fullWidth margin="dense">
                                    <InputLabel id="role-label" sx={{color: '#000'}}>Role</InputLabel>
                                    <Select
                                        labelId="role-label"
                                        name="role"
                                        value={selectedUser.role?.id || ''}
                                        onChange={handleRoleChange}
                                        sx={{color: '#000'}}
                                        inputProps={{sx: {color: '#000'}}}
                                    >
                                        {roles.map(role => (
                                            <MenuItem key={role.id} value={role.id} sx={{color: '#000'}}>
                                                {role.name}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            </DialogContent>
                            <DialogActions>
                                <Button onClick={handleCloseEdit} color="primary">
                                    Cancel
                                </Button>
                                <Button onClick={handleSave} color="primary">
                                    Save
                                </Button>
                            </DialogActions>
                        </Dialog>
                    )}
                </Container>
            );
    }
};

export default ManageUsers;
