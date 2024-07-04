import * as React from 'react';
import {
    Box,
    Button,
    Checkbox,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    InputBase,
    ListItem,
    MenuItem,
    TextField,
    Typography
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import {Spinner} from '../../utils/Spinner';
import {AlertDialog} from '../../utils/AlertDialog';
import {commonTextFieldProps} from "../../utils/Utils";
import ListItemText from "@mui/material/ListItemText";
import List from "@mui/material/List";
import useManageSections from "../../hooks/Section/useManageSection";
import {useTheme} from "@mui/material/styles";
import SectionTableContainer from "../../components/Section/Table/SectionTableContainer";

/**
 * Page to manage sections
 */
const ManageSections = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        selectedSection,
        handleStudentSelect,
        handleSectionClick,
        handleInputChange,
        handleModuleChange,
        searchQuery,
        setSearchQuery,
        handleSubmit,
        handleClose
    } = useManageSections();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const {sections, modules, filteredStudents, loading} = state;

            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color: customColor}}>
                        Manage Sections
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2}}>
                        <SectionTableContainer
                            sections={sections}
                            handleSectionClick={handleSectionClick}
                        />
                        {selectedSection && (
                            <Dialog open={!!selectedSection} onClose={handleClose} fullWidth maxWidth="sm">
                                <DialogTitle>Edit Section</DialogTitle>
                                <DialogContent>
                                    <form onSubmit={handleSubmit}>
                                        <TextField
                                            label="Name"
                                            type="text"
                                            name="name"
                                            required
                                            value={selectedSection.name}
                                            onChange={handleInputChange}
                                            {...commonTextFieldProps}
                                        />
                                        <TextField
                                            label="Module"
                                            select
                                            name="module"
                                            required
                                            value={selectedSection.module.id.toString()}
                                            onChange={handleModuleChange}
                                            {...commonTextFieldProps}
                                        >
                                            <MenuItem value="">
                                                <em>Choose The Module</em>
                                            </MenuItem>
                                            {modules.map(module => (
                                                <MenuItem key={module.id} value={module.id}>
                                                    {module.name}
                                                </MenuItem>
                                            ))}
                                        </TextField>
                                        <Box sx={{display: 'flex', alignItems: 'center', marginBottom: 2}}>
                                            <InputBase
                                                placeholder="Search filteredStudents"
                                                value={searchQuery}
                                                onChange={(e) => setSearchQuery(e.target.value)}
                                                sx={{flex: 1, paddingLeft: 1}}
                                            />
                                            <IconButton sx={{p: '10px'}}>
                                                <SearchIcon/>
                                            </IconButton>
                                        </Box>
                                        <List>
                                            {filteredStudents.map(student => (
                                                <ListItem key={student.id} sx={{color: '#000'}}>
                                                    <Checkbox
                                                        checked={selectedSection.students.some(stu => stu.id === student.id)}
                                                        onChange={() => handleStudentSelect(student)}
                                                    />
                                                    <ListItemText primary={student.username} sx={{color: '#000'}}/>
                                                </ListItem>
                                            ))}
                                        </List>
                                    </form>
                                </DialogContent>
                                <DialogActions>
                                    <Button onClick={handleClose} color="primary">
                                        Cancel
                                    </Button>
                                    <Button onClick={handleSubmit} color="primary" disabled={loading}>
                                        Save Changes
                                    </Button>
                                </DialogActions>
                            </Dialog>
                        )}
                    </Box>
                </Container>
            );
    }
};

export default ManageSections;
