import * as React from 'react';
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Box,
    Card,
    CardContent,
    Container,
    List,
    ListItem,
    ListItemText,
    Typography
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {useTheme} from '@mui/material/styles';
import {Department} from "../../services/department/models/Department";
import {Spinner} from "../../utils/Spinner";
import useDepartments from "../../hooks/useDepartments";
import {AlertDialog} from "../../utils/AlertDialog";

/**
 * Page to display the departments
 */
const Departments = () => {
    const {state} = useDepartments();
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const magenta = theme.palette.magenta.main;

    switch (state.type) {
        case "loading":
            return <Spinner/>;

        case "error":
            return <AlertDialog alert={state.message}/>;

        case "success":
            const {departments} = state;

            return (
                <Container maxWidth="md">
                    <Typography variant="h4" gutterBottom align="center" sx={{color: customColor}}>
                        Departments
                    </Typography>
                    <Box sx={{backgroundColor: customColor}}>
                        {departments.map((department: Department) => (
                            <Accordion key={department.id} sx={{boxShadow:'none'}}>
                                <AccordionSummary
                                    expandIcon={<ExpandMoreIcon/>}
                                    sx={{bgcolor: magenta}}
                                >
                                    <Typography sx={{color: 'white'}}>{department.name}</Typography>
                                </AccordionSummary>
                                <AccordionDetails >
                                    <Typography sx={{color: magenta}}>
                                        {department.description}
                                    </Typography>
                                    {department.fieldsStudy?.map((fieldStudy) => (
                                        <Accordion key={fieldStudy.id} sx={{border: '1px solid rgba(0, 0, 0, 0.2)'}}>
                                            <AccordionSummary
                                                expandIcon={<ExpandMoreIcon style={{color: 'white'}}/>}
                                                sx={{bgcolor: magenta}}
                                            >
                                                <Typography sx={{color: 'white'}}>{fieldStudy.name}</Typography>
                                            </AccordionSummary>
                                            <AccordionDetails>
                                                <Typography sx={{color: magenta}}>{fieldStudy.description}</Typography>
                                                <List>
                                                    {fieldStudy.modules.map((module) => (
                                                        <ListItem key={module.id}>
                                                            <ListItemText
                                                                primary={
                                                                    <Typography
                                                                        sx={{color: 'black', fontWeight: 'bold'}}>
                                                                        {module.name}:{' '}
                                                                        <Typography
                                                                            component="span"
                                                                            sx={{fontWeight: 'normal'}}
                                                                        >
                                                                            {module.description}
                                                                        </Typography>
                                                                    </Typography>
                                                                }
                                                            />
                                                        </ListItem>
                                                    ))}
                                                </List>
                                            </AccordionDetails>
                                        </Accordion>
                                    ))}
                                </AccordionDetails>
                            </Accordion>
                        ))}
                    </Box>
                </Container>
            );
    }
}

export default Departments;