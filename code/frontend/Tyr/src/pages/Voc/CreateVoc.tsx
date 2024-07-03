import * as React from 'react';
import {Box, Button, Container, Typography,} from '@mui/material';
import useCreateVoc from "../../hooks/Voc/useCreateVoc";
import {notStudent} from "../../utils/Utils";
import {Spinner} from "../../utils/Spinner";
import {AlertDialog} from "../../utils/AlertDialog";
import {User} from '../../services/user/models/User';
import VocCreateDialog from "../../components/Voc/Dialog/VocCreateDialog";
import {useTheme} from "@mui/material/styles";
import VocFormFields from "../../components/Voc/Field/VocFormFields";
import {useState} from "react";

/**
 * Page to create a voc
 */
const CreateVoc = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        vocData,
        setVocData,
        state,
        role,
        sections,
        students,
        handleSubmit,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        handleStudentChange,
    } = useCreateVoc();

    const [open, setOpen] = useState(false);
    const [searchQuery, setSearchQuery] = useState("");


    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const handleStudentSelect = (student: User) => {
                setVocData({...vocData, user: student});
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
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color: customColor}}>
                        Create VOC Class
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2}}>
                        <form onSubmit={handleSubmit}>
                            <VocFormFields
                                vocData={vocData}
                                sections={sections}
                                handleInputChange={handleInputChange}
                                handleDateChange={handleDateChange}
                                handleTimeChange={handleTimeChange}
                                handleSectionChange={handleSectionChange}
                            />
                            {notStudent(role) && (
                                <>
                                    <Box marginTop={2}>
                                        <Button variant="contained" color="primary" onClick={handleOpen}>
                                            Select Students
                                        </Button>
                                        <VocCreateDialog
                                            open={open}
                                            handleClose={handleClose}
                                            searchQuery={searchQuery}
                                            handleSearchChange={handleSearchChange}
                                            filteredStudents={filteredStudents}
                                            handleStudentSelect={handleStudentSelect}
                                        />
                                    </Box>
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
}

export default CreateVoc;
