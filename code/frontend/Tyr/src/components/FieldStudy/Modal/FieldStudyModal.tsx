import * as React from "react";
import {Box, Button, CircularProgress, Modal, TextField, Typography} from "@mui/material";
import {modalStyle} from "../../../utils/HardCoded";
import {Module} from "../../../services/module/models/Module";
import {FieldStudy} from "../../../services/fieldstudy/models/FieldStudy";

/**
 * Modal to add or edit a field study
 */
const FieldStudyModal = (props: FieldStudyModalProps) => {
    const {
        show,
        onHide,
        fieldStudy,
        setFieldStudy,
        onSave,
        loading
    } = props;

    return (
        <Modal open={show} onClose={onHide}>
            <Box sx={{...modalStyle, width: 400}}>
                <Typography variant="h6" component="h2" sx={{color: 'black'}}>
                    {fieldStudy?.id ? 'Edit FieldStudy' : 'Add FieldStudy'}
                </Typography>
                <TextField
                    fullWidth
                    label="FieldStudy Name"
                    value={fieldStudy?.name || ''}
                    onChange={(e) => setFieldStudy && setFieldStudy({...fieldStudy, name: e.target.value})}
                    margin="normal"
                />
                <TextField
                    fullWidth
                    label="FieldStudy Description"
                    value={fieldStudy?.description || ''}
                    onChange={(e) => setFieldStudy && setFieldStudy({
                        ...fieldStudy,
                        description: e.target.value
                    })}
                    margin="normal"
                    multiline
                    rows={3}
                />
                <Box sx={{mt: 2}}>
                    <Button variant="outlined" onClick={onHide}>
                        Close
                    </Button>
                    <Button variant="contained" onClick={onSave} disabled={loading} sx={{ml: 2}}>
                        {loading ? <CircularProgress size={24}/> : 'Save Changes'}
                    </Button>
                </Box>
            </Box>
        </Modal>
    )
}

interface FieldStudyModalProps {
    show: boolean;
    onHide: () => void;
    fieldStudy: FieldStudy | null;
    setFieldStudy: (fieldStudy: FieldStudy) => void;
    onSave: () => void;
    loading: boolean;
}

export default FieldStudyModal;