import * as React from 'react';
import { useState } from 'react';
import {
    Container,
    TextField,
    Button,
    FormControlLabel,
    MenuItem,
    Typography,
    Box,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Checkbox,
    List,
    ListItemButton,
    ListItemText, InputBase, IconButton, TextFieldProps,
} from '@mui/material';
import useCreateVocClass from "../../hooks/useCreateVocClass";
import { commonTextFieldProps, notStudent } from "../../utils/Utils";
import { Spinner } from "../../utils/Spinner";
import { AlertDialog } from "../../utils/AlertDialog";
import { User } from '../../services/user/models/User';
import SearchIcon from "@mui/icons-material/Search";

/**
 * Page to create a voc class
 */
const CreateVocClass = () => {
    const {
        vocData,
        setVocData,
        state,
        role,
        sections,
        students,
        handleSubmit,
        handleSectionChange,
        handleDateChange,
        handleStudentChange,
    } = useCreateVocClass();

    const [open, setOpen] = React.useState(false);
    const [searchQuery, setSearchQuery] = React.useState("");


    switch (state.type) {
        case 'loading':
            return <Spinner />;

        case 'error':
            return <AlertDialog alert={state.message} />;

        case 'success':
            const handleStudentSelect = (student: User) => {
                setVocData({ ...vocData, user: student });
                handleClose();
            };

            const handleOpen = () => setOpen(true);
            const handleClose = () => setOpen(false);

            const handleSearchChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
                setSearchQuery(event.target.value);
            };

            const filteredStudents = students.filter(student =>
                student.username.toLowerCase().includes(searchQuery.toLowerCase())
            );


            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"}>
                        Create VOC Class
                    </Typography>
                    <Box sx={{ backgroundColor: 'white', padding: 3, borderRadius: 2, color: '#000' }}>
                        <form onSubmit={handleSubmit}>
                            <TextField
                                label="Description"
                                required
                                value={vocData.description}
                                onChange={(e) => setVocData({ ...vocData, description: e.target.value })}
                                {...commonTextFieldProps}
                            />

                            <TextField
                                label="Start"
                                type="datetime-local"
                                required
                                value={vocData.started}
                                onChange={(e) => handleDateChange(e, 'started')}
                                {...commonTextFieldProps}
                            />

                            <TextField
                                label="End"
                                type="datetime-local"
                                required
                                value={vocData.ended}
                                onChange={(e) => handleDateChange(e, 'ended')}
                                {...commonTextFieldProps}
                            />

                            <TextField
                                select
                                label="Section"
                                required
                                value={vocData.section.id.toString()}
                                onChange={(e) => handleSectionChange(e)}
                                {...commonTextFieldProps}
                            >
                                <MenuItem value="" sx={{ color: '#000' }}>
                                    <em>Choose The Section</em>
                                </MenuItem>
                                {sections && sections.map(section => (
                                    <MenuItem key={section.id} value={section.id} sx={{ color: '#000' }}>
                                        {section.name}
                                    </MenuItem>
                                ))}
                            </TextField>

                            {notStudent(role) && (
                                <>
                                <Box marginTop={2}>
                                    <Button variant="contained" color="primary" onClick={handleOpen}>
                                        Select Students
                                    </Button>

                                    <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
                                        <DialogTitle sx={{ color: '#000' }}>Select Students</DialogTitle>
                                        <DialogContent>
                                            <Box sx={{ display: 'flex', alignItems: 'center', marginBottom: 2 }}>
                                                <InputBase
                                                    placeholder="Search students"
                                                    value={searchQuery}
                                                    onChange={handleSearchChange}
                                                    sx={{ flex: 1, paddingLeft: 1, color: '#000' }}
                                                />
                                                <IconButton type="submit" sx={{ p: '10px', color: '#000' }} >
                                                    <SearchIcon />
                                                </IconButton>
                                            </Box>
                                            <List>
                                                {filteredStudents.map(student => (
                                                    <ListItemButton
                                                        key={student.id}
                                                        sx={{ color: '#000' }}
                                                        onClick={() => handleStudentSelect(student)}>
                                                        <ListItemText primary={student.username} sx={{ color: '#000' }} />
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
                                </Box>
                                    <FormControlLabel
                                        control={
                                            <Checkbox
                                                checked={vocData.approved}
                                                onChange={(e) => setVocData({ ...vocData, approved: e.target.checked })}
                                                style={{ color: '#000' }}
                                            />
                                        }
                                        label="Approved"
                                        style={{ color: '#000' }}
                                    />
                                </>
                            )}

                            <Box marginTop={2}>
                                <Button variant="contained" color="primary" type="submit">
                                    Create VOC Class
                                </Button>
                            </Box>
                        </form>
                    </Box>
                </Container>
            );
    }
};

export default CreateVocClass;
