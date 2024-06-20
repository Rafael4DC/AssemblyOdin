import {useState} from 'react';
import {Accordion, Card, Container, Button, Form, Modal} from 'react-bootstrap';
import {Department} from "../../services/department/models/Department";
import {FieldStudy} from "../../services/fieldstudy/models/FieldStudy";
import {Module} from "../../services/module/models/Module";
import * as React from 'react';
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

    const handleCategoryEdit = (category: Department) => {
        setEditingCategory(category);
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
            <h1>Categories</h1>
            <Button onClick={() => setShowCategoryModal(true)}>Add Category</Button>
            <Accordion defaultActiveKey="0">
                {departments ? departments.map((category: Department, index: number) => (
                    <Accordion.Item eventKey={index.toString()} key={category.id}>
                        <Accordion.Header>{category.name}</Accordion.Header>
                        <Accordion.Body>
                            <Card.Text>{category.description}</Card.Text>
                            <Button onClick={() => handleCategoryEdit(category)}>Edit</Button>
                            <Button variant="danger" onClick={() => handleDeleteDepartments(category.id!)}>Delete</Button>
                            <Button onClick={() => {
                                setEditingSubCategory({
                                    id: undefined,
                                    name: '',
                                    description: '',
                                    department: {id: category.id}
                                });
                                setShowSubCategoryModal(true);
                            }}>Add SubCategory</Button>
                            <Accordion>
                                {category.fieldsStudy?.map((subCategory, subIndex) => (
                                    subCategory.department = {id: category.id},
                                    <Accordion.Item eventKey={subIndex.toString()} key={subCategory.id}>
                                        <Accordion.Header>{subCategory.name}</Accordion.Header>
                                        <Accordion.Body>
                                            <Card.Text>{subCategory.description}</Card.Text>
                                            <Button onClick={() => handleSubCategoryEdit(subCategory)}>Edit</Button>
                                            <Button variant="danger"
                                                    onClick={() => handleDeleteFieldsStudy(subCategory.id!)}>Delete</Button>
                                            <Button onClick={() => {
                                                setEditingModule({
                                                    id: undefined,
                                                    name: '',
                                                    description: '',
                                                    fieldStudy: {id: subCategory.id}
                                                });
                                                setShowModuleModal(true);
                                            }}>Add Module</Button>
                                            <ul>
                                                {subCategory.modules?.map((module) => (
                                                    module.fieldStudy = {id: subCategory.id},
                                                    <li key={module.id}>
                                                        <strong>{module.name}:</strong> {module.description}
                                                        <Button onClick={() => handleModuleEdit(module)}>Edit</Button>
                                                        <Button variant="danger"
                                                                onClick={() => handleDeleteModule(module.id!)}>Delete</Button>
                                                    </li>
                                                ))}
                                            </ul>
                                        </Accordion.Body>
                                    </Accordion.Item>
                                ))}
                            </Accordion>
                        </Accordion.Body>
                    </Accordion.Item>
                )) : <p>Loading categories...</p>}
            </Accordion>
            <CategoryModal
                show={showCategoryModal}
                category={editingCategory}
                onHide={() => setShowCategoryModal(false)}
                onSave={handleCategorySave}
                setCategory={setEditingCategory}
                loading={loadingCategory}/>
            <SubCategoryModal
                show={showSubCategoryModal}
                subCategory={editingSubCategory}
                onHide={() => setShowSubCategoryModal(false)}
                onSave={handleSubCategorySave}
                setSubCategory={setEditingSubCategory}
                loading={loadingSubCategory}/>
            <ModuleModal
                show={showModuleModal}
                module={editingModule}
                onHide={() => setShowModuleModal(false)}
                onSave={handleModuleSave}
                setModule={setEditingModule}
                loading={loadingModule}/>
        </Container>
    );
};

interface ModalProps {
    show: boolean;
    onHide: () => void;
    onSave: () => void;
    category?: Department | null;
    subCategory?: FieldStudy | null;
    module?: Module | null;
    setCategory?: (category: Department | null) => void;
    setSubCategory?: (subCategory: FieldStudy | null) => void;
    setModule?: (module: Module | null) => void;
    loading?: boolean;
}

const CategoryModal: React.FC<ModalProps> = ({show, onHide, onSave, category, setCategory, loading}) => (
    <Modal show={show} onHide={onHide}>
        <Modal.Header closeButton>
            <Modal.Title>{category?.id ? 'Edit Category' : 'Add Category'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form>
                <Form.Group controlId="categoryName">
                    <Form.Label>Category Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={category?.name || ''}
                        onChange={(e) => setCategory && setCategory({...category, name: e.target.value})}
                    />
                </Form.Group>
                <Form.Group controlId="categoryDescription">
                    <Form.Label>Category Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        value={category?.description || ''}
                        onChange={(e) => setCategory && setCategory({...category, description: e.target.value})}
                    />
                </Form.Group>
            </Form>
        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={onHide}>
                Close
            </Button>
            <Button variant="primary" onClick={onSave} disabled={loading}>
                {loading ? 'Saving...' : 'Save Changes'}
            </Button>
        </Modal.Footer>
    </Modal>
);

const SubCategoryModal: React.FC<ModalProps> = ({show, onHide, onSave, subCategory, setSubCategory, loading}) => (
    <Modal show={show} onHide={onHide}>
        <Modal.Header closeButton>
            <Modal.Title>{subCategory?.id ? 'Edit SubCategory' : 'Add SubCategory'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form>
                <Form.Group controlId="subCategoryName">
                    <Form.Label>SubCategory Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={subCategory?.name || ''}
                        onChange={(e) => setSubCategory && setSubCategory({...subCategory, name: e.target.value})}
                    />
                </Form.Group>
                <Form.Group controlId="subCategoryDescription">
                    <Form.Label>SubCategory Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        value={subCategory?.description || ''}
                        onChange={(e) => setSubCategory && setSubCategory({
                            ...subCategory,
                            description: e.target.value
                        })}
                    />
                </Form.Group>
            </Form>
        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={onHide}>
                Close
            </Button>
            <Button variant="primary" onClick={onSave} disabled={loading}>
                {loading ? 'Saving...' : 'Save Changes'}
            </Button>
        </Modal.Footer>
    </Modal>
);

const ModuleModal: React.FC<ModalProps> = ({show, onHide, onSave, module, setModule, loading}) => (
    <Modal show={show} onHide={onHide}>
        <Modal.Header closeButton>
            <Modal.Title>{module?.id ? 'Edit Module' : 'Add Module'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form>
                <Form.Group controlId="moduleName">
                    <Form.Label>Module Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={module?.name || ''}
                        onChange={(e) => setModule && setModule({...module, name: e.target.value})}
                    />
                </Form.Group>
                <Form.Group controlId="moduleDescription">
                    <Form.Label>Module Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        value={module?.description || ''}
                        onChange={(e) => setModule && setModule({...module, description: e.target.value})}
                    />
                </Form.Group>
            </Form>
        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={onHide}>
                Close
            </Button>
            <Button variant="primary" onClick={onSave} disabled={loading}>
                {loading ? 'Saving...' : 'Save Changes'}
            </Button>
        </Modal.Footer>
    </Modal>
);

export default DepartmentsManager;
