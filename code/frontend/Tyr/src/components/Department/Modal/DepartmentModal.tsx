import * as React from "react";
import {Box, Button, CircularProgress, Modal, TextField, Typography} from "@mui/material";
import {modalStyle} from "../../../utils/HardCoded";
import {Department} from "../../../services/department/models/Department";

/**
 * Modal to add or edit a department
 */
const DepartmentModal = (props: DepartmentModalProps) => {
    const {
        show,
        onHide,
        department,
        setDepartment,
        onSave,
        loading
    } = props;

    return (
        <Modal open={show} onClose={onHide}>
            <Box sx={{...modalStyle, width: 400}}>
                <Typography variant="h6" component="h2" sx={{color: 'black'}}>
                    {department?.id ? 'Edit Department' : 'Add Department'}
                </Typography>
                <TextField
                    fullWidth
                    label="Department Name"
                    value={department?.name || ''}
                    onChange={(e) => setDepartment && setDepartment({...department, name: e.target.value})}
                    margin="normal"
                />
                <TextField
                    fullWidth
                    label="Department Description"
                    value={department?.description || ''}
                    onChange={(e) => setDepartment && setDepartment({...department, description: e.target.value})}
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
 * Props for the DepartmentModal component
 *
 * @param show - boolean to show the modal
 * @param onHide - function to close the modal
 * @param department - the department to edit
 * @param setDepartment - function to set the department
 * @param onSave - function to save the department
 * @param loading - boolean to disable the button
 */
interface DepartmentModalProps {
    show: boolean;
    onHide: () => void;
    department: Department | null;
    setDepartment: (department: Department) => void;
    onSave: () => void;
    loading: boolean;
}

export default DepartmentModal;