import {useEffect, useState} from 'react';
import {VocService} from '../../services/voc/VocService';
import {Voc} from "../../services/voc/models/Voc";
import {Failure, Success} from "../../services/_utils/Either";
import {VocInputModel} from "../../services/voc/models/VocInputModel";

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
                if (data instanceof Success) {
                    setVocs(data.value.vocs);
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            })
            .catch(err => {
                setError(err);
            });
    }, []);


    return {
        vocs,
        error,
        handleSaveVocClass: async (vocClass: VocInputModel) => {
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
                await VocService.deleteById(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useVocs;