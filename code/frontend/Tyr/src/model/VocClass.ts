import { Course } from './Course';
import { Student } from './Student';

export interface VocClass {
  id?: number,
  description?: string,
  date?: Date,
  length?: number,
  approved?: boolean,
  student?: Student,
  course?: Course,
}
