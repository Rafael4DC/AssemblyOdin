import {StudentDTO} from "./dtos/user/StudentDTO";
import {TechDTO} from "./dtos/user/TechDTO";

export interface ClassAttendance {
    id: number;
    student: StudentDTO;
    tech: TechDTO;
    attended: boolean;
    studentId: number;
    techId: number;
}