import { Student } from '../../Student';

export interface StudentDTO {
  id: number;
  username: string;
  email: string;
  credits: number;
}

export function StudentDTOtoStudent(studentDTO: StudentDTO): Student {
  return {
    id: studentDTO.id,
    name: studentDTO.username,
    email: studentDTO.email,
    credits: studentDTO.credits,
    attendance: false
  };
}

export function StudentDTOListToStudentList(studentDTOs: StudentDTO[]): Student[] {
  return studentDTOs.map(StudentDTOtoStudent);
}