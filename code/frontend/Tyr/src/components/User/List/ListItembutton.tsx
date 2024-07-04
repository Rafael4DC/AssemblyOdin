import {ListItemText} from "@mui/material";
import * as React from "react";
import {User} from "../../../services/user/models/User";
import ListItemButton from "@mui/material/ListItemButton";

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

interface ListItemButtonProps {
    student: User;
    handleStudentSelect: (student: User) => void;
}

export default ListItembutton;