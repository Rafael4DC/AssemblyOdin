import { TecClass } from '../../TecClass';
import { TechDTO } from './TechDTO';

export interface DateDTO {
  year: number;
  dayOfMonth: number;
  month: string;
  dayOfWeek: string;
  dayOfYear: number;
  value$kotlinx_datetime: string;
  monthNumber: number;
}
export function DateDTOToDate(date: DateDTO): Date {
  return new Date(date.value$kotlinx_datetime)
}