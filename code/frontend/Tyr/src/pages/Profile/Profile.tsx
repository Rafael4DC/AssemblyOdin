import useUserInfo from '../../hooks/useUserInfo';
import TecTable from '../../components/Profile/TecTable';
import VocTable from '../../components/Profile/VocTable';
import * as React from 'react';
import { Col, Container, Image, Row } from 'react-bootstrap';
import { TecClassPersonal } from '../../model/TecClassPersonal';
import tecTable from '../../components/Profile/TecTable';

const tecCourses: TecClassPersonal[] = [
  {

    id: 1,
    teacher: 'Tomas Santos',
    date: '10/10/2002',
    summary: 'LOREM IPSUM',
    course_name: 'Code VI',
    attendance: true,
  },
  {
    id: 2,
    teacher: 'Manuel Santos',
    date: '10/10/2002',
    summary: 'LOREM IPSUM',
    course_name: 'Design',
    attendance: true,
  },
];

const vocCourses: VocClass[] = [
  {
    description: 'Code VI Practical',
    date: '11/10/2002',
    length: 60,
    approved: true,
    studentId: 0,
    curricularUnitId: 0,
  },
  {
    description: 'Code I Practical',
    date: '20/05/2024',
    length: 120,
    approved: true,
    studentId: 0,
    curricularUnitId: 0,
  },
];

function Profile() {
  const { userInfo, isLoading, error } = useUserInfo();

  if (isLoading) return <div className="text-center my-5">
    <div className="spinner-border" role="status"></div>
  </div>;
  if (error) return <div className="alert alert-danger" role="alert">Error: {error.message}</div>;

  return (
    <Container fluid className="p-5 text-center">
        <Row className="justify-content-center my-3">
          <Col md={6} lg={4} className="mb-3">
            <Image src="https://bootdey.com/img/Content/avatar/avatar7.png" roundedCircle width="150" />
            <div className="mt-3">
              <h4>{userInfo.name}</h4>
              <p className="text-secondary mb-1">{userInfo.role}</p>
              <p className="text-muted mb-1">{userInfo.email}</p>
              <p className="text-muted font-size-sm">Pontos: {userInfo.credits}</p>
            </div>
          </Col>
        </Row>
      <Row>
        <Col className="mb-3">
          <TecTable courses={tecCourses}/>
        </Col>
        <Col className="mb-3">
          <VocTable courses={vocCourses}/>
        </Col>
      </Row>
    </Container>
  );
}

export default Profile;
