import { Course } from '../../Course';

export interface CurricularUnitDTO {
  id: number;
  name: string;
  description: string;
}

export function CurricularUnitDTOToCourse(cuDTO: CurricularUnitDTO): Course {
  return {
    id: cuDTO.id,
    title: cuDTO.name,
    description: cuDTO.description,
    classes: []
  };
}

function CurricularUnitDTOListToCourseList(cuDTOs: CurricularUnitDTO[]): Course[] {
  return cuDTOs.map(CurricularUnitDTOToCourse);
}
