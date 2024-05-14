import {Student} from "./Student";
import {Tech} from "./Tech";

export interface ClassAttendance {
    id?: number;
    student?: Student;
    tech?: Tech;
    attended?: boolean;
}
