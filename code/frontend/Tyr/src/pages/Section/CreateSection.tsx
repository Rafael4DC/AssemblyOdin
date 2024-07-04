import * as React from 'react';
import {Box, Button, Container, MenuItem, TextField, Typography} from "@mui/material";
import {Spinner} from '../../utils/Spinner';
import {AlertDialog} from '../../utils/AlertDialog';
import useCreateSection from "../../hooks/Section/useCreateSection";
import {commonTextFieldProps} from "../../utils/Utils";
import {useTheme} from "@mui/material/styles";
import CreateDialog from "../../components/Shared/Dialog/CreateDialog";

/**
 * Page to create a section
 */
const CreateSection = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        selectedSection,
        open,
        setOpen,
        searchQuery,
        setSearchQuery,
        handleSubmit,
        handleInputChange,
        handleModuleChange,
        handleStudentSelect
    } = useCreateSection();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const {modules, filteredStudents, loading} = state;

            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color: customColor}}>
                        Create Section
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2}}>
                        <form onSubmit={handleSubmit}>
                            <TextField
                                label="Name"
                                required
                                name="name"
                                value={selectedSection.name}
                                onChange={handleInputChange}
                                {...commonTextFieldProps}
                            />
                            <TextField
                                label="Module"
                                select
                                required
                                value={selectedSection.module.id.toString()}
                                onChange={handleModuleChange}
                                {...commonTextFieldProps}
                            >
                                <MenuItem value="0">
                                    <em>Choose The Module</em>
                                </MenuItem>
                                {modules.map(module => (
                                    <MenuItem key={module.id} value={module.id}>
                                        {module.name}
                                    </MenuItem>
                                ))}
                            </TextField>
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
                                    selectedStudents={selectedSection.students}
                                    title={"Select Students"}
                                    listCheckBox={true}
                                />
                            </Box>
                            <Box marginTop={2}>
                                <Button variant="contained" color="primary" type="submit" disabled={loading}>
                                    Create Section
                                </Button>
                            </Box>
                        </form>
                    </Box>
                </Container>
            );
    }
};

export default CreateSection;
