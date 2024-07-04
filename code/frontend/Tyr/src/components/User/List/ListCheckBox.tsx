import {Checkbox, ListItem, ListItemText} from "@mui/material";
import * as React from "react";
import {User} from "../../../services/user/models/User";

const ListCheckBox = (props: ListCheckBoxProps) => {
    const {
        student,
        selectedStudents,
        handleStudentSelect
    } = props;

    return (
        <ListItem key={student.id} sx={{color: '#000'}}>
            <Checkbox
                checked={selectedStudents.some(stu => stu.id === student.id)}
                onChange={() => handleStudentSelect(student)}
            />
            <ListItemText primary={student.username}/>
        </ListItem>
    )
}

interface ListCheckBoxProps {
    student: User;
    selectedStudents: User[];
    handleStudentSelect: (student: User) => void;
}

export default ListCheckBox;