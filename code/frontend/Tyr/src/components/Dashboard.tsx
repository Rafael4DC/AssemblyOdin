import * as React from 'react';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import {Link} from 'react-router-dom';

import {WebUris} from "../utils/WebUris";
import {useSessionData} from "../session/Session";
import {
    AccountBoxOutlined,
    AddCircleOutlineOutlined,
    CalendarMonthOutlined,
    ClassOutlined,
    ManageAccountsOutlined,
    ManageHistory,
    ManageSearchOutlined,
    SchoolOutlined
} from "@mui/icons-material";
import logo from '../assets/logo_Assembly.png';
import {AppBar, Drawer, DrawerHeader, StyledDivider} from "../utils/StyledComponents";
import {useTheme} from "@mui/material/styles";
import {Tooltip} from "@mui/material";
import {notAdmin, notStudent} from "../utils/Utils";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import DEPARTMENTS = WebUris.DEPARTMENTS;
import CREATE_SECTION = WebUris.CREATE_SECTION;
import SECTION = WebUris.SECTION;
import CREATE_VOC = WebUris.CREATE_VOC;
import CREATE_TECH = WebUris.CREATE_TECH;
import TIMETABLE = WebUris.TIMETABLE;
import MANAGE_SECTIONS = WebUris.MANAGE_SECTIONS;
import MANAGE_TECH = WebUris.MANAGE_TECH;
import MANAGE_VOC = WebUris.MANAGE_VOC;

/**
 * Dashboard App Bar and Drawer
 */
