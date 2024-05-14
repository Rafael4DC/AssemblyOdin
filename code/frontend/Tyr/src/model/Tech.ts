import { User } from './User';
import { CurricularUnit } from './CurricularUnit';
import {Date} from "./Date";

export interface Tech {
  id?: number;
  teacher?: User;
  curricularUnit?: CurricularUnit;
  date?: Date;
  summary?: string;

  attendance?: boolean;
  students?: User[];
}
