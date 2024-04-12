import useUserInfo from '../../hooks/useUserInfo';
import TecTable from '../../components/Profile/TecTable';
import VocTable from '../../components/Profile/VocTable';
import * as React from 'react';
import { Col, Container, Image, Row } from 'react-bootstrap';


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
            <TecTable />
          </Col>
          <Col className="mb-3">
            <VocTable />
          </Col>
        </Row>
      </Container>
  );
}

export default Profile;
