import { Tech } from '../../Tech';
import { Date } from '../../Date';
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
  date: Date;
  summary: string;
}


export function TechDTOToTecClass(techDTO: TechDTO): Tech {
  return {
    id: techDTO.id,
    teacher: UserDTOtoUser(techDTO.teacher),
    curricularUnit: CurricularUnitDTOToCourse(techDTO.curricularUnit),
    date: techDTO.date,
    summary: techDTO.summary,
    attendance: false,
    students: []
  };
}

export function TechDTOListToTecClassList(techDTOs: TechDTO[]): Tech[] {
  return techDTOs.map(TechDTOToTecClass);
}