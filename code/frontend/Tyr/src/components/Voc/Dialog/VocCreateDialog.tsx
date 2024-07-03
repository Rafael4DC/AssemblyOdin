import {
    Box, Button, Dialog, DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    InputBase,
    List,
    ListItemButton,
    ListItemText
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import * as React from "react";
import {User} from "../../../services/user/models/User";
import {ChangeEvent} from "react";

/**
 * Dialog to create a voc
 */
const VocCreateDialog = (props: VocCreateDialogProps) => {
    const {
        open,
        handleClose,
        searchQuery,
        handleSearchChange,
        filteredStudents,
        handleStudentSelect
    } = props;

    return (
        <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
            <DialogTitle>Select Student</DialogTitle>
            <DialogContent>
                <Box sx={{display: 'flex', alignItems: 'center', marginBottom: 2}}>
                    <InputBase
                        placeholder="Search students"
                        value={searchQuery}
                        onChange={handleSearchChange}
                        sx={{flex: 1, paddingLeft: 1}}
                    />
                    <IconButton type="submit" sx={{p: '10px'}}>
                        <SearchIcon/>
                    </IconButton>
                </Box>
                <List>
                    {filteredStudents.map(student => (
                        <ListItemButton
                            key={student.id}
                            onClick={() => handleStudentSelect(student)}>
                            <ListItemText primary={student.username}/>
                        </ListItemButton>
                    ))}
                </List>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} color="primary">
                    Cancel
                </Button>
            </DialogActions>
        </Dialog>
    )
}

interface VocCreateDialogProps {
    open: boolean;
    handleClose: () => void;
    searchQuery: string;
    handleSearchChange: (e: ChangeEvent<HTMLInputElement>) => void;
    filteredStudents: User[];
    handleStudentSelect: (student: User) => void;

}

export default VocCreateDialog;