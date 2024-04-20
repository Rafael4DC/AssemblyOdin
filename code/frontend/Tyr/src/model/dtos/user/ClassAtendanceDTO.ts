import { TechDTO } from './TechDTO';
import { StudentDTO } from './StudentDTO';
import { Tech } from '../../Tech';
import { UserDTOtoUser } from './UserDTO';
import { CurricularUnitDTOToCourse } from './CurricularUnitDTO';
import { DateDTOToDate } from './DateDTO';

export interface ClassAttendanceDTO {
  id: number;
  student: StudentDTO;
  tech: TechDTO;
  attended: boolean;
}

export function ClassAttendanceDTOToTecClass(dto: ClassAttendanceDTO): Tech {
  return {
    id: dto.tech.id,
    teacher: UserDTOtoUser(dto.tech.teacher),
    course: CurricularUnitDTOToCourse(dto.tech.curricularUnit),
    date: DateDTOToDate(dto.tech.date),
    summary: dto.tech.summary,
    personal_attendance: dto.attended,
    students: [{
      id: dto.student.id,
      name: dto.student.username,
      email: dto.student.email,
      credits: dto.student.credits,
      attendance: dto.attended
    }]
  };
}
export function ClassAttendanceDTOListToTecClassList(dtos: ClassAttendanceDTO[]): Tech[] {
  return dtos.map(ClassAttendanceDTOToTecClass);
}
