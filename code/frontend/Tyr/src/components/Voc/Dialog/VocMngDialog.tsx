import {Button, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import * as React from "react";
import {ChangeEvent, FormEvent} from "react";
import {Voc} from "../../../services/voc/models/Voc";
import {Section} from "../../../services/section/models/Section";
import VocFormFields from "../Field/VocFormFields";

/**
 * Dialog to edit a VOC class
 */
const VocMngDialog = (props: VocDialogProps) => {
    const {
        selectedVoc,
        sections,
        handleInputChange,
        handleDateChange,
        handleTimeChange,
        handleSectionChange,
        handleClose,
        handleSubmit,
        loading
    } = props;

    return (
        <Dialog open={!!selectedVoc} onClose={handleClose} fullWidth maxWidth="sm">
            <DialogTitle>Edit VOC Class</DialogTitle>
            <DialogContent>
                <form onSubmit={handleSubmit}>
                    <VocFormFields
                        selectedVoc={selectedVoc}
                        sections={sections}
                        handleInputChange={handleInputChange}
                        handleDateChange={handleDateChange}
                        handleTimeChange={handleTimeChange}
                        handleSectionChange={handleSectionChange}
                    />
                </form>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} color="primary">
                    Cancel
                </Button>
                <Button onClick={handleSubmit} color="primary" disabled={loading}>
                    Save Changes
                </Button>
            </DialogActions>
        </Dialog>
    )
}

/**
 * Props for the VocMngDialog component
 *
 * @param selectedVoc - the selected voc to edit
 * @param sections - list of sections to display
 * @param handleInputChange - function to handle input change
 * @param handleDateChange - function to handle date change
 * @param handleTimeChange - function to handle time change
 * @param handleSectionChange - function to handle section change
 * @param handleClose - function to close the dialog
 * @param handleSubmit - function to handle form submission
 * @param loading - boolean to show loading state
 */
interface VocDialogProps {
    selectedVoc: Voc;
    sections: Section[];
    handleInputChange: (e: ChangeEvent<HTMLInputElement>) => void;
    handleDateChange: (e: ChangeEvent<HTMLInputElement>) => void;
    handleTimeChange: (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, type: string) => void;
    handleSectionChange: (e: ChangeEvent<HTMLInputElement>) => void;
    handleClose: () => void;
    handleSubmit: (e: FormEvent) => void;
    loading: boolean;
}

export default VocMngDialog;