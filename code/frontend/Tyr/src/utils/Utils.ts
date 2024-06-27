import {Tech} from "../services/tech/models/Tech";
import {Voc} from "../services/voc/models/Voc";
import {TextFieldProps} from "@mui/material";

/**
 * Formated the date String of backend to a more readable date and time string
 */
export function toDateTimeStr(date: string): string {
    return new Date(date).toLocaleString();
}

/**
 * Formated the date String of backend to a more readable date string
 */
export function toDateStr(date: string): String {
    return new Date(date).toLocaleDateString();
}

/**
 * Formated the date String of backend to a more readable time string
 */
export function toTimeStr(date: string): string {
    return new Date(date).toLocaleTimeString();
}

/**
 * Formated the date String of backend to a Date object
 */
export function toDate(date: string): Date {
    return new Date(date);
}

/**
 * Get the duration between two dates
 */
export const getDuration = (start?: string, end?: string) => {
    if (!start || !end) return '';
    const startDate = new Date(start);
    const endDate = new Date(end);

    // Check if the dates are valid
    if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) return '';

    // Calculate the duration in minutes
    const duration = (endDate.getTime() - startDate.getTime()) / 1000 / 60;

    // Return the duration in minutes, ensure it's not negative
    return duration >= 0 ? `${duration} min` : '';
};

/**
 * Filter options for the tech classes
 *
 * @param Happened - filter for the happened classes
 * @param ToHappen - filter for the to happen classes
 * @param Upcoming - filter for the upcoming classes
 */
export enum FilterOptions {
    Happened = 'Happened',
    ToHappen = 'To Happen',
    Upcoming = 'Upcoming (Next Week)'
}

/**
 * Capitalize the first letter of a string
 */
export function capitalizeFirstLetter(text: string) {
    return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
}

type Course = Tech | Voc;
type FilterCoursesFunction = (courses: Course[], filter: FilterOptions, dateKey: string) => Course[];

// Utility function to filter courses
export const filterCourses: FilterCoursesFunction = (courses, filter, dateKey) => {
    const now = new Date();
    const oneWeekFromNow = new Date();
    oneWeekFromNow.setDate(now.getDate() + 7);

    return courses.filter((course) => {
        const courseDate = new Date((course as any)[dateKey]);
        switch (filter) {
            case FilterOptions.Happened:
                return courseDate < now;
            case FilterOptions.ToHappen:
                return courseDate >= now;
            case FilterOptions.Upcoming:
                return courseDate >= now && courseDate <= oneWeekFromNow;
            default:
                return true;
        }
    });
};

export function notStudent(role: string) {
    return role === 'TEACHER' || role === 'ADMIN';
}


export const commonTextFieldProps: Partial<TextFieldProps> = {
    fullWidth: true,
    margin: "normal",
    InputLabelProps: { shrink: true },
    InputProps: { style: { color: '#000' } },
    style: { color: '#000' },
};
