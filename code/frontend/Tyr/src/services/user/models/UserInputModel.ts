
/**
 * Input model for user
 *
 * @param id the id of the user
 * @param email the email of the user
 * @param username the username of the user
 * @param credits the credits of the user
 * @param role the role of the user
 */
export interface UserInputModel {
    id?: number;
    email?: string;
    username?: string;
    credits?: number;
    role?: number;
}