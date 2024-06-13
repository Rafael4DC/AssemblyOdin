import {useEffect, useState} from 'react';
import {TechService} from '../services/TechService';
import {Voc} from '../model/Voc';
import {VocService} from '../services/VocService';
import {Tech} from "../model/Tech";

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
        TechService.getMyTechsAttendance()
            .then(data => setTechClasses(data))
            .catch(err => console.error(err));
*/

        VocService.getAll()
            .then(data => setVocClasses(data))
            .catch(err => console.error(err));
    }, []);

    const filteredTechClasses = techClasses.filter(cls => {
        const classDate = new Date(cls.tech.date);
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
