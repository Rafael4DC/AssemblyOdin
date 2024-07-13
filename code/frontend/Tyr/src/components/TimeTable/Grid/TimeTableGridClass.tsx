import {DaySlot, EventSlot} from "../../../utils/StyledComponents";
import {Grid, Typography} from "@mui/material";
import {times} from "../../../utils/HardCoded";
import {differenceInMinutes, format, parseISO} from "date-fns";
import * as React from "react";


/**
 * Grid for the timetable class
 */
const TimeTableGridClass = (props: TimeTableGridClassProps) => {
    const {
        filteredEvents,
        day,
        dayIndex,
        handleClickOpen
    } = props;

    return (
        <Grid item xs={1.2} key={dayIndex} sx={{borderTop: '1px solid white'}}>
            <DaySlot>
                <Typography>{day}</Typography>
            </DaySlot>
            {times.map((time, timeIndex) => {
                /**
                 * Skip slots that are covered by a multi-hour event
                 * If the current slot is within the duration of a previous event, skip rendering
                 */
                const previousEvent = filteredEvents.find(event => {
                    const eventStart = parseISO(event.started);
                    const eventEnd = parseISO(event.ended);
                    const eventDay = format(eventStart, 'EEE');

                    const eventStartIndex = times.indexOf(format(eventStart, 'HH:mm'));
                    const eventEndIndex = eventStartIndex + Math.ceil(differenceInMinutes(eventEnd, eventStart) / 60);

                    return eventDay === day && timeIndex > eventStartIndex && timeIndex < eventEndIndex;
                });

                if (previousEvent) return null;

                /**
                 * For each time slot, check if there is an event that starts at this time and day
                 * If there is an event, render the event slot with the event details
                 */
                const event = filteredEvents.find(event => {
                    const eventStart = parseISO(event.started);
                    const eventDay = format(eventStart, 'EEE');
                    const eventTime = format(eventStart, 'HH:mm');

                    return eventDay === day && eventTime === time;
                });

                if (event) {
                    const eventStart = parseISO(event.started);
                    const eventEnd = parseISO(event.ended);
                    const span = Math.ceil(differenceInMinutes(eventEnd, eventStart) / 60);

                    return (
                        <EventSlot
                            key={timeIndex}
                            color={'description' in event ? "#FFD700" : "#FF5733"}
                            style={{height: `${50 * span}px`}}
                            onClick={() => handleClickOpen(event)}
                        >
                            <Typography>{event.section.module.name}</Typography>
                        </EventSlot>
                    );
                }

                return (
                    <EventSlot
                        key={timeIndex}
                    />
                );
            })}
        </Grid>
    )
}

/**
 * Props for the TimeTableGridClass component
 *
 * @param filteredEvents - list of events to display
 * @param day - the day to display
 * @param dayIndex - the index of the day
 * @param handleClickOpen - function to handle when an event is clicked
 */
interface TimeTableGridClassProps {
    filteredEvents: any[];
    day: string;
    dayIndex: number;
    handleClickOpen: (event: any) => void;
}

export default TimeTableGridClass;