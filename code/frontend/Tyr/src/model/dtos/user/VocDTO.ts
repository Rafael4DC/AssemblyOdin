import { StudentDTO, StudentDTOtoStudent } from './StudentDTO';
import { DateDTO, DateDTOToDate } from './DateDTO';
import { VocClass } from '../../VocClass';
import { CurricularUnitDTO, CurricularUnitDTOToCourse } from './CurricularUnitDTO';

export interface VocDTO {
  id: number;
  description: string;
  approved: boolean;
  student: StudentDTO;
  curricularUnit: CurricularUnitDTO;
  started: DateDTO;
  ended: DateDTO;
}

export function VocDTOToVoc(dto: VocDTO): VocClass {
  const startDate = DateDTOToDate(dto.ended)
  const endDate = DateDTOToDate(dto.started)
  const lengthInMinutes = (startDate.getTime() - endDate.getTime()) / 60000;
  return {
    id: dto.id,
    description: dto.description,
    date: startDate, // Using the start date as the 'date' for the VocClass
    length: lengthInMinutes,
    approved: dto.approved,
    student: StudentDTOtoStudent(dto.student),
    course: CurricularUnitDTOToCourse(dto.curricularUnit),
  };
}
export function VocDTOListToVocList(dtos: VocDTO[]): VocClass[] {
  return dtos.map(VocDTOToVoc);
}