import { Tech } from './Tech';

export interface CurricularUnit {
  id?: number;
  name?: string;
  description?: string;


  classes?: Tech[];
}
