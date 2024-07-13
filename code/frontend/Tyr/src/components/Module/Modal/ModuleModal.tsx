import * as React from "react";
import {Box, Button, CircularProgress, Modal, TextField, Typography} from "@mui/material";
import {modalStyle} from "../../../utils/HardCoded";
import {Module} from "../../../services/module/models/Module";

/**
 * Modal to add or edit a module
 */
const ModuleModal = (props: ModuleModalProps) => {
    const {
        show,
        onHide,
        module,
        setModule,
        onSave,
        loading
    } = props;

    return (
        <Modal open={show} onClose={onHide}>
            <Box sx={{...modalStyle, width: 400}}>
                <Typography variant="h6" component="h2" sx={{color: 'black'}}>
                    {module?.id ? 'Edit Module' : 'Add Module'}
                </Typography>
                <TextField
                    fullWidth
                    label="Module Name"
                    value={module?.name || ''}
                    onChange={(e) => setModule && setModule({...module, name: e.target.value})}
                    margin="normal"
                />
                <TextField
                    fullWidth
                    label="Module Description"
                    value={module?.description || ''}
                    onChange={(e) => setModule && setModule({...module, description: e.target.value})}
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

/**
 * Props for the ModuleModal component
 *
 * @param show - boolean to show the modal
 * @param onHide - function to close the modal
 * @param module - the module to edit
 * @param setModule - function to set the module
 * @param onSave - function to save the module
 * @param loading - boolean to show loading spinner
 */
interface ModuleModalProps {
    show: boolean;
    onHide: () => void;
    module: Module | null;
    setModule: (module: Module) => void;
    onSave: () => void;
    loading: boolean;
}

export default ModuleModal;