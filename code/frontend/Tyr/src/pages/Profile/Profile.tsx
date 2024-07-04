import * as React from 'react';
import {Avatar, Card, CardContent, Container, Divider, Grid, List, ListItem, Typography} from '@mui/material';
import useUserWithLogs from "../../hooks/User/useUserWithLogs";
import {notStudent, toDateTimeStr} from "../../utils/Utils";
import {CustomScrollbar} from '../../components/Shared/CustomScrollbar';
import {Spinner} from "../../utils/Spinner";
import {AlertDialog} from "../../utils/AlertDialog";

/**
 * Page to display the user profile
 */
const Profile = () => {
    const {state} = useUserWithLogs()

    switch (state.type) {
        case "loading":
            return <Spinner/>

        case "error":
            return <AlertDialog alert={state.message}/>;

        case "success":
            const userWithLogs = state.userWithLogs;
            const role = userWithLogs.role.name;

            return (
                <Container maxWidth="lg">
                    <Card sx={{mt: 5, p: 2}}>
                        <CardContent>
                            <Grid container spacing={2}>
                                <Grid item xs={12} sm={4} display="flex" justifyContent="center">
                                    <Avatar
                                        alt={userWithLogs.username}
                                        src=""
                                        sx={{
                                            width: 180,
                                            height: 180,
                                            border: `3px solid black`
                                        }}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={8}>
                                    <Typography variant="h4">{userWithLogs.username}</Typography>
                                    <Typography variant="body1">{userWithLogs.email}</Typography>
                                    {!notStudent(role)
                                        && <Typography variant="h6">Credits: {userWithLogs.credits}</Typography>}
                                    <Typography variant="h6">Role: {role}</Typography>
                                </Grid>
                            </Grid>
                        </CardContent>
                    </Card>

                    {!notStudent(role)
                        && <Card sx={{mt: 3}}>
                            <CardContent>
                                <Typography variant="h6"> Logs </Typography>
                                <CustomScrollbar style={{width: '100%'}}>
                                    <List>
                                        {userWithLogs.logs.map((log, index) => (
                                            <>
                                                <ListItem key={index}>
                                                    <Grid container spacing={2} alignItems="center">
                                                        <Grid item xs={6}>
                                                            <Typography variant="h6">{log.description}</Typography>
                                                        </Grid>
                                                        <Grid item xs={3}>
                                                            <Typography
                                                                variant="body1"
                                                            >
                                                                {`Value: ${log.value}`}
                                                            </Typography>
                                                        </Grid>
                                                        <Grid item xs={3}>
                                                            <Typography
                                                                variant="body1"
                                                                sx={{textAlign: 'right'}}
                                                            >
                                                                {`Date: ${toDateTimeStr(log.date)}`}
                                                            </Typography>
                                                        </Grid>
                                                    </Grid>
                                                </ListItem>
                                                {index < userWithLogs.logs.length - 1 && <Divider/>}
                                            </>
                                        ))}
                                    </List>
                                </CustomScrollbar>
                            </CardContent>
                        </Card>}
                </Container>
            );
    }
}

export default Profile;
