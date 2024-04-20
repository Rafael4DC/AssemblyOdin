import { TecClass } from './TecClass';

export interface Course {
  id?: number;
  title?: string;
  description?: string;
  classes?: TecClass[];
}
