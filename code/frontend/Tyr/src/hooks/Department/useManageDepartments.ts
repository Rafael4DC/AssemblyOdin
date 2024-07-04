import {useState} from 'react';
import {DepartmentService} from "../../services/department/DepartmentService";
import {FieldStudyService} from "../../services/fieldstudy/FieldStudyService";
import {ModuleService} from "../../services/module/ModuleService";
import {Department, departmentToInput} from "../../services/department/models/Department";
import {FieldStudy, fieldStudyToInput} from "../../services/fieldstudy/models/FieldStudy";
import {Module, moduleToInput} from "../../services/module/models/Module";
import useDepartments from "./useDepartments";
import {handleError} from "../../utils/Utils";
import {Failure} from "../../services/_utils/Either";

/**
 * Hook to get the departments
 *
 * @returns the departments, error and handles to save and delete a category
 */

const useManageDepartments = () => {
    const {state, setState, getDepartments} = useDepartments();

    const [showDepartmentModal, setShowDepartmentModal] = useState(false);
    const [showFieldStudyModal, setShowFieldStudyModal] = useState(false);
    const [showModuleModal, setShowModuleModal] = useState(false);

    const [editingDepartment, setEditingDepartment] = useState<Department | null>(null);
    const [editingFieldStudy, setEditingFieldStudy] = useState<FieldStudy | null>(null);
    const [editingModule, setEditingModule] = useState<Module | null>(null);

    const handleDepartmentEdit = (department: Department) => {
        setEditingDepartment(department);
        setShowDepartmentModal(true);
    };

    const handleFieldStudyEdit = (fieldStudy: FieldStudy) => {
        setEditingFieldStudy(fieldStudy);
        setShowFieldStudyModal(true);
    };

    const handleModuleEdit = (module: Module) => {
        setEditingModule(module);
        setShowModuleModal(true);
    };

    return {
        state,
        handleChangeDepart: async () => {
            if (editingDepartment) {
                setState(prevState => ({...prevState, type: 'loading'}));
                try {
                    let data;
                    if (editingDepartment.id) {
                        data = await DepartmentService.update(departmentToInput(editingDepartment));
                    } else {
                        data = await DepartmentService.save(departmentToInput(editingDepartment));
                    }
                    if (data instanceof Failure) setState({type: 'error', message: handleError(data.value)});

                    await getDepartments();
                    setEditingDepartment(null);
                    setShowDepartmentModal(false);
                } catch (err) {
                    setState({type: 'error', message: err.message || err});
                }
            }
        },
        handleChangeFieldsStudy: async () => {
            if (editingFieldStudy) {
                setState(prevState => ({...prevState, type: 'loading'}));
                try {
                    let data;
                    if (editingFieldStudy.id) {
                        data = await FieldStudyService.update(fieldStudyToInput(editingFieldStudy));
                    } else {
                        data = await FieldStudyService.save(fieldStudyToInput(editingFieldStudy));
                    }
                    if (data instanceof Failure) setState({type: 'error', message: handleError(data.value)});

                    await getDepartments();
                    setEditingFieldStudy(null);
                    setShowFieldStudyModal(false);
                } catch (err) {
                    setState({type: 'error', message: err.message || err});
                }
            }
        },
        handleChangeModule: async () => {
            if (editingModule) {
                setState(prevState => ({...prevState, type: 'loading'}));
                try {
                    let data;
                    if (editingModule.id) {
                        data = await ModuleService.update(moduleToInput(editingModule));
                    } else {
                        data = await ModuleService.save(moduleToInput(editingModule));
                    }
                    if (data instanceof Failure) setState({type: 'error', message: handleError(data.value)});

                    await getDepartments();
                    setEditingModule(null);
                    setShowModuleModal(false);
                } catch (err) {
                    setState({type: 'error', message: err.message || err});
                }
            }
        },
        handleDepartmentEdit,
        handleFieldStudyEdit,
        handleModuleEdit,
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
        setEditingModule
    };
};

export default useManageDepartments;
