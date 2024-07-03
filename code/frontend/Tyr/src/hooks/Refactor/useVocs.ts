import {useEffect, useState} from 'react';
import {VocService} from '../../services/voc/VocService';
import {Voc} from "../../services/voc/models/Voc";
import {Failure, Success} from "../../services/_utils/Either";

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
        handleSaveVocClass: async (vocClass: Voc) => {
            setError(null);
            debugger
            try {
                if (vocClass.id) {
                    return await VocService.update({
                        id: vocClass.id,
                        description: vocClass.description,
                        started: vocClass.started,
                        ended: vocClass.ended,
                        approved: vocClass.approved,
                        user: vocClass.user.id,
                        section: vocClass.section.id
                    });
                } else {
                    return await VocService.save({
                        description: vocClass.description,
                        started: vocClass.started,
                        ended: vocClass.ended,
                        approved: vocClass.approved,
                        user: vocClass.user.id,
                        section: vocClass.section.id
                    });
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