import * as React from 'react';
import { Spinner } from '../../utils/Spinner';
import { AlertDialog } from '../../utils/AlertDialog';
import useCreateTechClass from "../../hooks/useCreateTechClass";
import { Button, Container, MenuItem, TextField, TextFieldProps, FormControlLabel, Switch } from "@mui/material";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { commonTextFieldProps } from "../../utils/Utils";

const CreateTechClass = () => {
    const {
        techData,
        state,
        sections,
        handleSubmit,
        handleSectionChange,
        handleChange,
        handleMultiChange,
        isMultiple,
        toggleMultiple
    } = useCreateTechClass();

    const availableTimes = [
        '10:00', '11:00', '12:00', '13:00', '14:00',
        '15:00', '16:00', '17:00', '18:00', '19:00',
        '20:00', '21:00',
    ];

    switch (state.type) {
        case 'loading':
            return <Spinner />;

        case 'error':
            return <AlertDialog alert={state.message} />;

        case 'success':
            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom style={{ color: '#000' }}>
                        Create Tech Class
                    </Typography>
                    <Box sx={{ backgroundColor: 'white', padding: 3, borderRadius: 2, color: '#000' }}>
                        <form onSubmit={handleSubmit}>
                            <FormControlLabel
                                control={<Switch checked={isMultiple} onChange={toggleMultiple} />}
                                label="Multiple Classes"
                            />
                            {isMultiple ? (
                                <>
                                    <TextField
                                        label="Start Date"
                                        type="date"
                                        name="startDate"
                                        required
                                        value={techData.startDate}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    />
                                    <TextField
                                        label="End Date"
                                        type="date"
                                        name="endDate"
                                        required
                                        value={techData.endDate}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    />
                                    <TextField
                                        label="Class Time"
                                        select
                                        name="classTime"
                                        required
                                        value={techData.classTime}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    >
                                        {availableTimes.map(time => (
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
                                        value={techData.classLengthHours}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    />
                                    <TextField
                                        label="Day of the Week"
                                        select
                                        name="dayOfWeek"
                                        required
                                        value={techData.dayOfWeek}
                                        onChange={handleMultiChange}
                                        {...commonTextFieldProps}
                                    >
                                        {['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'].map(day => (
                                            <MenuItem key={day} value={day}>
                                                {day}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                </>
                            ) : (
                                <>
                                    <TextField
                                        label="Starts"
                                        type="datetime-local"
                                        name="started"
                                        required
                                        value={techData.started}
                                        onChange={handleChange}
                                        {...commonTextFieldProps}
                                    />
                                    <TextField
                                        label="Ends"
                                        type="datetime-local"
                                        name="ended"
                                        required
                                        value={techData.ended}
                                        onChange={handleChange}
                                        {...commonTextFieldProps}
                                    />
                                    <TextField
                                        label="Summary"
                                        multiline
                                        rows={3}
                                        name="summary"
                                        required
                                        value={techData.summary}
                                        onChange={handleChange}
                                        {...commonTextFieldProps}
                                    />
                                </>
                            )}
                            <TextField
                                label="Section"
                                select
                                name="section"
                                required
                                value={techData.section.id}
                                onChange={handleSectionChange}
                                {...commonTextFieldProps}
                            >
                                <MenuItem value="" sx={{ color: '#000' }}>
                                    <em style={{ color: '#000' }}>Choose The Section</em>
                                </MenuItem>
                                {sections.map(section => (
                                    <MenuItem key={section.id} value={section.id} sx={{ color: '#000' }}>
                                        {section.name}
                                    </MenuItem>
                                ))}
                            </TextField>
                            <Box marginTop={2}>
                                <Button variant="contained" color="primary" type="submit">
                                    Create Tech Class
                                </Button>
                            </Box>
                        </form>
                    </Box>
                </Container>
            );
    }
};

export default CreateTechClass;
