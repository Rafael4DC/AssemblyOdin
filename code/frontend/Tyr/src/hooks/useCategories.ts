import { useEffect, useState } from 'react';
import { CategoryService } from "../services/CategoryService";
import { SubCategoryService } from "../services/SubCategoryService";
import { ModuleService } from "../services/ModuleService";
import { Category } from "../model/Category";
import { SubCategory } from "../model/SubCategory";
import { Module } from "../model/Module";

/**
 * Hook to get the categories
 *
 * @returns the categories, error and handles to save and delete a category
 */
const useCategories = () => {
    const [categories, setCategories] = useState<Category[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        CategoryService.getAll()
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
        handleSaveCategory: async (category: Category) => {
            setError(null);
            try {
                if (category.id) {
                    await CategoryService.update(category);
                } else {
                    await CategoryService.save(category);
                }
                const updatedCategories = await CategoryService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteCategory: async (id: number) => {
            setError(null);
            try {
                await CategoryService.delete(id);
                const updatedCategories = await CategoryService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        },
        handleSaveSubCategory: async (subCategory: SubCategory) => {
            setError(null);
            debugger;
            try {
                if (subCategory.id) {
                    await SubCategoryService.update(subCategory);
                } else {
                    await SubCategoryService.save(subCategory);
                }
                const updatedCategories = await CategoryService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteSubCategory: async (subCategoryId: number) => {
            setError(null);
            try {
                await SubCategoryService.delete(subCategoryId);
                const updatedCategories = await CategoryService.getAll();
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
                const updatedCategories = await CategoryService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteModule: async (moduleId: number) => {
            setError(null);
            try {
                await ModuleService.delete(moduleId);
                const updatedCategories = await CategoryService.getAll();
                setCategories(updatedCategories);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useCategories;
