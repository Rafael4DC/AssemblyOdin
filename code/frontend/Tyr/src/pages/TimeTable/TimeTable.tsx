import * as React from 'react';
import {useState} from 'react';
import {Button, Dialog, DialogContent, DialogTitle, Grid, Paper, Typography} from '@mui/material';
import {styled} from '@mui/system';
import {useTheme} from "@mui/material/styles";
import {differenceInMinutes, endOfWeek, format, isWithinInterval, parseISO, startOfWeek} from 'date-fns';
import useMyTechs from "../../hooks/Tech/useMyTechs";
import {Spinner} from "../../utils/Spinner";
import {times, weekDaysShort} from "../../utils/HardCoded";
import useMyVocs from "../../hooks/Voc/useMyVocs";
import {DaySlot, EventSlot, Root, TimeSlot} from "../../utils/StyledComponents";
import {AlertDialog} from "../../utils/AlertDialog";
import {Voc} from "../../services/voc/models/Voc";
import {Tech} from "../../services/tech/models/Tech";
import {MILLISECONDS_IN_A_WEEK} from "../../utils/Utils";
import TimeTableDialog from "../../components/TimeTable/Dialog/TimeTableDialog";
import TimeTableGridClass from "../../components/TimeTable/Grid/TimeTableGridClass";

/**
 * Page to display the timetable
 */
const Timetable = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;

    const {state: MyTechsState} = useMyTechs();
    const {state: MyVocsState} = useMyVocs();

    const [weekOffset, setWeekOffset] = useState(0);
    const [open, setOpen] = useState(false);
    const [selectedEvent, setSelectedEvent] = useState<any/*Tech | Voc*/>(null);

    switch (true) {
        case MyTechsState.type === 'loading' || MyVocsState.type === 'loading':
            return <Spinner/>;

        case MyTechsState.type === 'error':
            return <AlertDialog alert={MyTechsState.message}/>;

        case MyVocsState.type === 'error':
            return <AlertDialog alert={MyVocsState.message}/>;

        case MyTechsState.type === 'success' && MyVocsState.type === 'success':
            const techs = MyTechsState.techs;
            const vocs = MyVocsState.vocs;

            const currentWeekStart = startOfWeek(new Date(), {weekStartsOn: 1}).getTime();
            const currentWeekEnd = endOfWeek(new Date(), {weekStartsOn: 1}).getTime();

            const getWeekStart = (weekOffset: number) =>
                startOfWeek(
                    new Date(currentWeekStart + weekOffset * MILLISECONDS_IN_A_WEEK), {weekStartsOn: 1}
                );
            const getWeekEnd = (weekOffset: number) =>
                endOfWeek(
                    new Date(currentWeekEnd + weekOffset * MILLISECONDS_IN_A_WEEK), {weekStartsOn: 1}
                );

            const handleNextWeek = () => setWeekOffset(weekOffset + 1);
            const handlePrevWeek = () => setWeekOffset(weekOffset - 1);

            const allEvents = [...techs, ...vocs];

            const filteredEvents = allEvents.filter(event => {
                const eventStart = parseISO(event.started);
                const eventEnd = parseISO(event.ended);
                const weekStart = getWeekStart(weekOffset);
                const weekEnd = getWeekEnd(weekOffset);

                return isWithinInterval(eventStart, {start: weekStart, end: weekEnd}) ||
                    isWithinInterval(eventEnd, {start: weekStart, end: weekEnd});
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
                    <Typography variant="h6" align="center" gutterBottom sx={{color: customColor}}>
                        Week {format(getWeekStart(weekOffset), 'MMM dd')}
                             - {format(getWeekEnd(weekOffset), 'MMM dd')}
                    </Typography>
                    <Grid container justifyContent="center" spacing={0}>
                        <Grid item xs={1} sx={{borderLeft: '1px solid white', borderTop: '1px solid white'}}>
                            <DaySlot>
                                <Typography>Time</Typography>
                            </DaySlot>
                            {times.map((time, index) => (
                                <TimeSlot key={index}>
                                    <Typography>{time}</Typography>
                                </TimeSlot>
                            ))}
                        </Grid>
                        {weekDaysShort.map((day, dayIndex) => (
                            <TimeTableGridClass
                                filteredEvents={filteredEvents}
                                day={day}
                                dayIndex={dayIndex}
                                handleClickOpen={handleClickOpen}
                            />
                        ))}
                    </Grid>

                    <Grid container justifyContent="center" spacing={2} sx={{marginTop: 2}}>
                        <Grid item>
                            <Button variant="contained" onClick={handlePrevWeek}>Previous Week</Button>
                        </Grid>
                        <Grid item>
                            <Button variant="contained" onClick={handleNextWeek}>Next Week</Button>
                        </Grid>
                    </Grid>

                    <TimeTableDialog
                        open={open}
                        handleClose={handleClose}
                        selectedEvent={selectedEvent}
                    />
                </Root>
            );
    }
};

export default Timetable;
