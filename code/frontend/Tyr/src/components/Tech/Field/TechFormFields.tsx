import * as React from 'react';
import {Box, MenuItem, TextField} from '@mui/material';
import {Tech} from '../../../services/tech/models/Tech';
import {Section} from "../../../services/section/models/Section";
import {commonTextFieldProps} from "../../../utils/Utils";
import {times} from "../../../utils/HardCoded";

/**
 * Form fields for the tech class
 */
const TechFormFields = (props: TechFormFieldsProps) => {
    const {
        techData,
        sections,
        handleInputChange,
        handleDateChange,
        handleTimeChange,
        handleSectionChange,
    } = props;

    return (
        <>
            <TextField
                label="Summary"
                required
                name="summary"
                value={techData.summary}
                onChange={handleInputChange}
                {...commonTextFieldProps}
            />
            <TextField
                label="Date"
                type="date"
                required
                value={techData.started.split('T')[0]}
                onChange={handleDateChange}
                {...commonTextFieldProps}
            />
            <Box display="flex" justifyContent="space-between" marginTop={2}>
                <TextField
                    label="Start Time"
                    select
                    required
                    value={techData.started.split('T')[1]}
                    onChange={(e) => handleTimeChange(e, 'started')}
                    {...commonTextFieldProps}
                    sx={{width: '48%'}}
                >
                    {times.map(time => (
                        <MenuItem key={time} value={time}>
                            {time}
                        </MenuItem>
                    ))}
                </TextField>

                <TextField
                    label="End Time"
                    select
                    required
                    value={techData.ended.split('T')[1]}
                    onChange={(e) => handleTimeChange(e, 'ended')}
                    {...commonTextFieldProps}
                    sx={{width: '48%'}}
                >
                    {times.map(time => (
                        <MenuItem key={time} value={time}>
                            {time}
                        </MenuItem>
                    ))}
                </TextField>
            </Box>
            <TextField
                select
                label="Section"
                required
                value={techData.section?.id.toString()}
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
    )
}

/**
 * Props for the TechFormFields component
 *
 * @param techData - the tech data
 * @param sections - the sections
 * @param handleInputChange - function to handle input change
 * @param handleDateChange - function to handle date change
 * @param handleTimeChange - function to handle time change
 * @param handleSectionChange - function to handle section change
 */
interface TechFormFieldsProps {
    techData: Tech;
    sections: Section[];
    handleInputChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleDateChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleTimeChange: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, type: string) => void;
    handleSectionChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export default TechFormFields;
