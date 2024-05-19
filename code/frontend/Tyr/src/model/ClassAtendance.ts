import {Student} from "./Student";
import {Tech} from "./Tech";

/**
 * Class attendance
 *
 * @param id - the id
 * @param student - the student
 * @param tech - the tech
 * @param attended - if the student attended the class
 */
export interface ClassAttendance {
    id?: number;
    student?: Student;
    tech?: Tech;
    attended?: boolean;
}
