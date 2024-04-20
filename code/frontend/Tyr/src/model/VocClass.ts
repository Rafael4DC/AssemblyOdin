import { CurricularUnit } from './CurricularUnit';
import { Student } from './Student';

export interface VocClass {
  id?: number,
  description?: string,
  date?: Date,
  length?: number,
  approved?: boolean,
  student?: Student,
  course?: CurricularUnit,
}
