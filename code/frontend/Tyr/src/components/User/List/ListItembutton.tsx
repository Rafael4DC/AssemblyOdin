import {ListItemText} from "@mui/material";
import * as React from "react";
import {User} from "../../../services/user/models/User";
import ListItemButton from "@mui/material/ListItemButton";

/**
 * List item with a button to select a student
 */
const ListItembutton = (props: ListItemButtonProps) => {
    const {
        student,
        handleStudentSelect
    } = props;

    return (
        <ListItemButton
            key={student.id}
            onClick={() => handleStudentSelect(student)}>
            <ListItemText primary={student.username}/>
        </ListItemButton>
    )
}

/**
 * Props for the ListItembutton component
 *
 * @param student - the student to display
 * @param handleStudentSelect - function to handle when a student is selected
 */
interface ListItemButtonProps {
    student: User;
    handleStudentSelect: (student: User) => void;
}

export default ListItembutton;