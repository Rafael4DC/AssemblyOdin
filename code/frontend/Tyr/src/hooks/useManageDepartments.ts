import {useEffect, useState} from 'react';
import {DepartmentService} from "../services/department/DepartmentService";
import {FieldStudyService} from "../services/fieldstudy/FieldStudyService";
import {ModuleService} from "../services/module/ModuleService";
import {Department, departmentToInput} from "../services/department/models/Department";
import {FieldStudy, fieldStudyToInput} from "../services/fieldstudy/models/FieldStudy";
import {Module, moduleToInput} from "../services/module/models/Module";
import {Failure, Success} from "../services/_utils/Either";
import {handleError} from "../utils/Utils";
import useDepartments from "./useDepartments";

/**
 * Hook to get the departments
 *
 * @returns the departments, error and handles to save and delete a category
 */

const useManageDepartments = () => {
    const {state, setState} = useDepartments();

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

    const getDepartments = async () => {
        const data = await DepartmentService.getAll();
        if (data instanceof Success) {
            setState({type: 'success', departments: data.value.departments, loading: false});
        } else if (data instanceof Failure) {
            setState({type: 'error', message: handleError(data.value)});
        }
    }

    return {
        state,
        handleChangeDepart: async () => {
            if (editingDepartment) {
                setState(prevState => {
                    return {...prevState, loading: true};
                });
                try {
                    if (editingDepartment.id) {
                        await DepartmentService.update(departmentToInput(editingDepartment));
                    } else {
                        await DepartmentService.save(departmentToInput(editingDepartment));
                    }
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
                setState(prevState => {
                    return {...prevState, loading: true};
                });
                try {
                    if (editingFieldStudy.id) {
                        await FieldStudyService.update(fieldStudyToInput(editingFieldStudy));
                    } else {
                        await FieldStudyService.save(fieldStudyToInput(editingFieldStudy));
                    }
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
                setState(prevState => {
                    return {...prevState, loading: true};
                });
            try {
                debugger;
                if (editingModule.id) {
                    await ModuleService.update(moduleToInput(editingModule));
                } else {
                    await ModuleService.save(moduleToInput(editingModule));
                }
                await getDepartments();
                setEditingModule(null);
                setShowModuleModal(false);
            } catch (err) {
                setState({type: 'error', message: err.message || err });
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
