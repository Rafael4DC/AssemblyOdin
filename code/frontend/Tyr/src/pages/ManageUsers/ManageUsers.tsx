import * as React from "react";
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
    TextField,
    Typography
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import useManageUsers from "../../hooks/User/useManageUsers";
import {useTheme} from "@mui/material/styles";
import {Spinner} from "../../utils/Spinner";
import {AlertDialog} from "../../utils/AlertDialog";

/**
 * Page to manage users
 */
const ManageUsers = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        selectedUser,
        handleInputChange,
        handleRoleChange,
        searchQuery,
        setSearchQuery,
        handleSubmit,
        handleOpenEdit,
        handleCloseEdit
    } = useManageUsers();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const {filteredUsers, roles, loading} = state;

            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color: customColor}}>
                        Manage Users
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2}}>
                        <Box sx={{display: 'flex', alignItems: 'center', marginBottom: 2}}>
                            <InputBase
                                placeholder="Search users"
                                value={searchQuery}
                                onChange={(e) => setSearchQuery(e.target.value)}
                                sx={{flex: 1, paddingLeft: 1}}
                            />
                            <IconButton sx={{p: '10px'}}>
                                <SearchIcon/>
                            </IconButton>
                        </Box>
                        <Grid container spacing={2} sx={{paddingLeft: 2, marginBottom: 2, fontWeight: 'bold'}}>
                            <Grid item xs={3}>Name</Grid>
                            <Grid item xs={3}>Email</Grid>
                            <Grid item xs={2}>Credits</Grid>
                            <Grid item xs={2}>Role</Grid>
                            <Grid item xs={2}>Action</Grid>
                        </Grid>
                        <List>
                            {filteredUsers.map(user => (
                                <ListItem key={user.id}>
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
                                            <Button onClick={() => handleOpenEdit(user)}
                                                    disabled={loading}>Edit</Button>
                                        </Grid>
                                    </Grid>
                                </ListItem>
                            ))}
                        </List>
                    </Box>
                    {selectedUser && (
                        <Dialog open={Boolean(selectedUser)} onClose={handleCloseEdit} fullWidth maxWidth="sm">
                            <DialogTitle>Edit User</DialogTitle>
                            <DialogContent sx={{padding: 3}}>
                                <TextField
                                    label="Username"
                                    type="text"
                                    name="username"
                                    value={selectedUser.username}
                                    onChange={handleInputChange}
                                    fullWidth
                                    margin="dense"
                                />
                                <TextField
                                    label="Email"
                                    type="email"
                                    name="email"
                                    value={selectedUser.email}
                                    onChange={handleInputChange}
                                    fullWidth
                                    margin="dense"
                                />
                                <TextField
                                    label="Credits"
                                    type="number"
                                    name="credits"
                                    value={selectedUser.credits}
                                    onChange={handleInputChange}
                                    fullWidth
                                    margin="dense"
                                />
                                <FormControl fullWidth margin="dense">
                                    <InputLabel id="role-label">Role</InputLabel>
                                    <Select
                                        labelId="role-label"
                                        name="role"
                                        value={selectedUser.role?.id || ''}
                                        onChange={handleRoleChange}
                                    >
                                        {roles.map(role => (
                                            <MenuItem key={role.id} value={role.id}>
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
                                <Button onClick={handleSubmit} color="primary" disabled={loading}>
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
