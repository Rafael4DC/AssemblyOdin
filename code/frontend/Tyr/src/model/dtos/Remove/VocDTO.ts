import { StudentDTO, StudentDTOtoStudent } from './StudentDTO';
import { Date} from '../../Date';
import { CurricularUnitDTO, CurricularUnitDTOToCourse } from './CurricularUnitDTO';
import {Voc} from "../../Voc";

export interface VocDTO {
  id: number;
  description: string;
  approved: boolean;
  student: StudentDTO;
  curricularUnit: CurricularUnitDTO;
  started: Date;
  ended: Date;
}

export function VocDTOToVoc(dto: VocDTO): Voc {
  const startDate =dto.ended
  const endDate = dto.started
  const lengthInMinutes = (startDate.dayOfMonth - endDate.dayOfMonth) / 60000;
  return {
    id: dto.id,
    description: dto.description,
    //date: startDate, // Using the start date as the 'date' for the VocClass
    //length: lengthInMinutes,
    approved: dto.approved,
    student: StudentDTOtoStudent(dto.student),
    //course: CurricularUnitDTOToCourse(dto.curricularUnit),
  };
}
export function VocDTOListToVocList(dtos: VocDTO[]): Voc[] {
  return dtos.map(VocDTOToVoc);
}