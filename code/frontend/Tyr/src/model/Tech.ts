import { Student } from './Student';
import { User } from './User';
import { CurricularUnit } from './CurricularUnit';

export interface Tech {
  id?: number;
  teacher?: User;
  course?: CurricularUnit;
  date?: Date;
  summary?: string;
  personal_attendance?: boolean ;
  students?: Student[];
  teacherId?: number;
  CurricularUnitId?: number;
}