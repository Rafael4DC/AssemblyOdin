import {useEffect, useState} from 'react';
import {Voc} from '../../services/voc/models/Voc';
import {VocService} from '../../services/voc/VocService';
import {Tech} from "../../services/tech/models/Tech";
import {Failure, Success} from "../../services/_utils/Either";

/**
 * Filter options for the classes
 *
 * @param All - filter for all classes
 * @param Previous - filter for the previous classes
 * @param Future - filter for the future classes
 */
export enum ClassFilterOptions {
    All = 'All',
    Previous = 'Previous',
    Future = 'Future'
}

/**
 * Techs attendance
 *
 * @param tech - the tech
 * @param attendedStudents - the students that attended the class
 */
export interface TechsAttendance {
    tech: Tech;
    /*attendedStudents: ClassAttendance[];*/
}

/**
 * Hook to get the classes
 *
 * @returns the classes, filter and handles to set the filter
 */
export const useClassManager = () => {
    const [techClasses, setTechClasses] = useState<TechsAttendance[]>([]);
    const [vocClasses, setVocClasses] = useState<Voc[]>([]);
    const [filter, setFilter] = useState<ClassFilterOptions>(ClassFilterOptions.All);

    useEffect(() => {
        /*
                TechService.getTechsByUser()
                    .then(data => setTechClasses(data.techs))
                    .catch(err => console.error(err));
        */

        VocService.getAll()
            .then(data => {
                    if (data instanceof Success) {
                        setVocClasses(data.value.vocs);
                    } else if (data instanceof Failure) {
                        console.error('Error fetching data:', data.value);
                    }
                }
            )
            .catch(err => console.error(err));
    }, []);

    const filteredTechClasses = techClasses.filter(cls => {
        const classDate = new Date(cls.tech.started);
        const now = new Date();
        if (filter === ClassFilterOptions.Previous) return classDate < now;
        if (filter === ClassFilterOptions.Future) return classDate >= now;
        return true;
    });

    const filteredVocClasses = vocClasses.filter(cls => {
        const classDate = new Date(cls.started || '');
        const now = new Date();
        if (filter === ClassFilterOptions.Previous) return classDate < now;
        if (filter === ClassFilterOptions.Future) return classDate >= now;
        return true;
    });

    return {
        techClasses: filteredTechClasses,
        vocClasses: filteredVocClasses,
        filter,
        setFilter,
    };
};
