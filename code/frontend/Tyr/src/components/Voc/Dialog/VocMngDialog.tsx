import {Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem, TextField} from "@mui/material";
import {commonTextFieldProps} from "../../../utils/Utils";
import {times} from "../../../utils/HardCoded";
import * as React from "react";
import {Voc} from "../../../services/voc/models/Voc";
import {Section} from "../../../services/section/models/Section";
import {ChangeEvent, FormEvent} from "react";
import VocFormFields from "../Field/VocFormFields";

/**
 * Dialog to edit a VOC class
 */
const VocMngDialog = (props: VocDialogProps) => {
    const {
        selectedVoc,
        vocData,
        sections,
        handleInputChange,
        handleDateChange,
        handleTimeChange,
        handleSectionChange,
        handleClose,
        handleSubmit
    } = props;

    return (
        <Dialog open={!!selectedVoc} onClose={handleClose} fullWidth maxWidth="sm">
            <DialogTitle>Edit VOC Class</DialogTitle>
            <DialogContent>
                <form onSubmit={handleSubmit}>
                    <VocFormFields
                        vocData={vocData}
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
                <Button onClick={handleSubmit} color="primary">
                    Save Changes
                </Button>
            </DialogActions>
        </Dialog>
        )
}

interface VocDialogProps {
    selectedVoc: Voc;
    vocData: Voc;
    sections: Section[];
    handleInputChange: (e: ChangeEvent<HTMLInputElement>) => void;
    handleDateChange: (e: ChangeEvent<HTMLInputElement>) => void;
    handleTimeChange: (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, type: string) => void;
    handleSectionChange: (e: ChangeEvent<HTMLInputElement>) => void;
    handleClose: () => void;
    handleSubmit: (e: FormEvent) => void;
}

export default VocMngDialog;