import {useEffect, useState} from 'react';
import {CategoryService} from "../services/CategoryService";
import {Category} from "../model/Category";

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
                    return await CategoryService.update(category);
                } else {
                    return await CategoryService.save(category);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteCategory: async (id: number) => {
            setError(null);
            try {
                await CategoryService.delete(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useCategories;