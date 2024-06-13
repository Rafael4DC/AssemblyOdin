import { useEffect, useState } from 'react';
import { DepartmentService } from "../services/DepartmentService";
import { FieldStudyService } from "../services/FieldStudyService";
import { ModuleService } from "../services/ModuleService";
import { Department } from "../model/Department";
import { FieldStudy } from "../model/FieldStudy";
import { Module } from "../model/Module";

/**
 * Hook to get the categories
 *
 * @returns the categories, error and handles to save and delete a category
 */
const useCategories = () => {
    const [categories, setCategories] = useState<Department[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        DepartmentService.getAll()
            .then(data => {
                setCategories(data);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {
        categories,
        error,
        handleSaveCategory: async (category: Department) => {
            setError(null);
            try {
                if (category.id) {
                    await DepartmentService.update(category);
                } else {
                    await DepartmentService.save(category);
                }
                const updatedCategories = await DepartmentService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteCategory: async (id: number) => {
            setError(null);
            try {
                await DepartmentService.delete(id);
                const updatedCategories = await DepartmentService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        },
        handleSaveSubCategory: async (subCategory: FieldStudy) => {
            setError(null);
            debugger;
            try {
                if (subCategory.id) {
                    await FieldStudyService.update(subCategory);
                } else {
                    await FieldStudyService.save(subCategory);
                }
                const updatedCategories = await DepartmentService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteSubCategory: async (subCategoryId: number) => {
            setError(null);
            try {
                await FieldStudyService.delete(subCategoryId);
                const updatedCategories = await DepartmentService.getAll();
                setCategories(updatedCategories);
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
                const updatedCategories = await DepartmentService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteModule: async (moduleId: number) => {
            setError(null);
            try {
                await ModuleService.delete(moduleId);
                const updatedCategories = await DepartmentService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useCategories;
