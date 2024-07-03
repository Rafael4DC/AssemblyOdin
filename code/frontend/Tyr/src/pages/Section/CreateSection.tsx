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
    List,
    ListItem,
    ListItemText,
    MenuItem,
    TextField,
    Typography
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import {Spinner} from '../../utils/Spinner';
import {AlertDialog} from '../../utils/AlertDialog';
import useCreateSection from "../../hooks/Section/useCreateSection";
import {commonTextFieldProps} from "../../utils/Utils";
import {useTheme} from "@mui/material/styles";

/**
 * Page to create a section
 */
const CreateSection = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        sectionData,
        state,
        modules,
        students,
        handleSubmit,
        handleModuleChange,
        handleInputChange,
        handleStudentSelection,
        selectedStudents
    } = useCreateSection();

    const [open, setOpen] = React.useState(false);
    const [searchQuery, setSearchQuery] = React.useState("");

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
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
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color:customColor}}>
                        Create Section
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2, color: '#000'}}>
                        <form onSubmit={handleSubmit}>
                            <TextField
                                label="Name"
                                type="text"
                                name="name"
                                required
                                value={sectionData.name}
                                onChange={handleInputChange}
                                {...commonTextFieldProps}
                            />
                            <TextField
                                label="Module"
                                select
                                name="module"
                                required
                                value={sectionData.module.id.toString()}
                                onChange={handleModuleChange}
                                {...commonTextFieldProps}
                            >
                                <MenuItem value="" sx={{color: '#000'}}>
                                    <em style={{color: '#000'}}>Choose The Module</em>
                                </MenuItem>
                                {modules.map(module => (
                                    <MenuItem key={module.id} value={module.id} sx={{color: '#000'}}>
                                        {module.name}
                                    </MenuItem>
                                ))}
                            </TextField>
                            <Box marginTop={2}>
                                <Button variant="contained" color="primary" onClick={handleOpen}>
                                    Select Students
                                </Button>
                                <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
                                    <DialogTitle sx={{color: '#000'}}>Select Students</DialogTitle>
                                    <DialogContent>
                                        <Box sx={{display: 'flex', alignItems: 'center', marginBottom: 2}}>
                                            <InputBase
                                                placeholder="Search students"
                                                value={searchQuery}
                                                onChange={handleSearchChange}
                                                sx={{flex: 1, paddingLeft: 1, color: '#000'}}
                                            />
                                            <IconButton type="submit" sx={{p: '10px', color: '#000'}}>
                                                <SearchIcon/>
                                            </IconButton>
                                        </Box>
                                        <List>
                                            {filteredStudents.map(student => (
                                                <ListItem key={student.id} sx={{color: '#000'}}>
                                                    <Checkbox
                                                        checked={selectedStudents.includes(student.id)}
                                                        onChange={() => handleStudentSelection(student.id)}
                                                        sx={{color: '#000'}}
                                                    />
                                                    <ListItemText primary={student.username}/>
                                                </ListItem>
                                            ))}
                                        </List>
                                    </DialogContent>
                                    <DialogActions>
                                        <Button onClick={handleClose} color="primary">
                                            Done
                                        </Button>
                                    </DialogActions>
                                </Dialog>
                            </Box>
                            <Box marginTop={2}>
                                <Button variant="contained" color="primary" type="submit">
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
