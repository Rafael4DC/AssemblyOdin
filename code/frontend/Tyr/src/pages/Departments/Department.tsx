import * as React from 'react';
import { Accordion, AccordionSummary, AccordionDetails, Typography, Container, Card, CardContent, CircularProgress, Box, List, ListItem, ListItemText } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useTheme } from '@mui/material/styles';
import useDepartments from "../../hooks/useDepartments";
import { Department } from "../../services/department/models/Department";
import {Spinner} from "../../utils/Spinner";

function Departments() {
    const { departments } = useDepartments();
    const theme = useTheme();
    const magenta = theme.palette.magenta.main;
    const lightBackgroundColor = '#f5f5f5';
    const lighterBackgroundColor = '#ffffff';

    return (
        <Container maxWidth="md">
            <Typography variant="h4" gutterBottom align="center" sx={{ mb: 4 }}>
                Departments
            </Typography>
            {departments ? (
                <Box>
                    {departments.map((department: Department, index: number) => (
                        <Accordion key={department.id} defaultExpanded={index === 0} disableGutters>
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon style={{ color: 'white' }} />}
                                aria-controls={`panel${index}-content`}
                                id={`panel${index}-header`}
                                sx={{ padding: 0, bgcolor: magenta }}
                            >
                                <Typography variant="h6" sx={{ color: 'white', marginLeft: 2 }}>{department.name}</Typography>
                            </AccordionSummary>
                            <AccordionDetails sx={{ padding: 0 }}>
                                <Card variant="outlined" sx={{ mb: 2, backgroundColor: lightBackgroundColor }}>
                                    <CardContent sx={{ padding: '16px' }}>
                                        <Typography variant="body1" paragraph sx={{ color: magenta, mb: 3 }}>
                                            {department.description}
                                        </Typography>
                                        {department.fieldsStudy && department.fieldsStudy.length > 0 && (
                                            <Box>
                                                {department.fieldsStudy.map((fieldStudy, subIndex) => (
                                                    <Accordion key={fieldStudy.id} defaultExpanded={subIndex === 0} disableGutters sx={{ mb: 2 }}>
                                                        <AccordionSummary
                                                            expandIcon={<ExpandMoreIcon style={{ color: 'white' }} />}
                                                            aria-controls={`subpanel${subIndex}-content`}
                                                            id={`subpanel${subIndex}-header`}
                                                            sx={{ padding: 0, bgcolor: magenta }}
                                                        >
                                                            <Typography variant="subtitle1" sx={{ color: 'white', marginLeft: 2 }}>{fieldStudy.name}</Typography>
                                                        </AccordionSummary>
                                                        <AccordionDetails sx={{ padding: 0 }}>
                                                            <Card variant="outlined" sx={{ mb: 2, ml: 2, backgroundColor: lighterBackgroundColor }}>
                                                                <CardContent sx={{ padding: '16px' }}>
                                                                    <Typography variant="body2" paragraph sx={{ color: magenta, mb: 2 }}>
                                                                        {fieldStudy.description}
                                                                    </Typography>
                                                                    <List>
                                                                        {fieldStudy.modules.map((module) => (
                                                                            <ListItem key={module.id} sx={{ pl: 2 }}>
                                                                                <ListItemText
                                                                                    primary={
                                                                                        <Typography variant="body2" sx={{ color: magenta, fontWeight: 'bold' }}>
                                                                                            {module.name}
                                                                                        </Typography>
                                                                                    }
                                                                                    secondary={
                                                                                        <Typography variant="body2" sx={{ color: magenta }}>
                                                                                            {module.description}
                                                                                        </Typography>
                                                                                    }
                                                                                />
                                                                            </ListItem>
                                                                        ))}
                                                                    </List>
                                                                </CardContent>
                                                            </Card>
                                                        </AccordionDetails>
                                                    </Accordion>
                                                ))}
                                            </Box>
                                        )}
                                    </CardContent>
                                </Card>
                            </AccordionDetails>
                        </Accordion>
                    ))}
                </Box>
            ) : (
                <Spinner/>
            )}
        </Container>
    );
}

export default Departments;