import * as React from 'react';
import {
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    IconButton,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import useManageTech from "../../hooks/Tech/useManageTech";
import {Spinner} from "../../utils/Spinner";
import {AlertDialog} from "../../utils/AlertDialog";
import {commonTextFieldProps, toDateTimeStr} from "../../utils/Utils";
import TechFormFields from "../../components/Tech/Field/TechFormFields";
import {useTheme} from "@mui/material/styles";

/**
 * Page to manage tech
 */
const ManageTech = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        techs,
        sections,
        state,
        techData,
        selectedTech,
        handleTechClick,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        handleSubmit,
        handleClose,
        filter,
        setFilter,
        sectionFilter,
        setSectionFilter,
        filteredTechs,
        handleDeleteTech,
    } = useManageTech();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color:customColor}}>
                        Manage Tech Classes
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2, color: '#000'}}>
                        <Box display="flex" justifyContent="space-between" marginBottom={2}>
                            <FormControl sx={{width: '48%'}}>
                                <InputLabel sx={{color: '#000'}}>Section Filter</InputLabel>
                                <Select
                                    value={sectionFilter}
                                    onChange={(e) => setSectionFilter(e.target.value)}
                                    sx={{color: '#000'}}
                                >
                                    <MenuItem value="all" sx={{color: '#000'}}>All Sections</MenuItem>
                                    {sections.map((section) => (
                                        <MenuItem key={section.id} value={section.id} sx={{color: '#000'}}>
                                            {section.name}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                            <FormControl sx={{width: '48%'}}>
                                <InputLabel sx={{color: '#000'}}>Date Filter</InputLabel>
                                <Select
                                    value={filter}
                                    onChange={(e) => setFilter(e.target.value)}
                                    sx={{color: '#000'}}
                                >
                                    <MenuItem value="all" sx={{color: '#000'}}>All</MenuItem>
                                    <MenuItem value="past" sx={{color: '#000'}}>Past Classes</MenuItem>
                                    <MenuItem value="future" sx={{color: '#000'}}>Future Classes</MenuItem>
                                    <MenuItem value="nextWeek" sx={{color: '#000'}}>Next Week</MenuItem>
                                    <MenuItem value="pastWeek" sx={{color: '#000'}}>Past Week</MenuItem>
                                </Select>
                            </FormControl>
                        </Box>
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{color: '#000'}}>Section</TableCell>
                                        <TableCell sx={{color: '#000'}}>Teacher</TableCell>
                                        <TableCell sx={{color: '#000'}}>Started</TableCell>
                                        <TableCell sx={{color: '#000'}}>Ended</TableCell>
                                        <TableCell sx={{color: '#000'}}>Edit</TableCell>
                                        <TableCell sx={{color: '#000'}}>Delete</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {filteredTechs.map(tech => (
                                        <TableRow key={tech.id}>
                                            <TableCell sx={{color: '#000'}}>{tech.section?.name}</TableCell>
                                            <TableCell sx={{color: '#000'}}>{tech.teacher?.username}</TableCell>
                                            <TableCell sx={{color: '#000'}}>{toDateTimeStr(tech.started)}</TableCell>
                                            <TableCell sx={{color: '#000'}}>{toDateTimeStr(tech.ended)}</TableCell>
                                            <TableCell sx={{color: '#000'}}>
                                                <IconButton onClick={() => handleTechClick(tech)}>
                                                    <EditIcon sx={{color: '#000'}}/>
                                                </IconButton>
                                            </TableCell>
                                            <TableCell sx={{color: '#000'}}>
                                                <IconButton onClick={() => handleDeleteTech(tech.id)}>
                                                    <DeleteIcon sx={{color: '#000'}}/>
                                                </IconButton>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        {selectedTech && (
                            <Dialog open={!!selectedTech} onClose={handleClose} fullWidth maxWidth="sm">
                                <DialogTitle sx={{color: '#000'}}>Edit Tech Class</DialogTitle>
                                <DialogContent>
                                    <form onSubmit={handleSubmit}>
                                        <TechFormFields
                                            techData={techData}
                                            sections={sections}
                                            handleInputChange={handleInputChange}
                                            handleDateChange={handleDateChange}
                                            handleTimeChange={handleTimeChange}
                                            handleSectionChange={handleSectionChange}
                                        />
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
}

export default ManageTech;
