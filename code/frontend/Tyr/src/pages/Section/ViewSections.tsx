import * as React from "react";
import {useState} from "react";
import useSections from "../../hooks/Section/useSections";
import useDepartments from "../../hooks/Department/useDepartments";
import {
    Box,
    Card,
    CardContent,
    Container,
    Grid,
    IconButton,
    List,
    ListItem,
    ListItemText,
    Modal,
    Typography
} from "@mui/material";
import {Spinner} from "../../utils/Spinner";
import CloseIcon from '@mui/icons-material/Close';
import {Section} from "../../services/section/models/Section";
import {useTheme} from "@mui/material/styles";
import {AlertDialog} from "../../utils/AlertDialog";
import {Department} from "../../services/department/models/Department";

const ViewSections = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;

    const {state: sectionsState} = useSections();
    const {state: departmentState} = useDepartments()
    const [selectedSection, setSelectedSection] = useState<Section | null>(null);
    const [open, setOpen] = React.useState(false);

    const handleOpen = (section: Section) => {
        setSelectedSection(section);
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setSelectedSection(null);
    };

    const groupSectionsByDepartmentAndField = (sections: Section[], departments: Department[]) => {
        const groupedSections: any = {};

        departments.forEach(department => {
            groupedSections[department.id] = {
                department,
                fields: {}
            };

            department.fieldsStudy.forEach(field => {
                groupedSections[department.id].fields[field.id] = {
                    field,
                    sections: []
                };

                field.modules.forEach(module => {
                    sections.forEach(section => {
                        if (section.module?.id === module.id) {
                            groupedSections[department.id].fields[field.id].sections.push(section);
                        }
                    });
                });
            });
        });

        return groupedSections;
    };

    switch (true) {
        case sectionsState.type === "loading" && departmentState.type === "loading":
            return <Spinner/>;

        case sectionsState.type === "error":
            return <AlertDialog alert={sectionsState.message}/>;

        case departmentState.type === "error":
            return <AlertDialog alert={departmentState.message}/>;

        case sectionsState.type === "success" && departmentState.type === "success":
            const {sections} = sectionsState;
            const {departments} = departmentState;
            const groupedSections = groupSectionsByDepartmentAndField(sections, departments);

            return (
                <Container>
                    <Typography variant="h4" sx={{my: 4, textAlign: 'center', color: customColor}}>Sections</Typography>
                    {Object.keys(groupedSections).length ? (
                        Object.keys(groupedSections).map(departmentId => {
                            const departmentData = groupedSections[departmentId];
                            return (
                                <Box key={departmentId}
                                     sx={{mb: 6, backgroundColor: '#fff', padding: 3, borderRadius: 3}}>
                                    <Typography variant="h5" sx={{mb: 3}}>{departmentData.department.name}</Typography>
                                    {Object.keys(departmentData.fields).map(fieldId => {
                                        const fieldData = departmentData.fields[fieldId];
                                        if (fieldData.sections.length === 0) return
                                        return (
                                            <Box key={fieldId} sx={{mb: 4, pl: 3}}>
                                                <Typography variant="h6"
                                                            sx={{mb: 2}}>{fieldData.field.name}</Typography>
                                                <Grid container spacing={4}>
                                                    {fieldData.sections.map((section: Section) => (
                                                            <Grid item xs={12} sm={6} md={4} key={section.id}>
                                                                <Card sx={{
                                                                    maxWidth: 600,
                                                                    margin: '20px auto',
                                                                    boxShadow: 3,
                                                                    transition: 'transform 0.3s, box-shadow 0.3s',
                                                                    '&:hover': {
                                                                        transform: 'scale(1.05)',
                                                                        boxShadow: '0 6px 12px rgba(0,0,0,0.2)',
                                                                    }
                                                                }}>
                                                                    <CardContent>
                                                                        <Typography variant="h5" component="div">
                                                                            {section.name}
                                                                        </Typography>
                                                                        <Typography sx={{color: '#666'}}
                                                                                    variant="subtitle1">
                                                                            Module: {section.module?.name}
                                                                        </Typography>
                                                                        <Box sx={{
                                                                            maxHeight: 150,
                                                                            overflow: 'hidden',
                                                                            marginTop: 2
                                                                        }}>
                                                                            <Typography sx={{
                                                                                color: 'primary.main',
                                                                                cursor: 'pointer'
                                                                            }} variant="button"
                                                                                        display="block"
                                                                                        onClick={() => handleOpen(section)}>
                                                                                View All Students
                                                                            </Typography>
                                                                        </Box>
                                                                    </CardContent>
                                                                </Card>
                                                            </Grid>
                                                        )
                                                    )}
                                                </Grid>
                                            </Box>
                                        );
                                    })}
                                </Box>
                            );
                        })
                    ) : (
                        <Typography variant="h6" sx={{textAlign: 'center', width: '100%'}}>
                            No departments or fields available.
                        </Typography>
                    )}

                    <Modal
                        open={open}
                        onClose={handleClose}
                        aria-labelledby="modal-title"
                        aria-describedby="modal-description"
                    >
                        <Box sx={{
                            position: 'absolute',
                            top: '50%',
                            left: '50%',
                            transform: 'translate(-50%, -50%)',
                            width: 400,
                            bgcolor: 'background.paper',
                            boxShadow: 24,
                            p: 4,
                            borderRadius: 2,
                            maxHeight: '80vh',
                            overflow: 'auto',
                            transition: 'all 0.3s',
                        }}>
                            <Box sx={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                                <Typography sx={{color: '#000'}} id="modal-title" variant="h6" component="h2">
                                    Students in {selectedSection?.name}
                                </Typography>
                                <IconButton onClick={handleClose}>
                                    <CloseIcon/>
                                </IconButton>
                            </Box>
                            <List>
                                {selectedSection?.students?.length ? (
                                    selectedSection.students.map(student => (
                                        <ListItem sx={{color: '#000'}} key={student.id}>
                                            <ListItemText sx={{color: '#000'}} primary={student.username}/>
                                        </ListItem>
                                    ))
                                ) : (
                                    <Typography sx={{color: '#000', marginTop: 2}} variant="body2"
                                                color="text.secondary">
                                        No students enrolled.
                                    </Typography>
                                )}
                            </List>
                        </Box>
                    </Modal>
                </Container>
            );
    }
}

export default ViewSections;
