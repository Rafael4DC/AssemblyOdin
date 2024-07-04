import * as React from 'react';
import {Box, Button, Container, Typography,} from '@mui/material';
import useCreateVoc from "../../hooks/Voc/useCreateVoc";
import {notStudent} from "../../utils/Utils";
import {Spinner} from "../../utils/Spinner";
import {AlertDialog} from "../../utils/AlertDialog";
import CreateDialog from "../../components/Shared/Dialog/CreateDialog";
import {useTheme} from "@mui/material/styles";
import VocFormFields from "../../components/Voc/Field/VocFormFields";

/**
 * Page to create a voc
 */
const CreateVoc = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        selectedVoc,
        open,
        setOpen,
        searchQuery,
        setSearchQuery,
        handleSubmit,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        handleStudentSelect
    } = useCreateVoc();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const {sections, userInfo, filteredStudents, loading} = state;

            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color: customColor}}>
                        Create VOC Class
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2}}>
                        <form onSubmit={handleSubmit}>
                            <VocFormFields
                                selectedVoc={selectedVoc}
                                sections={sections}
                                handleInputChange={handleInputChange}
                                handleDateChange={handleDateChange}
                                handleTimeChange={handleTimeChange}
                                handleSectionChange={handleSectionChange}
                            />
                            {notStudent(userInfo.role.name) && (
                                <Box marginTop={2}>
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        disabled={loading}
                                        onClick={() => setOpen(true)}>
                                        Select Students
                                    </Button>
                                    <CreateDialog
                                        open={open}
                                        handleClose={() => setOpen(false)}
                                        searchQuery={searchQuery}
                                        setSearchQuery={setSearchQuery}
                                        filteredStudents={filteredStudents}
                                        handleStudentSelect={handleStudentSelect}
                                        title={"Select Students"}
                                        listCheckBox={false}
                                    />
                                </Box>
                            )}
                            <Box marginTop={2}>
                                <Button variant="contained" color="primary" type="submit" disabled={loading}>
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
