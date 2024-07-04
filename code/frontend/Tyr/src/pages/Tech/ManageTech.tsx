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
import {toDateTimeStr} from "../../utils/Utils";
import TechFormFields from "../../components/Tech/Field/TechFormFields";
import {useTheme} from "@mui/material/styles";

/**
 * Page to manage tech
 */
const ManageTech = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        selectedTech,
        handleTechClick,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        filter,
        setFilter,
        sectionFilter,
        setSectionFilter,
        handleSubmit,
        handleClose,
        handleDeleteTech
    } = useManageTech();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const {sections, filteredTechs, loading} = state;

            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color: customColor}}>
                        Manage Tech Classes
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2}}>
                        <Box display="flex" justifyContent="space-between" marginBottom={2}>
                            <FormControl sx={{width: '48%'}}>
                                <InputLabel>Section Filter</InputLabel>
                                <Select
                                    value={sectionFilter}
                                    onChange={(e) => setSectionFilter(e.target.value)}
                                >
                                    <MenuItem value="all">All Sections</MenuItem>
                                    {sections.map((section) => (
                                        <MenuItem key={section.id} value={section.id}>
                                            {section.name}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                            <FormControl sx={{width: '48%'}}>
                                <InputLabel>Date Filter</InputLabel>
                                <Select
                                    value={filter}
                                    onChange={(e) => setFilter(e.target.value)}
                                >
                                    <MenuItem value="all">All</MenuItem>
                                    <MenuItem value="past">Past Classes</MenuItem>
                                    <MenuItem value="future">Future Classes</MenuItem>
                                    <MenuItem value="nextWeek">Next Week</MenuItem>
                                    <MenuItem value="pastWeek">Past Week</MenuItem>
                                </Select>
                            </FormControl>
                        </Box>
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Section</TableCell>
                                        <TableCell>Teacher</TableCell>
                                        <TableCell>Started</TableCell>
                                        <TableCell>Ended</TableCell>
                                        <TableCell>Edit</TableCell>
                                        <TableCell>Delete</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {filteredTechs.map(tech => (
                                        <TableRow key={tech.id}>
                                            <TableCell>{tech.section?.name}</TableCell>
                                            <TableCell>{tech.teacher?.username}</TableCell>
                                            <TableCell>{toDateTimeStr(tech.started)}</TableCell>
                                            <TableCell>{toDateTimeStr(tech.ended)}</TableCell>
                                            <TableCell>
                                                <IconButton onClick={() => handleTechClick(tech)}>
                                                    <EditIcon/>
                                                </IconButton>
                                            </TableCell>
                                            <TableCell>
                                                <IconButton onClick={() => handleDeleteTech(tech.id)}>
                                                    <DeleteIcon/>
                                                </IconButton>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        {selectedTech && (
                            <Dialog open={!!selectedTech} onClose={handleClose} fullWidth maxWidth="sm">
                                <DialogTitle>Edit Tech Class</DialogTitle>
                                <DialogContent>
                                    <form onSubmit={handleSubmit}>
                                        <TechFormFields
                                            techData={selectedTech}
                                            sections={sections}
                                            handleInputChange={handleInputChange}
                                            handleDateChange={handleDateChange}
                                            handleTimeChange={handleTimeChange}
                                            handleSectionChange={handleSectionChange}
                                        />
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
}

export default ManageTech;
