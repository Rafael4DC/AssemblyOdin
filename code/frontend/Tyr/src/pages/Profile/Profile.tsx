import * as React from 'react';
import {
    Container,
    Grid,
    Card,
    CardContent,
    Typography,
    Avatar,
    List,
    ListItem,
    Divider
} from '@mui/material';
import {useTheme} from '@mui/material/styles';
import useUserWithLogs from "../../hooks/useUserWithLogs";
import {toDateTimeStr} from "../../utils/Utils";
import { CustomScrollbar } from '../../components/Shared/CustomScrollbar';
import {Spinner} from "../../utils/Spinner";


function Profile() {
    const theme = useTheme();
    const {userWithLogs, error} = useUserWithLogs()

    if (userWithLogs == null) {
        return <Spinner/>
    }

    return (
        <Container maxWidth="lg">
            <Card sx={{mt: 5, p: 2}}>
                <CardContent>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={4} display="flex" justifyContent="center">
                            <Avatar
                                alt={userWithLogs.username}
                                src="/static/images/avatar/1.jpg"
                                sx={{width: 180, height: 180, border: `3px solid ${theme.palette.azulEscuro.main}`}}
                            />
                        </Grid>
                        <Grid item xs={12} sm={8}>
                            <Typography
                                variant="h4"
                                sx={{color: theme.palette.azulEscuro.main, fontFamily: theme.typography.fontFamily}}
                            >
                                {userWithLogs.username}
                            </Typography>
                            <Typography
                                variant="body1"
                                sx={{color: theme.palette.azulEscuro.main, fontFamily: theme.typography.fontFamily, mt: 1}}
                            >
                                {userWithLogs.email}
                            </Typography>
                            <Typography
                                variant="h6"
                                sx={{color: theme.palette.azulEscuro.main, fontFamily: theme.typography.fontFamily, mt: 1}}
                            >
                                Credits: {userWithLogs.credits}
                            </Typography>
                            <Typography
                                variant="h6"
                                sx={{color: theme.palette.azulEscuro.main, fontFamily: theme.typography.fontFamily, mt: 1}}
                            >
                                Role: {userWithLogs.role.name}
                            </Typography>
                        </Grid>
                    </Grid>
                </CardContent>
            </Card>

            <Card sx={{mt: 3}}>
                <CardContent>
                    <Typography
                        variant="h6"
                        sx={{color: theme.palette.azulEscuro.main, fontFamily: theme.typography.fontFamily, mb: 2}}
                    >
                        Logs
                    </Typography>
                    <CustomScrollbar style={{width: '100%'}}>
                        <List>
                            {userWithLogs.logs.map((log, index) => (
                                <React.Fragment key={index}>
                                    <ListItem>
                                        <Grid container spacing={2} alignItems="center">
                                            <Grid item xs={6}>
                                                <Typography
                                                    variant="h6"
                                                    sx={{
                                                        color: theme.palette.azulEscuro.main,
                                                        fontFamily: theme.typography.fontFamily
                                                    }}
                                                >
                                                    {log.description}
                                                </Typography>
                                            </Grid>
                                            <Grid item xs={3}>
                                                <Typography
                                                    variant="body1"
                                                    sx={{
                                                        color: theme.palette.azulEscuro.main,
                                                        fontFamily: theme.typography.fontFamily,
                                                        textAlign: 'right'
                                                    }}
                                                >
                                                    {`Points: ${log.value}`}
                                                </Typography>
                                            </Grid>
                                            <Grid item xs={3}>
                                                <Typography
                                                    variant="body1"
                                                    sx={{
                                                        color: theme.palette.azulEscuro.main,
                                                        fontFamily: theme.typography.fontFamily,
                                                        textAlign: 'right'
                                                    }}
                                                >
                                                    {`Date: ${toDateTimeStr(log.date)}`}
                                                </Typography>
                                            </Grid>
                                        </Grid>
                                    </ListItem>
                                    {index < userWithLogs.logs.length - 1 && <Divider/>}
                                </React.Fragment>
                            ))}
                        </List>
                    </CustomScrollbar>
                </CardContent>
            </Card>
        </Container>
    );
};

export default Profile;
