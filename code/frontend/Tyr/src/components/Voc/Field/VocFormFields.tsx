import * as React from 'react';
import {ChangeEvent} from 'react';
import {Box, MenuItem, TextField} from '@mui/material';
import {commonTextFieldProps} from "../../../utils/Utils";
import {Voc} from "../../../services/voc/models/Voc";
import {Section} from "../../../services/section/models/Section";
import {times} from "../../../utils/HardCoded";

interface VocFormFieldsProps {
    selectedVoc: Voc;
    sections: Section[];
    handleInputChange: (e: ChangeEvent<HTMLInputElement>) => void;
    handleDateChange: (e: ChangeEvent<HTMLInputElement>) => void;
    handleTimeChange: (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: string) => void;
    handleSectionChange: (e: ChangeEvent<HTMLInputElement>) => void;

}

const VocFormFields = (props: VocFormFieldsProps) => {
    const {
        selectedVoc,
        sections,
        handleInputChange,
        handleDateChange,
        handleTimeChange,
        handleSectionChange
    } = props;

    return <>
        <TextField
            label="Description"
            required
            name="description"
            value={selectedVoc.description}
            onChange={handleInputChange}
            {...commonTextFieldProps}
        />
        <TextField
            label="Date"
            type="date"
            required
            value={selectedVoc.started.split('T')[0]}
            onChange={handleDateChange}
            {...commonTextFieldProps}
        />
        <Box display="flex" justifyContent="space-between" marginTop={2}>
            <TextField
                label="Start Time"
                select
                required
                value={selectedVoc.started.split('T')[1]}
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
                value={selectedVoc.ended.split('T')[1]}
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
            label="Section"
            select
            required
            value={selectedVoc.section.id.toString()}
            onChange={handleSectionChange}
            {...commonTextFieldProps}
        >
            <MenuItem value="0">
                <em>Choose The Section</em>
            </MenuItem>
            {sections.map(section => (
                <MenuItem key={section.id} value={section.id}>
                    {section.name}
                </MenuItem>
            ))}
        </TextField>
    </>
}

export default VocFormFields;
