import * as React from 'react';
import {Spinner} from '../../utils/Spinner';
import {AlertDialog} from '../../utils/AlertDialog';
import useCreateTech from "../../hooks/Tech/useCreateTech";
import {Button, Container, FormControlLabel, MenuItem, Switch, TextField} from "@mui/material";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import {commonTextFieldProps} from "../../utils/Utils";
import {times, weekDays} from "../../utils/HardCoded";
import TechFormFields from "../../components/Tech/Field/TechFormFields";
import {useTheme} from "@mui/material/styles";

/**
 * Page to create a tech
 */
const CreateTech = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        selectedTech,
        handleSubmit,
        handleSectionChange,
        handleInputChange,
        handleMultiChange,
        handleDateChange,
        handleTimeChange,
        isMultiple,
        toggleMultiple
    } = useCreateTech();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const {sections, loading} = state;

            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color: customColor}}>
                        Create Tech Class
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2}}>
                        <form onSubmit={handleSubmit}>
                            <FormControlLabel
                                control={<Switch checked={isMultiple} onChange={toggleMultiple}/>}
                                label="Multiple Classes"
                            />
                            {isMultiple ? (
                                <>
                                    <TextField
                                        label="Start Date"
                                        type="date"
                                        name="startDate"
                                        required
                                        value={selectedTech.startDate}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    />
                                    <TextField
                                        label="End Date"
                                        type="date"
                                        name="endDate"
                                        required
                                        value={selectedTech.endDate}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    />
                                    <TextField
                                        label="Class Time"
                                        select
                                        name="classTime"
                                        required
                                        value={selectedTech.classTime}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    >
                                        {times.map(time => (
                                            <MenuItem key={time} value={time}>
                                                {time}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                    <TextField
                                        label="Class Length (Hours)"
                                        type="number"
                                        name="classLengthHours"
                                        required
                                        value={selectedTech.classLengthHours}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    />
                                    <TextField
                                        label="Day of the Week"
                                        select
                                        name="dayOfWeek"
                                        required
                                        value={selectedTech.dayOfWeek}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    >
                                        {weekDays.map(day => (
                                            <MenuItem key={day} value={day} sx={{color: '#000'}}>
                                                {day}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                    <TextField
                                        select
                                        label="Section"
                                        required
                                        value={selectedTech.section?.id.toString()}
                                        onChange={handleSectionChange}
                                        {...commonTextFieldProps}
                                    >
                                        <MenuItem value="0">
                                            <em>Choose The Section</em>
                                        </MenuItem>
                                        {sections && sections.map(section => (
                                            <MenuItem key={section.id} value={section.id}>
                                                {section.name}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                </>
                            ) : (
                                <TechFormFields
                                    techData={selectedTech}
                                    sections={sections}
                                    handleInputChange={handleInputChange}
                                    handleDateChange={handleDateChange}
                                    handleTimeChange={handleTimeChange}
                                    handleSectionChange={handleSectionChange}
                                />
                            )}
                            <Box marginTop={2}>
                                <Button variant="contained" color="primary" type="submit" disabled={loading}>
                                    Create Tech Class
                                </Button>
                            </Box>
                        </form>
                    </Box>
                </Container>
            );
    }
};

export default CreateTech;
