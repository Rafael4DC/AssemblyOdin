import * as React from 'react';
import {CSSObject, styled, Theme, useTheme} from '@mui/material/styles';
import Box from '@mui/material/Box';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar, {AppBarProps as MuiAppBarProps} from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import {Link} from 'react-router-dom';
import Tooltip from '@mui/material/Tooltip'; // Import Tooltip
import {WebUris} from "../utils/WebUris";
import {useSessionData} from "../session/Session";
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import {Class, ManageAccounts, ManageHistory, ManageSearch, NoteAdd, School} from "@mui/icons-material";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import DEPARTMENTS = WebUris.DEPARTMENTS;
import MANAGE_CLASSES = WebUris.MANAGE_CLASSES;
import CREATE_SECTION = WebUris.CREATE_SECTION;
import SECTION = WebUris.SECTION;
import CREATE_VOC = WebUris.CREATE_VOC;
import CREATE_TECH = WebUris.CREATE_TECH;

const drawerWidth = 210;

const openedMixin = (theme: Theme): CSSObject => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
});

const closedMixin = (theme: Theme): CSSObject => ({
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: `calc(${theme.spacing(7)} + 1px)`,
    [theme.breakpoints.up('sm')]: {
        width: `calc(${theme.spacing(8)} + 1px)`,
    },
});

const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
}));

interface AppBarProps extends MuiAppBarProps {
    open?: boolean;
}

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})<AppBarProps>(({theme, open}) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    backgroundColor: '#333',  // Dark grey background
    color: '#fff',  // White text color
    ...(open && {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const Drawer = styled(MuiDrawer, {shouldForwardProp: (prop) => prop !== 'open'})(
    ({theme, open}) => ({
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
        boxSizing: 'border-box',
        ...(open && {
            ...openedMixin(theme),
            '& .MuiDrawer-paper': openedMixin(theme),
        }),
        ...(!open && {
            ...closedMixin(theme),
            '& .MuiDrawer-paper': closedMixin(theme),
        }),
    }),
);

const StyledDivider = styled(Divider)(({theme}) => ({
    margin: theme.spacing(2, 0),
    backgroundColor: '#333', // Dark grey color for divider
    height: 2, // Increase the thickness
}));

export default function Dashboard({children}: { children: React.ReactNode }) {
    const theme = useTheme();
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
        // Handle the logout functionality here
        console.log("Logging out...");
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
                        }}
                    >
                        <MenuIcon/>
                    </IconButton>
                    {!open && (
                        <Typography variant="h6" noWrap component="div">
                            <Link to={HOME} style={{textDecoration: 'none', color: '#fff'}}>
                                Assembly
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
                                Assembly
                            </Link>
                        </Typography>
                    )}
                    <IconButton onClick={handleDrawerClose}>
                        {theme.direction === 'rtl' ? <ChevronRightIcon/> : <ChevronLeftIcon/>}
                    </IconButton>
                </DrawerHeader>
                <List>
                    <Tooltip title="Profile" placement="right">
                        <ListItemButton component={Link} to={PROFILE} className={linkClass(PROFILE)}>
                            <ListItemIcon sx={{color: 'inherit'}}><AccountBoxIcon/></ListItemIcon>
                            <ListItemText primary="Profile"/>
                        </ListItemButton>
                    </Tooltip>
                    <Tooltip title="Departments" placement="right">
                        <ListItemButton component={Link} to={DEPARTMENTS} className={linkClass(DEPARTMENTS)}>
                            <ListItemIcon sx={{color: 'inherit'}}><School/></ListItemIcon>
                            <ListItemText primary="Departments"/>
                        </ListItemButton>
                    </Tooltip>
                    <Tooltip title="Create VOC" placement="right">
                        <ListItemButton component={Link} to={CREATE_VOC} className={linkClass(CREATE_VOC)}>
                            <ListItemIcon sx={{color: 'inherit'}}><NoteAdd/></ListItemIcon>
                            <ListItemText primary="Create VOC"/>
                        </ListItemButton>
                    </Tooltip>
                </List>
                <StyledDivider/>
                {(role === 'TEACHER' || role === 'ADMIN') && (
                    <List>
                        <Tooltip title="Sections" placement="right">
                            <ListItemButton component={Link} to={SECTION} className={linkClass(SECTION)}>
                                <ListItemIcon sx={{color: 'inherit'}}><Class/></ListItemIcon>
                                <ListItemText primary="Sections"/>
                            </ListItemButton>
                        </Tooltip>
                        <Tooltip title="Create Section" placement="right">
                            <ListItemButton component={Link} to={CREATE_SECTION} className={linkClass(CREATE_SECTION)}>
                                <ListItemIcon sx={{color: 'inherit'}}><NoteAdd/></ListItemIcon>
                                <ListItemText primary="Create Section"/>
                            </ListItemButton>
                        </Tooltip>
                        <Tooltip title="Create Tech" placement="right">
                            <ListItemButton component={Link} to={CREATE_TECH} className={linkClass(CREATE_TECH)}>
                                <ListItemIcon sx={{color: 'inherit'}}><NoteAdd/></ListItemIcon>
                                <ListItemText primary="Create Tech"/>
                            </ListItemButton>
                        </Tooltip>
                        <Tooltip title="Manage Class" placement="right">
                            <ListItemButton component={Link} to={MANAGE_CLASSES} className={linkClass(MANAGE_CLASSES)}>
                                <ListItemIcon sx={{color: 'inherit'}}><ManageHistory/></ListItemIcon>
                                <ListItemText primary="Manage Class"/>
                            </ListItemButton>
                        </Tooltip>
                    </List>
                )}
                <StyledDivider/>
                {role === 'ADMIN' && (
                    <List>
                        <Tooltip title="Manage Class" placement="right">
                            <ListItemButton component={Link} to={WebUris.MANAGE_CLASSES}
                                            className={linkClass(WebUris.MANAGE_CLASSES)}>
                                <ListItemIcon sx={{color: 'inherit'}}><ManageHistory/></ListItemIcon>
                                <ListItemText primary="Manage Class"/>
                            </ListItemButton>
                        </Tooltip>
                        <Tooltip title="Manage Users" placement="right">
                            <ListItemButton component={Link} to={WebUris.MANAGE_USERS}
                                            className={linkClass(WebUris.MANAGE_USERS)}>
                                <ListItemIcon sx={{color: 'inherit'}}><ManageAccounts/></ListItemIcon>
                                <ListItemText primary="Manage Users"/>
                            </ListItemButton>
                        </Tooltip>
                        <Tooltip title="Manage Departments" placement="right">
                            <ListItemButton component={Link} to={WebUris.DEPARTMENT_MANAGER}
                                            className={linkClass(WebUris.DEPARTMENT_MANAGER)}>
                                <ListItemIcon sx={{color: 'inherit'}}><ManageSearch/></ListItemIcon>
                                <ListItemText primary="Manage Depart"/>
                            </ListItemButton>
                        </Tooltip>
                    </List>
                )}
                <StyledDivider/>
                <Tooltip title="Logout" placement="right">
                    <ListItemButton onClick={handleLogout} className={linkClass('/logout')}>
                        <ListItemIcon sx={{color: 'inherit'}}><InboxIcon/></ListItemIcon>
                        <ListItemText primary="Logout"/>
                    </ListItemButton>
                </Tooltip>
            </Drawer>
            <Box component="main" sx={{flexGrow: 1, p: 3}}>
                <div style={{height: '64px'}}/>
                {/* Added div to avoid overlap */}
                {children}
            </Box>
        </Box>
    );
}
