import {IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import * as React from "react";
import {Section} from "../../../services/section/models/Section";

const SectionTableContainer = (props: SectionTableContainerProps) => {
    const {
        sections,
        handleSectionClick
    } = props;

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Section Name</TableCell>
                        <TableCell>Module</TableCell>
                        <TableCell>Edit</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {sections.map(section => (
                        <TableRow key={section.id}>
                            <TableCell>{section.name}</TableCell>
                            <TableCell>{section.module.name}</TableCell>
                            <TableCell>
                                <IconButton onClick={() => handleSectionClick(section)}>
                                    <EditIcon/>
                                </IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

interface SectionTableContainerProps {
    sections: Section[];
    handleSectionClick: (section: Section) => void;
}

export default SectionTableContainer;
