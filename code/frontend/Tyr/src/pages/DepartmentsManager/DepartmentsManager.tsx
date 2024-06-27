import * as React from 'react';
import { useState } from 'react';
import {
    Accordion,
    AccordionSummary,
    AccordionDetails,
    Button,
    Box,
    TextField,
    Modal,
    CircularProgress,
    Container,
    Typography
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Department } from "../../services/department/models/Department";
import { FieldStudy } from "../../services/fieldstudy/models/FieldStudy";
import { Module } from "../../services/module/models/Module";
import useDepartments from "../../hooks/useDepartments";

const DepartmentsManager: React.FC = () => {
    const {
        departments,
        handleSaveDepartments,
        handleDeleteDepartments,
        handleSaveFieldsStudy,
        handleDeleteFieldsStudy,
        handleSaveModule,
        handleDeleteModule
    } = useDepartments();


    const [showCategoryModal, setShowCategoryModal] = useState(false);
    const [showSubCategoryModal, setShowSubCategoryModal] = useState(false);
    const [showModuleModal, setShowModuleModal] = useState(false);

    const [loadingCategory, setLoadingCategory] = useState(false);
    const [loadingSubCategory, setLoadingSubCategory] = useState(false);
    const [loadingModule, setLoadingModule] = useState(false);

    const [editingCategory, setEditingCategory] = useState<Department | null>(null);
    const [editingSubCategory, setEditingSubCategory] = useState<FieldStudy | null>(null);
    const [editingModule, setEditingModule] = useState<Module | null>(null);

    const handleCategoryEdit = (department: Department) => {
        setEditingCategory(department);
        setShowCategoryModal(true);
    };

    const handleSubCategoryEdit = (subCategory: FieldStudy) => {
        setEditingSubCategory(subCategory);
        setShowSubCategoryModal(true);
    };

    const handleModuleEdit = (module: Module) => {
        setEditingModule(module);
        setShowModuleModal(true);
    };

    const handleCategorySave = async () => {
        if (editingCategory) {
            setLoadingCategory(true);
            await handleSaveDepartments(editingCategory);
            setLoadingCategory(false);
            setShowCategoryModal(false);
            setEditingCategory(null);
        }
    };

    const handleSubCategorySave = async () => {
        if (editingSubCategory) {
            setLoadingSubCategory(true);
            await handleSaveFieldsStudy(editingSubCategory);
            setLoadingSubCategory(false);
            setShowSubCategoryModal(false);
            setEditingSubCategory(null);
        }
    };

    const handleModuleSave = async () => {
        if (editingModule) {
            setLoadingModule(true);
            await handleSaveModule(editingModule);
            setLoadingModule(false);
            setShowModuleModal(false);
            setEditingModule(null);
        }
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom sx={{ color: 'black' }}>
                Categories
            </Typography>
            <Button variant="contained" color="primary" onClick={() => setShowCategoryModal(true)}>Add Category</Button>
            {departments ? departments.map((department: Department, index: number) => (
                <Accordion key={department.id}>
                    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography sx={{ color: 'black' }}>{department.name}</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography sx={{ color: 'black' }}>{department.description}</Typography>
                        <Button onClick={() => handleCategoryEdit(department)}>Edit</Button>
                        <Button variant="outlined" color="error" onClick={() => handleDeleteDepartments(department.id!)}>Delete</Button>
                        <Button onClick={() => {
                            setEditingSubCategory({
                                id: undefined,
                                name: '',
                                description: '',
                                department: { id: department.id }
                            });
                            setShowSubCategoryModal(true);
                        }}>Add SubCategory</Button>
                        {department.fieldsStudy?.map((subCategory, subIndex) => (
                            <Accordion key={subCategory.id}>
                                <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                                    <Typography sx={{ color: 'black' }}>{subCategory.name}</Typography>
                                </AccordionSummary>
                                <AccordionDetails>
                                    <Typography sx={{ color: 'black' }}>{subCategory.description}</Typography>
                                    <Button onClick={() => handleSubCategoryEdit(subCategory)}>Edit</Button>
                                    <Button variant="outlined" color="error" onClick={() => handleDeleteFieldsStudy(subCategory.id!)}>Delete</Button>
                                    <Button onClick={() => {
                                        setEditingModule({
                                            id: undefined,
                                            name: '',
                                            description: '',
                                            fieldStudy: { id: subCategory.id }
                                        });
                                        setShowModuleModal(true);
                                    }}>Add Module</Button>
                                    <ul>
                                        {subCategory.modules?.map((module: Module) => (
                                            <li key={module.id}>
                                                <Typography sx={{ color: 'black' }}><strong>{module.name}:</strong> {module.description}</Typography>
                                                <Button onClick={() => handleModuleEdit(module)}>Edit</Button>
                                                <Button variant="outlined" color="error" onClick={() => handleDeleteModule(module.id!)}>Delete</Button>
                                            </li>
                                        ))}
                                    </ul>
                                </AccordionDetails>
                            </Accordion>
                        ))}
                    </AccordionDetails>
                </Accordion>
            )) : <Typography sx={{ color: 'black' }}>Loading categories...</Typography>}
            <CategoryModal
                show={showCategoryModal}
                department={editingCategory}
                onHide={() => setShowCategoryModal(false)}
                onSave={handleCategorySave}
                setCategory={setEditingCategory}
                loading={loadingCategory} />
            <SubCategoryModal
                show={showSubCategoryModal}
                subCategory={editingSubCategory}
                onHide={() => setShowSubCategoryModal(false)}
                onSave={handleSubCategorySave}
                setSubCategory={setEditingSubCategory}
                loading={loadingSubCategory} />
            <ModuleModal
                show={showModuleModal}
                module={editingModule}
                onHide={() => setShowModuleModal(false)}
                onSave={handleModuleSave}
                setModule={setEditingModule}
                loading={loadingModule} />
        </Container>
    );
};

