import {useEffect, useState} from 'react';
import {DepartmentService} from "../services/department/DepartmentService";
import {FieldStudyService} from "../services/fieldstudy/FieldStudyService";
import {ModuleService} from "../services/module/ModuleService";
import {Department} from "../services/department/models/Department";
import {FieldStudy} from "../services/fieldstudy/models/FieldStudy";
import {Module} from "../services/module/models/Module";
import {Failure, Success} from "../services/_utils/Either";

/**
 * Hook to get the categories
 *
 * @returns the categories, error and handles to save and delete a category
 */
const useDepartments = () => {
    const [departments, setDepartments] = useState<Department[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        DepartmentService.getAll()
            .then(data => {
                if (data instanceof Success) {
                    setDepartments(data.value.departments);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {
        departments,
        error,
        handleSaveDepartments: async (department: Department) => {
            setError(null);
            try {
                if (department.id) {
                    await DepartmentService.update({
                        id: department.id,
                        name: department.name,
                        description: department.description
                    });
                } else {
                    await DepartmentService.save({
                        name: department.name,
                        description: department.description
                    });
                }
                const updatedDepartments = await DepartmentService.getAll();
                if (updatedDepartments instanceof Success) {
                    setDepartments(updatedDepartments.value.departments);
                } else if (updatedDepartments instanceof Failure) {
                    console.error('Error fetching data:', updatedDepartments.value);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteDepartments: async (id: number) => {
            setError(null);
            try {
                await DepartmentService.deleteById(id);
                const updatedDepartments = await DepartmentService.getAll();
                if (updatedDepartments instanceof Success) {
                    setDepartments(updatedDepartments.value.departments);
                } else if (updatedDepartments instanceof Failure) {
                    console.error('Error fetching data:', updatedDepartments.value);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleSaveFieldsStudy: async (fieldStudy: FieldStudy) => {
            setError(null);
            debugger;
            try {
                if (fieldStudy.id) {
                    await FieldStudyService.update({
                        id: fieldStudy.id,
                        name: fieldStudy.name,
                        description: fieldStudy.description,
                        department: fieldStudy.department.id

                    });
                } else {
                    await FieldStudyService.save({
                        name: fieldStudy.name,
                        description: fieldStudy.description,
                        department: fieldStudy.department.id
                    });
                }
                const updatedDepartments = await DepartmentService.getAll();
                if (updatedDepartments instanceof Success) {
                    setDepartments(updatedDepartments.value.departments);
                } else if (updatedDepartments instanceof Failure) {
                    console.error('Error fetching data:', updatedDepartments.value);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteFieldsStudy: async (subCategoryId: number) => {
            setError(null);
            try {
                await FieldStudyService.deleteById(subCategoryId);
                const updatedDepartments = await DepartmentService.getAll();
                if (updatedDepartments instanceof Success) {
                    setDepartments(updatedDepartments.value.departments);
                } else if (updatedDepartments instanceof Failure) {
                    console.error('Error fetching data:', updatedDepartments.value);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleSaveModule: async (module: Module) => {
            setError(null);
            try {
                if (module.id) {
                    await ModuleService.update({
                        id: module.id,
                        name: module.name,
                        description: module.description,
                        fieldStudy: module.fieldStudy.id
                    });
                } else {
                    await ModuleService.save({
                        name: module.name,
                        description: module.description,
                        fieldStudy: module.fieldStudy.id
                    });
                }
                const updatedDepartments = await DepartmentService.getAll();
                if (updatedDepartments instanceof Success) {
                    setDepartments(updatedDepartments.value.departments);
                } else if (updatedDepartments instanceof Failure) {
                    console.error('Error fetching data:', updatedDepartments.value);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteModule: async (moduleId: number) => {
            setError(null);
            try {
                await ModuleService.deleteById(moduleId);
                const updatedDepartments = await DepartmentService.getAll();
                if (updatedDepartments instanceof Success) {
                    setDepartments(updatedDepartments.value.departments);
                } else if (updatedDepartments instanceof Failure) {
                    console.error('Error fetching data:', updatedDepartments.value);
                }
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useDepartments;
