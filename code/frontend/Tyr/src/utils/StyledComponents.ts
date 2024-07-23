import {Paper} from "@mui/material";
import {CSSObject, styled, Theme} from '@mui/material/styles';
import MuiAppBar, {AppBarProps as MuiAppBarProps} from '@mui/material/AppBar';
import MuiDrawer from '@mui/material/Drawer';
import Divider from '@mui/material/Divider';


export const Root = styled(Paper)(({theme}) => ({
    padding: theme.spacing(1),
    backgroundColor: 'transparent',
    color: '#fff',
    boxShadow: 'none',
    border: 'none',
}));

export const TimeSlot = styled('div')(({theme}) => ({
    borderBottom: `1px solid white`,
    borderRight: '1px solid white',
    height: '50px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#0D0D40',
    color: '#F70088',
    fontWeight: 'bold',
}));

export const DaySlot = styled(TimeSlot)(({theme}) => ({
    backgroundColor: '#F70088',
    color: '#fff',
    height: '50px',
    fontWeight: 'bold',
    borderRight: '1px solid white',
}));

export const EventSlot = styled('div')<{ color?: string; span?: number }>(({theme, color, span}) => ({
    borderBottom: `1px solid white`,
    borderRight: '1px solid white',
    height: '50px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: color || '#0D0D40',
    color: "#fff",
    fontWeight: 'bold',
    cursor: 'pointer',
}));

const drawerWidth = 220;

export const openedMixin = (theme: Theme): CSSObject => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
});

export const closedMixin = (theme: Theme): CSSObject => ({
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

export const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
}));

interface AppBarProps extends MuiAppBarProps {
    open?: boolean;
}

export const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})<AppBarProps>(({theme, open}) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    backgroundColor: '#fff', // Use theme background color
    color: theme.palette.text.primary, // Use theme text color
    ...(open && {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

export const Drawer = styled(MuiDrawer, {shouldForwardProp: (prop) => prop !== 'open'})(
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

export const StyledDivider = styled(Divider)(({theme}) => ({
    margin: theme.spacing(1, 0),
    backgroundColor: theme.palette.azulEscuro.main, // Dark grey color for divider
    height: 1, // Increase the thickness
}));


