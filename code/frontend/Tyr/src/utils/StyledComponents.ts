import {styled} from "@mui/system";
import {Paper} from "@mui/material";

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
