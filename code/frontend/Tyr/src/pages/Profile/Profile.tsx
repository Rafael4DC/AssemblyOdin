import useUserInfo from '../../hooks/useUserInfo';
import TecTable from '../../components/Profile/TecTable';
import VocTable from '../../components/Profile/VocTable';
import * as React from 'react';
import {Col, Container, Image, Row} from 'react-bootstrap';
import {Spinner} from "../../utils/Spinner";
import {AlertError} from "../../utils/AlertError";

function Profile() {
  const { userInfo, userTechs, userVocs, error } = useUserInfo();

  if (userInfo == null || userTechs == null) return <Spinner/>
  if (error) return <AlertError error={error} />;

  return (
    <Container fluid className="p-5 text-center">
        <Row className="justify-content-center my-3">
          <Col md={6} lg={4} className="mb-3">
            <Image src="https://bootdey.com/img/Content/avatar/avatar7.png" roundedCircle width="150" />
            <div className="mt-3">
              <h4>{userInfo.username}</h4>
              <p className="text-secondary mb-1">{userInfo.role}</p>
              <p className="text-muted mb-1">{userInfo.email}</p>
              <p className="text-muted font-size-sm">Pontos: {userInfo.credits}</p>
            </div>
          </Col>
        </Row>
      <Row>
        <Col className="mb-3">
          <TecTable techs={userTechs}/>
        </Col>
        <Col className="mb-3">
          <VocTable courses={userVocs}/>
        </Col>
      </Row>
    </Container>
  );
}

export default Profile;
