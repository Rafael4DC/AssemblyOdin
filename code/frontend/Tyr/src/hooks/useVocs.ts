import {useEffect, useState} from 'react';
import {VocService} from '../services/VocService';
import {Voc} from "../model/Voc";

/**
 * Hook to get the voc classes
 *
 * @returns the voc classes, error and handles to save and delete a voc class
 */
const useVocs = () => {
    const [vocs, setVocs] = useState<Voc[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        VocService.getAll()
            .then(data => {
                setVocs(data);
            })
            .catch(err => {
                setError(err);
            });
    }, []);


    return {
        vocs,
        error,
        handleSaveVocClass: async (vocClass: Voc) => {
            setError(null);
            try {
                if (vocClass.id) {
                    return await VocService.update(vocClass);
                } else {
                    return await VocService.save(vocClass);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteVocClass: async (id: number) => {
            setError(null);
            try {
                await VocService.delete(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useVocs;