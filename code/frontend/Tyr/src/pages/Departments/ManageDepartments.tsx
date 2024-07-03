import * as React from 'react';
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Box,
    Button,
    Container,
    Typography,
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {Department} from '../../services/department/models/Department';
import {Module} from '../../services/module/models/Module';
import useManageDepartments from '../../hooks/useManageDepartments';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemSecondaryAction from '@mui/material/ListItemSecondaryAction';
import ModuleModal from '../../components/Module/Modal/ModuleModal';
import FieldStudyModal from '../../components/FieldStudy/Modal/FieldStudyModal';
import DepartmentModal from '../../components/Department/Modal/DepartmentModal';
import {useTheme} from '@mui/material/styles';
import {Spinner} from '../../utils/Spinner';
import {AlertDialog} from '../../utils/AlertDialog';

const ManageDepartments = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        handleDepartmentEdit,
        handleFieldStudyEdit,
        handleModuleEdit,
        handleChangeDepart,
        handleChangeFieldsStudy,
        handleChangeModule,
        showDepartmentModal,
        showFieldStudyModal,
        showModuleModal,
        setShowDepartmentModal,
        setShowFieldStudyModal,
        setShowModuleModal,
        editingDepartment,
        editingFieldStudy,
        editingModule,
        setEditingDepartment,
        setEditingFieldStudy,
        setEditingModule,
    } = useManageDepartments();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const {departments} = state;

            return (
                <Container maxWidth="md">
                    <Typography variant="h4" gutterBottom align="center" sx={{color: customColor}}>
                        Departments
                    </Typography>
                    <Box sx={{backgroundColor: customColor}}>
                        {departments?.map((department: Department) => (
                            <Accordion key={department.id} sx={{boxShadow:'none'}}>
                                <AccordionSummary expandIcon={<ExpandMoreIcon/>}>
                                    <Typography>{department.name}</Typography>
                                </AccordionSummary>
                                <AccordionDetails>
                                    <Typography>{department.description}</Typography>
                                    {department.fieldsStudy?.map((fieldStudy) => {
                                        fieldStudy.department = department;
                                        return (
                                            <Accordion key={fieldStudy.id}
                                                       sx={{border: '1px solid rgba(0, 0, 0, 0.2)'}}>
                                                <AccordionSummary expandIcon={<ExpandMoreIcon/>}>
                                                    <Typography>{fieldStudy.name}</Typography>
                                                </AccordionSummary>
                                                <AccordionDetails>
                                                    <Typography>{fieldStudy.description}</Typography>
                                                    <List>
                                                        {fieldStudy.modules?.map((module: Module) => {
                                                            module.fieldStudy = fieldStudy;
                                                            return (
                                                                <ListItem key={module.id}>
                                                                    <ListItemText
                                                                        primary={
                                                                            <Typography
                                                                                sx={{
                                                                                    color: 'black',
                                                                                    fontWeight: 'bold'
                                                                                }}>
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
                                                                    <ListItemSecondaryAction>
                                                                        <Button
                                                                            onClick={() => handleModuleEdit(module)}>
                                                                            Edit
                                                                        </Button>
                                                                    </ListItemSecondaryAction>
                                                                </ListItem>
                                                            )
                                                        })}
                                                    </List>
                                                </AccordionDetails>
                                                <Box display="flex" justifyContent="space-between">
                                                    <Button
                                                        onClick={() => handleFieldStudyEdit(fieldStudy)}>Edit</Button>
                                                    <Button
                                                        onClick={() => {
                                                            setEditingModule({
                                                                name: '',
                                                                description: '',
                                                                fieldStudy: {id: fieldStudy.id},
                                                            });
                                                            setShowModuleModal(true);
                                                        }}
                                                    >
                                                        Add Module
                                                    </Button>
                                                </Box>
                                            </Accordion>
                                        )
                                    })}
                                    <Box display="flex" justifyContent="space-between">
                                        <Button onClick={() => handleDepartmentEdit(department)}>Edit</Button>
                                        <Button
                                            onClick={() => {
                                                setEditingFieldStudy({
                                                    name: '',
                                                    description: '',
                                                    department: {id: department.id},
                                                });
                                                setShowFieldStudyModal(true);
                                            }}
                                        >
                                            Add Field Of Study
                                        </Button>
                                    </Box>
                                </AccordionDetails>
                            </Accordion>
                        ))}
                    </Box>
                    <Button variant="contained" color="primary" onClick={() => setShowDepartmentModal(true)}>
                        Add Department
                    </Button>
                    <DepartmentModal
                        show={showDepartmentModal}
                        department={editingDepartment}
                        onHide={() => setShowDepartmentModal(false)}
                        onSave={handleChangeDepart}
                        setDepartment={setEditingDepartment}
                        loading={state.loading}
                    />
                    <FieldStudyModal
                        show={showFieldStudyModal}
                        fieldStudy={editingFieldStudy}
                        onHide={() => setShowFieldStudyModal(false)}
                        onSave={handleChangeFieldsStudy}
                        setFieldStudy={setEditingFieldStudy}
                        loading={state.loading}
                    />
                    <ModuleModal
                        show={showModuleModal}
                        module={editingModule}
                        onHide={() => setShowModuleModal(false)}
                        onSave={handleChangeModule}
                        setModule={setEditingModule}
                        loading={state.loading}
                    />
                </Container>
            );
    }
};

export default ManageDepartments;