export default function Dashboard({children}: { children: React.ReactNode }) {
    const theme = useTheme();
    const azulEscuroMain = theme.palette.azulEscuro.main;
    const [open, setOpen] = React.useState(false);
    const userInfo = useSessionData();
    const role = userInfo?.role;

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    const handleLogout = () => {
        fetch('http://localhost:8080/logout', {
            method: 'POST',
            credentials: 'same-origin',
        }).then(() => {
            window.location.href = '/';
        })
    };

    const linkClass = (path: string) =>
        window.location.pathname === path ? "link clickedLink" : "link";

    if (userInfo == null) return null;

    return (
        <Box sx={{display: 'flex'}}>
            <CssBaseline/>
            <AppBar position="fixed" open={open}>
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        sx={{
                            marginRight: 5,
                            ...(open && {display: 'none'}),
                            color: azulEscuroMain, // Set color to azulEscuro
                        }}
                    >
                        <MenuIcon/>
                    </IconButton>
                    {!open && (
                        <Typography variant="h6" noWrap component="div">
                            <Link to={HOME} style={{textDecoration: 'none', color: '#fff'}}>
                                <img src={logo} alt="Assembly" style={{height: '42px'}}/>
                            </Link>
                        </Typography>
                    )}
                </Toolbar>
            </AppBar>
            <Drawer variant="permanent" open={open}>
                <DrawerHeader>
                    {open && (
                        <Typography variant="h6" noWrap component="div" sx={{flexGrow: 1}}>
                            <Link to={HOME} style={{textDecoration: 'none', color: 'inherit'}}>
                                <img src={logo} alt="Assembly" style={{height: '35px'}}/>
                            </Link>
                        </Typography>
                    )}
                    <IconButton onClick={handleDrawerClose} sx={{color: azulEscuroMain}}>
                        {theme.direction === 'rtl' ? <ChevronRightIcon/> : <ChevronLeftIcon/>}
                    </IconButton>
                </DrawerHeader>
                <List>
                    <Tooltip title="Profile" placement="right">
                        <ListItemButton
                            component={Link}
                            to={PROFILE}
                            className={linkClass(PROFILE)}
                            sx={!open && {
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                textAlign: 'center',
                            }}
                        >
                            <ListItemIcon
                                sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                            >
                                <AccountBoxOutlined/>
                            </ListItemIcon>
                            {!open && (
                                <Typography
                                    variant="caption"
                                    sx={{
                                        mt: 1,
                                        whiteSpace: 'nowrap',
                                        overflow: 'hidden',
                                        textOverflow: 'ellipsis',
                                        color: azulEscuroMain,
                                        fontSize: '11px'
                                    }}
                                >
                                    Profile
                                </Typography>
                            )}
                            {open && <ListItemText primary="Profile" sx={{color: azulEscuroMain}}/>}
                        </ListItemButton>
                    </Tooltip>
                    {notAdmin(role) && (
                        <>
                            <Tooltip title="Timetable" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={TIMETABLE}
                                    className={linkClass(TIMETABLE)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <CalendarMonthOutlined/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            Timetable
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Timetable" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                            <Tooltip title="Departments" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={DEPARTMENTS}
                                    className={linkClass(DEPARTMENTS)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <SchoolOutlined/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            Departs
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Departments" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                        </>
                    )}
                    <Tooltip title="Create VOC" placement="right">
                        <ListItemButton
                            component={Link}
                            to={CREATE_VOC}
                            className={linkClass(CREATE_VOC)}
                            sx={!open && {
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                textAlign: 'center',
                            }}
                        >
                            <ListItemIcon
                                sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                            >
                                <AddCircleOutlineOutlined/>
                            </ListItemIcon>
                            {!open && (
                                <Typography
                                    variant="caption"
                                    sx={{
                                        mt: 1,
                                        whiteSpace: 'nowrap',
                                        overflow: 'hidden',
                                        textOverflow: 'ellipsis',
                                        color: azulEscuroMain,
                                        fontSize: '11px'
                                    }}
                                >
                                    Add Voc
                                </Typography>
                            )}
                            {open && <ListItemText primary="Create VOC" sx={{color: azulEscuroMain}}/>}
                        </ListItemButton>
                    </Tooltip>
                </List>

                {notStudent(role) && (
                    <>
                        <StyledDivider/>
                        <List>
                            <Tooltip title="Sections" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={SECTION}
                                    className={linkClass(SECTION)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <ClassOutlined/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            Sections
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Sections" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                            <Tooltip title="Create Section" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={CREATE_SECTION}
                                    className={linkClass(CREATE_SECTION)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <AddCircleOutlineOutlined/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            New Sec
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Create Section" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                            <Tooltip title="Create Tech" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={CREATE_TECH}
                                    className={linkClass(CREATE_TECH)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <AddCircleOutlineOutlined/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            New Tech
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Create Tech" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                            <Tooltip title="Manage Tech" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={MANAGE_TECH}
                                    className={linkClass(MANAGE_TECH)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <ManageHistory/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            Mng Tech
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Manage Tech" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                            <Tooltip title="Manage Voc" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={MANAGE_VOC}
                                    className={linkClass(MANAGE_VOC)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <ManageHistory/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            Mng Voc
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Manage Voc" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                        </List>
                    </>
                )}

                {role === 'ADMIN' && (
                    <>
                        <StyledDivider/>
                        <List>
                            <Tooltip title="ManageSection" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={MANAGE_SECTIONS}
                                    className={linkClass(MANAGE_SECTIONS)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <AccountBoxOutlined/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            Mng Sec
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Manage Sections" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                            <Tooltip title="Manage Users" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={WebUris.MANAGE_USERS}
                                    className={linkClass(WebUris.MANAGE_USERS)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <ManageAccountsOutlined/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            Mng User
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Manage Users" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                            <Tooltip title="Manage Departments" placement="right">
                                <ListItemButton
                                    component={Link}
                                    to={WebUris.MANAGE_DEPARTMENT}
                                    className={linkClass(WebUris.MANAGE_DEPARTMENT)}
                                    sx={!open && {
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        textAlign: 'center',
                                    }}
                                >
                                    <ListItemIcon
                                        sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                                    >
                                        <ManageSearchOutlined/>
                                    </ListItemIcon>
                                    {!open && (
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                mt: 1,
                                                whiteSpace: 'nowrap',
                                                overflow: 'hidden',
                                                textOverflow: 'ellipsis',
                                                color: azulEscuroMain,
                                                fontSize: '11px'
                                            }}
                                        >
                                            Mng Depart
                                        </Typography>
                                    )}
                                    {open && <ListItemText primary="Manage Depart" sx={{color: azulEscuroMain}}/>}
                                </ListItemButton>
                            </Tooltip>
                        </List>
                    </>
                )}
                <StyledDivider/>
                <Tooltip title="Logout" placement="right">
                    <ListItemButton
                        onClick={handleLogout}
                        className={linkClass('/logout')}
                        sx={!open && {
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            textAlign: 'center',
                        }}
                    >
                        <ListItemIcon
                            sx={{color: azulEscuroMain, justifyContent: !open ? 'center' : ''}}
                        >
                            <InboxIcon/>
                        </ListItemIcon>
                        {!open && (
                            <Typography
                                variant="caption"
                                sx={{
                                    mt: 1,
                                    whiteSpace: 'nowrap',
                                    overflow: 'hidden',
                                    textOverflow: 'ellipsis',
                                    color: azulEscuroMain,
                                    fontSize: '11px'
                                }}
                            >
                                Logout
                            </Typography>
                        )}
                        {open && <ListItemText primary="Logout" sx={{color: azulEscuroMain}}/>}
                    </ListItemButton>
                </Tooltip>
            </Drawer>
            <Box component="main" sx={{flexGrow: 1, p: 3}}>
                <div style={{height: '64px'}}/>
                {children}
            </Box>
        </Box>
    );
}
