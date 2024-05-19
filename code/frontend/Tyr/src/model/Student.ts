/**
 * Interface for the Student model
 *
 * @param id the id of the student
 * @param email the email of the student
 * @param username the username of the student
 * @param credits the credits of the student
 *
 * @param attendance if the student attended the class
 */
export interface Student {
    id?: number;
    email?: string;
    username?: string;
    credits?: number;

    attendance?: boolean;
}
