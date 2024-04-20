import { TechDTO } from './TechDTO';
import { StudentDTO } from './StudentDTO';
import { TecClass } from '../../TecClass';
import { UserDTOtoUser } from './UserDTO';
import { CurricularUnitDTOToCourse } from './CurricularUnitDTO';
import { DateDTOToDate } from './DateDTO';

interface ClassAttendanceDTO {
  id: number;
  student: StudentDTO;
  tech: TechDTO;
  attended: boolean;
}

function ClassAttendanceDTOToTecClass(dto: ClassAttendanceDTO): TecClass {
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
function ClassAttendanceDTOListToTecClassList(dtos: ClassAttendanceDTO[]): TecClass[] {
  return dtos.map(ClassAttendanceDTOToTecClass);
}
