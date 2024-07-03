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
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import EditIcon from '@mui/icons-material/Edit';
import {Spinner} from '../../utils/Spinner';
import {AlertDialog} from '../../utils/AlertDialog';
import {commonTextFieldProps} from "../../utils/Utils";
import ListItemText from "@mui/material/ListItemText";
import List from "@mui/material/List";
import useManageSections from "../../hooks/Section/useManageSection";
import {useTheme} from "@mui/material/styles";

/**
 * Page to manage sections
 */
const ManageSections = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        sections,
        modules,
        students,
        state,
        sectionData,
        selectedSection,
        handleSectionClick,
        handleInputChange,
        handleModuleChange,
        handleStudentSelection,
        handleSearchChange,
        searchQuery,
        handleSubmit,
        handleClose
    } = useManageSections();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const filteredStudents = students.filter(student =>
                student.username.toLowerCase().includes(searchQuery.toLowerCase())
            );

            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color:customColor}}>
                        Manage Sections
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2, color: '#000'}}>
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{color: '#000'}}>Section Name</TableCell>
                                        <TableCell sx={{color: '#000'}}>Module</TableCell>
                                        <TableCell sx={{color: '#000'}}>Edit</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {sections.map(section => (
                                        <TableRow key={section.id}>
                                            <TableCell sx={{color: '#000'}}>{section.name}</TableCell>
                                            <TableCell sx={{color: '#000'}}>{section.module.name}</TableCell>
                                            <TableCell sx={{color: '#000'}}>
                                                <IconButton onClick={() => handleSectionClick(section)}>
                                                    <EditIcon sx={{color: '#000'}}/>
                                                </IconButton>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        {selectedSection && (
                            <Dialog open={!!selectedSection} onClose={handleClose} fullWidth maxWidth="sm">
                                <DialogTitle sx={{color: '#000'}}>Edit Section</DialogTitle>
                                <DialogContent>
                                    <form onSubmit={handleSubmit}>
                                        <TextField
                                            label="Name"
                                            type="text"
                                            name="name"
                                            required
                                            value={sectionData.name}
                                            onChange={handleInputChange}
                                            {...commonTextFieldProps}
                                            sx={{color: '#000'}}
                                        />
                                        <TextField
                                            label="Module"
                                            select
                                            name="module"
                                            required
                                            value={sectionData.module.id.toString()}
                                            onChange={handleModuleChange}
                                            {...commonTextFieldProps}
                                            sx={{color: '#000'}}
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
                                        <Box sx={{display: 'flex', alignItems: 'center', marginBottom: 2}}>
                                            <InputBase
                                                placeholder="Search students"
                                                value={searchQuery}
                                                onChange={handleSearchChange}
                                                sx={{flex: 1, paddingLeft: 1, color: '#000'}}
                                            />
                                            <IconButton sx={{p: '10px', color: '#000'}}>
                                                <SearchIcon/>
                                            </IconButton>
                                        </Box>
                                        <List>
                                            {filteredStudents.map(student => (
                                                <ListItem key={student.id} sx={{color: '#000'}}>
                                                    <Checkbox
                                                        checked={sectionData.students.includes(student.id)}
                                                        onChange={() => handleStudentSelection(student.id)}
                                                        sx={{color: '#000'}}
                                                    />
                                                    <ListItemText primary={student.username} sx={{color: '#000'}}/>
                                                </ListItem>
                                            ))}
                                        </List>
                                    </form>
                                </DialogContent>
                                <DialogActions>
                                    <Button onClick={handleClose} color="primary" sx={{color: '#000'}}>
                                        Cancel
                                    </Button>
                                    <Button onClick={handleSubmit} color="primary" sx={{color: '#000'}}>
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
