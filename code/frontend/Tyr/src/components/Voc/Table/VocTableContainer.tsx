import {
    Checkbox,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import {toDateTimeStr} from "../../../utils/Utils";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import * as React from "react";
import {Voc} from "../../../services/voc/models/Voc";

/**
 * Container for the Voc table display
 */
const VocTableContainer = (props: VocTableContainerProps) => {
    const {
        filteredVocs,
        handleVocClick,
        handleDeleteVoc,
        handleApprovedChange,
        loading
    } = props;

    return (
        <TableContainer component={Paper} sx={{border: '1px solid rgba(0, 0, 0, 0.2)', boxShadow: 'none'}}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Description</TableCell>
                        <TableCell>Section</TableCell>
                        <TableCell>Student</TableCell>
                        <TableCell>Date</TableCell>
                        <TableCell>Approved</TableCell>
                        <TableCell>Edit</TableCell>
                        <TableCell>Delete</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {filteredVocs.map(voc => (
                        <TableRow key={voc.id}>
                            <TableCell>{voc.description}</TableCell>
                            <TableCell>{voc.section.name}</TableCell>
                            <TableCell>{voc.user.username}</TableCell>
                            <TableCell>{toDateTimeStr(voc.started)}</TableCell>
                            <TableCell>
                                <Checkbox
                                    checked={voc.approved}
                                    onChange={() => handleApprovedChange(voc)}
                                    disabled={loading}
                                />
                            </TableCell>
                            <TableCell>
                                <IconButton onClick={() => handleVocClick(voc)}>
                                    <EditIcon/>
                                </IconButton>
                            </TableCell>
                            <TableCell>
                                <IconButton onClick={() => handleDeleteVoc(voc.id)}>
                                    <DeleteIcon/>
                                </IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

/**
 * Props for the VocTableContainer component
 *
 * @param filteredVocs - list of filtered vocs to display
 * @param handleVocClick - function to handle when a voc is clicked
 * @param handleDeleteVoc - function to handle when a voc is deleted
 * @param handleApprovedChange - function to handle when a voc is approved
 * @param loading - boolean to show loading state
 */
interface VocTableContainerProps {
    filteredVocs: Voc[];
    handleVocClick: (voc: Voc) => void;
    handleDeleteVoc: (id: number) => Promise<void>;
    handleApprovedChange: (voc: Voc) => Promise<void>;
    loading: boolean;
}

export default VocTableContainer;