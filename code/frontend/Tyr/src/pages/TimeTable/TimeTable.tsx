import * as React from 'react';
import { useState } from 'react';
import { Paper, Typography, Grid, Button, Dialog, DialogTitle, DialogContent } from '@mui/material';
import { styled } from '@mui/system';
import { useTheme } from "@mui/material/styles";
import { format, startOfWeek, endOfWeek, isWithinInterval, parseISO, differenceInMinutes } from 'date-fns';
import useMyTechs from "../../hooks/useMyTechs";
import { Spinner } from "../../utils/Spinner";

const Root = styled(Paper)(({ theme }) => ({
    padding: theme.spacing(1),
    backgroundColor: theme.palette.background.default,
    color: '#fff'
}));

const TimeSlot = styled('div')(({ theme }) => ({
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

const DaySlot = styled(TimeSlot)(({ theme }) => ({
    backgroundColor: '#F70088',
    color: '#fff',
    height: '50px',
    fontWeight: 'bold',
    borderRight: '1px solid white',
}));

const EventSlot = styled('div')<{ color?: string; span?: number }>(({ theme, color, span }) => ({
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

const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
const times = [
    '10:00', '11:00', '12:00', '13:00', '14:00',
    '15:00', '16:00', '17:00', '18:00', '19:00',
    '20:00', '21:00',
];

const Timetable = () => {
    const theme = useTheme();

    const { techs } = useMyTechs();

    const [weekOffset, setWeekOffset] = useState(0);
    const [open, setOpen] = useState(false);
    const [selectedEvent, setSelectedEvent] = useState<any>(null);

    if (techs === null) return <Spinner />;

    const currentStartOfWeek = startOfWeek(new Date(), { weekStartsOn: 1 });
    const currentEndOfWeek = endOfWeek(new Date(), { weekStartsOn: 1 });

    const handleNextWeek = () => setWeekOffset(weekOffset + 1);
    const handlePrevWeek = () => setWeekOffset(weekOffset - 1);

    const filteredEvents = techs.filter(event => {
        const eventStart = parseISO(event.started);
        const eventEnd = parseISO(event.ended);
        const weekStart = startOfWeek(new Date(currentStartOfWeek.getTime() + weekOffset * 7 * 24 * 60 * 60 * 1000), { weekStartsOn: 1 });
        const weekEnd = endOfWeek(new Date(currentEndOfWeek.getTime() + weekOffset * 7 * 24 * 60 * 60 * 1000), { weekStartsOn: 1 });

        return isWithinInterval(eventStart, { start: weekStart, end: weekEnd }) ||
            isWithinInterval(eventEnd, { start: weekStart, end: weekEnd });
    });

    const handleClickOpen = (event: any) => {
        setSelectedEvent(event);
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setSelectedEvent(null);
    };

    return (
        <Root>
            <Typography variant="h6" align="center" gutterBottom>
                1º TRIMESTRE - setembro/dezembro 2024 -
                Week {format(startOfWeek(new Date(currentStartOfWeek.getTime() + weekOffset * 7 * 24 * 60 * 60 * 1000), { weekStartsOn: 1 }), 'MMM dd')} - {format(endOfWeek(new Date(currentEndOfWeek.getTime() + weekOffset * 7 * 24 * 60 * 60 * 1000), { weekStartsOn: 1 }), 'MMM dd')}
            </Typography>
            <Grid container justifyContent="center" spacing={2} sx={{ marginBottom: theme.spacing(2) }}>
                <Grid item>
                    <Button variant="contained" sx={{color: '#0D0D40'}} onClick={handlePrevWeek}>Previous Week</Button>
                </Grid>
                <Grid item>
                    <Button variant="contained" color="secondary" onClick={handleNextWeek}>Next Week</Button>
                </Grid>
            </Grid>
            <Grid container justifyContent="center" spacing={0}>
                <Grid item xs={1} sx={{ borderLeft: '1px solid white', borderTop: '1px solid white'}}>
                    <DaySlot>
                        <Typography>Time</Typography>
                    </DaySlot>
                    {times.map((time, index) => (
                        <TimeSlot key={index}>
                            <Typography>{time}</Typography>
                        </TimeSlot>
                    ))}
                </Grid>
                {days.map((day, dayIndex) => (
                    <Grid item xs={1.2} key={dayIndex} sx={{borderTop: '1px solid white'}}>
                        <DaySlot>
                            <Typography>{day}</Typography>
                        </DaySlot>
                        {times.map((time, timeIndex) => {
                            const event = filteredEvents.find(event => {
                                const eventStart = parseISO(event.started);
                                const eventEnd = parseISO(event.ended);
                                const eventDay = format(eventStart, 'EEE');
                                const eventTime = format(eventStart, 'HH:mm');
                                const eventDuration = differenceInMinutes(eventEnd, eventStart) / 60;

                                return eventDay === day && eventTime === time;
                            });

                            if (event) {
                                const eventStart = parseISO(event.started);
                                const eventEnd = parseISO(event.ended);
                                const span = Math.ceil(differenceInMinutes(eventEnd, eventStart) / 60);
                                const slots = Array(span).fill(event.section.module.name);

                                return (
                                    <React.Fragment key={timeIndex}>
                                        {slots.map((slot, index) => (
                                            <EventSlot
                                                key={index}
                                                color={"#FFD700"}
                                                onClick={() => event && handleClickOpen(event)}
                                            >
                                                <Typography>{slot}</Typography>
                                            </EventSlot>
                                        ))}
                                    </React.Fragment>
                                );
                            }

                            return (
                                <EventSlot
                                    key={timeIndex}
                                />
                            );
                        })}
                    </Grid>
                ))}
            </Grid>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>{selectedEvent ? selectedEvent.module : ''}</DialogTitle>
                <DialogContent>
                    <Typography>Summary: {selectedEvent ? selectedEvent.summary : ''}</Typography>
                    <Typography>Attended: {selectedEvent ? (selectedEvent.attended ? 'Yes' : 'No') : ''}</Typography>
                    <Typography>Approved: {selectedEvent ? (selectedEvent.approved ? 'Yes' : 'No') : ''}</Typography>
                </DialogContent>
            </Dialog>
        </Root>
    );
};

export default Timetable;
