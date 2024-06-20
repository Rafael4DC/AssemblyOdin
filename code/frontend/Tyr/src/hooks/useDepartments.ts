import { useEffect, useState } from 'react';
import { DepartmentService } from "../services/department/DepartmentService";
import { FieldStudyService } from "../services/fieldstudy/FieldStudyService";
import { ModuleService } from "../services/module/ModuleService";
import { Department } from "../services/department/models/Department";
import { FieldStudy } from "../services/fieldstudy/models/FieldStudy";
import { Module } from "../services/module/models/Module";

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
                setDepartments(data.departments);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {
        departments,
        error,
        handleSaveDepartments: async (category: Department) => {
            setError(null);
            try {
                if (category.id) {
                    await DepartmentService.update(category);
                } else {
                    await DepartmentService.save(category);
                }
                const updatedDepartments = await DepartmentService.getAll();
                setDepartments(updatedDepartments.departments);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteDepartments: async (id: number) => {
            setError(null);
            try {
                await DepartmentService.delete(id);
                const updatedDepartments = await DepartmentService.getAll();
                setDepartments(updatedDepartments.departments);
            } catch (err) {
                setError(err);
            }
        },
        handleSaveFieldsStudy: async (subCategory: FieldStudy) => {
            setError(null);
            debugger;
            try {
                if (subCategory.id) {
                    await FieldStudyService.update(subCategory);
                } else {
                    await FieldStudyService.save(subCategory);
                }
                const updatedDepartments = await DepartmentService.getAll();
                setDepartments(updatedDepartments.departments);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteFieldsStudy: async (subCategoryId: number) => {
            setError(null);
            try {
                await FieldStudyService.delete(subCategoryId);
                const updatedDepartments = await DepartmentService.getAll();
                setDepartments(updatedDepartments.departments);
            } catch (err) {
                setError(err);
            }
        },
        handleSaveModule: async (module: Module) => {
            setError(null);
            try {
                if (module.id) {
                    await ModuleService.update(module);
                } else {
                    await ModuleService.save(module);
                }
                const updatedDepartments = await DepartmentService.getAll();
                setDepartments(updatedDepartments.departments);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteModule: async (moduleId: number) => {
            setError(null);
            try {
                await ModuleService.delete(moduleId);
                const updatedDepartments = await DepartmentService.getAll();
                setDepartments(updatedDepartments.departments);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useDepartments;
