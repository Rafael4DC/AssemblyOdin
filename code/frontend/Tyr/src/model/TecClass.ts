import { Student } from './Student';
import { User } from './User';
import { Course } from './Course';

export interface TecClass {
  id?: number;
  teacher?: User;
  course?: Course;
  date?: Date;
  summary?: string;
  personal_attendance?: boolean ;
  students?: Student[];
}