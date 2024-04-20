import { Tech } from '../../Tech';
import { DateDTO, DateDTOToDate } from './DateDTO';
import { UserDTOtoUser } from './UserDTO';
import { CurricularUnitDTOToCourse } from './CurricularUnitDTO';

export interface TechDTO {
  id: number;
  teacher: {
    id: number;
    username: string;
    email: string;
    credits: number;
  };
  curricularUnit: {
    id: number;
    name: string;
    description: string;
  };
  date: DateDTO;
  summary: string;
}


export function TechDTOToTecClass(techDTO: TechDTO): Tech {
  return {
    id: techDTO.id,
    teacher: UserDTOtoUser(techDTO.teacher),
    course: CurricularUnitDTOToCourse(techDTO.curricularUnit),
    date: DateDTOToDate(techDTO.date),
    summary: techDTO.summary,
    personal_attendance: false,
    students: []
  };
}

export function TechDTOListToTecClassList(techDTOs: TechDTO[]): Tech[] {
  return techDTOs.map(TechDTOToTecClass);
}