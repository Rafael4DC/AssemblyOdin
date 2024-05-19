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
    const duration = (endDate.getTime() - startDate.getTime()) / 1000 / 60;
    return `${duration} min`;
};
