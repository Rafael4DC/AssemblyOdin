import { CurricularUnit } from '../../CurricularUnit';

export interface CurricularUnitDTO {
  id: number;
  name: string;
  description: string;
}

export function CurricularUnitDTOToCourse(cuDTO: CurricularUnitDTO): CurricularUnit {
  return {
    id: cuDTO.id,
    name: cuDTO.name,
    description: cuDTO.description,
    classes: []
  };
}

export function CurricularUnitDTOListToCourseList(cuDTOs: CurricularUnitDTO[]): CurricularUnit[] {
  return cuDTOs.map(CurricularUnitDTOToCourse);
}
