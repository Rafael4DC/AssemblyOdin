import {Dialog, DialogContent, DialogTitle, Typography} from "@mui/material";
import * as React from "react";

/**
 * Dialog to display information about a timetable event
 */
const TimeTableDialog = (props: TimeTableDialogProps) => {
    const {
        open,
        handleClose,
        selectedEvent
    } = props;

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>
                {selectedEvent ? (selectedEvent.section.module.name ? selectedEvent.section.module.name : 'Event') : ''}</DialogTitle>
            <DialogContent>
                <Typography>Summary: {selectedEvent ? selectedEvent.summary || selectedEvent.description : ''}</Typography>
                {(selectedEvent && selectedEvent.attended !== undefined) && <Typography
                >Attended: {(selectedEvent.attended ? 'Yes' : 'No')}</Typography>}
                {(selectedEvent && selectedEvent.approved !== undefined) && <Typography
                >Approved: {(selectedEvent.approved ? 'Yes' : 'No')}</Typography>}
            </DialogContent>
        </Dialog>
    )
}

/**
 * Props for the TimeTableDialog component
 *
 * @param open - boolean to show the dialog
 * @param handleClose - function to close the dialog
 * @param selectedEvent - the selected event to display
 */
interface TimeTableDialogProps {
    open: boolean;
    handleClose: () => void;
    selectedEvent: any;
}

export default TimeTableDialog;