import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    InputBase,
    List
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import * as React from "react";
import {Dispatch, SetStateAction} from "react";
import {User} from "../../../services/user/models/User";
import ListCheckBox from "../../User/List/ListCheckBox";
import ListItembutton from "../../User/List/ListItembutton";

/**
 * Dialog to create a voc
 */
const CreateDialog = (props: CreateDialogProps) => {
    const {
        open,
        handleClose,
        searchQuery,
        setSearchQuery,
        filteredStudents,
        selectedStudents,
        handleStudentSelect,
        title,
        listCheckBox
    } = props;

    return (
        <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <Box sx={{display: 'flex', alignItems: 'center', marginBottom: 2}}>
                    <InputBase
                        placeholder="Search filteredStudents"
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                        sx={{flex: 1, paddingLeft: 1}}
                    />
                    <IconButton type="submit" sx={{p: '10px'}}>
                        <SearchIcon/>
                    </IconButton>
                </Box>
                <List>
                    {filteredStudents.map(student => {
                        if (listCheckBox)
                            return <ListCheckBox
                                key={student.id}
                                student={student}
                                selectedStudents={selectedStudents}
                                handleStudentSelect={handleStudentSelect}
                            />
                        else
                            return <ListItembutton
                                key={student.id}
                                student={student}
                                handleStudentSelect={handleStudentSelect}
                            />
                    })}
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

interface CreateDialogProps {
    open: boolean;
    handleClose: () => void;
    searchQuery: string;
    setSearchQuery: Dispatch<SetStateAction<string>>;
    filteredStudents: User[];
    handleStudentSelect: (student: User) => void;
    selectedStudents?: User[];
    title: string;
    listCheckBox: boolean;
}

export default CreateDialog;