interface ModalProps {
    show: boolean;
    onHide: () => void;
    onSave: () => void;
    department?: Department | null;
    subCategory?: FieldStudy | null;
    module?: Module | null;
    setCategory?: (department: Department | null) => void;
    setSubCategory?: (subCategory: FieldStudy | null) => void;
    setModule?: (module: Module | null) => void;
    loading?: boolean;
}

const CategoryModal: React.FC<ModalProps> = ({ show, onHide, onSave, department, setCategory, loading }) => (
    <Modal open={show} onClose={onHide}>
        <Box sx={{ ...modalStyle, width: 400 }}>
            <Typography variant="h6" component="h2" sx={{ color: 'black' }}>
                {department?.id ? 'Edit Category' : 'Add Category'}
            </Typography>
            <TextField
                fullWidth
                label="Category Name"
                value={department?.name || ''}
                onChange={(e) => setCategory && setCategory({ ...department, name: e.target.value })}
                margin="normal"
            />
            <TextField
                fullWidth
                label="Category Description"
                value={department?.description || ''}
                onChange={(e) => setCategory && setCategory({ ...department, description: e.target.value })}
                margin="normal"
                multiline
                rows={3}
            />
            <Box sx={{ mt: 2 }}>
                <Button variant="outlined" onClick={onHide}>
                    Close
                </Button>
                <Button variant="contained" onClick={onSave} disabled={loading} sx={{ ml: 2 }}>
                    {loading ? <CircularProgress size={24} /> : 'Save Changes'}
                </Button>
            </Box>
        </Box>
    </Modal>
);

const SubCategoryModal: React.FC<ModalProps> = ({ show, onHide, onSave, subCategory, setSubCategory, loading }) => (
    <Modal open={show} onClose={onHide}>
        <Box sx={{ ...modalStyle, width: 400 }}>
            <Typography variant="h6" component="h2" sx={{ color: 'black' }}>
                {subCategory?.id ? 'Edit SubCategory' : 'Add SubCategory'}
            </Typography>
            <TextField
                fullWidth
                label="SubCategory Name"
                value={subCategory?.name || ''}
                onChange={(e) => setSubCategory && setSubCategory({ ...subCategory, name: e.target.value })}
                margin="normal"
            />
            <TextField
                fullWidth
                label="SubCategory Description"
                value={subCategory?.description || ''}
                onChange={(e) => setSubCategory && setSubCategory({
                    ...subCategory,
                    description: e.target.value
                })}
                margin="normal"
                multiline
                rows={3}
            />
            <Box sx={{ mt: 2 }}>
                <Button variant="outlined" onClick={onHide}>
                    Close
                </Button>
                <Button variant="contained" onClick={onSave} disabled={loading} sx={{ ml: 2 }}>
                    {loading ? <CircularProgress size={24} /> : 'Save Changes'}
                </Button>
            </Box>
        </Box>
    </Modal>
);

const ModuleModal: React.FC<ModalProps> = ({ show, onHide, onSave, module, setModule, loading }) => (
    <Modal open={show} onClose={onHide}>
        <Box sx={{ ...modalStyle, width: 400 }}>
            <Typography variant="h6" component="h2" sx={{ color: 'black' }}>
                {module?.id ? 'Edit Module' : 'Add Module'}
            </Typography>
            <TextField
                fullWidth
                label="Module Name"
                value={module?.name || ''}
                onChange={(e) => setModule && setModule({ ...module, name: e.target.value })}
                margin="normal"
            />
            <TextField
                fullWidth
                label="Module Description"
                value={module?.description || ''}
                onChange={(e) => setModule && setModule({ ...module, description: e.target.value })}
                margin="normal"
                multiline
                rows={3}
            />
            <Box sx={{ mt: 2 }}>
                <Button variant="outlined" onClick={onHide}>
                    Close
                </Button>
                <Button variant="contained" onClick={onSave} disabled={loading} sx={{ ml: 2 }}>
                    {loading ? <CircularProgress size={24} /> : 'Save Changes'}
                </Button>
            </Box>
        </Box>
    </Modal>
);

const modalStyle = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
};

export default DepartmentsManager;
