import {Accordion, Card, Container} from 'react-bootstrap';
import {Department} from "../../model/Department";
import * as React from 'react';
import useDepartments from "../../hooks/useDepartments";

/**
 * Page to display the categories
 */
const DepartmentsDisplay: React.FC = () => {
    const {departments} = useDepartments();

    debugger;
    return (
        <Container>
            <h1>Departments</h1>
            <Accordion defaultActiveKey="0">
                {departments ? departments.map((deparment: Department, index: number) => (
                    <Accordion.Item eventKey={index.toString()} key={deparment.id}>
                        <Accordion.Header>{deparment.name}</Accordion.Header>
                        <Accordion.Body>
                            <Card.Text>{deparment.description}</Card.Text>
                            <Accordion>
                                {deparment.fieldsStudy.map((fieldStudy, subIndex) => (
                                    <Accordion.Item eventKey={subIndex.toString()} key={fieldStudy.id}>
                                        <Accordion.Header>{fieldStudy.name}</Accordion.Header>
                                        <Accordion.Body>
                                            <Card.Text>{fieldStudy.description}</Card.Text>
                                            <ul>
                                                {fieldStudy.modules.map((module) => (
                                                    <li key={module.id}>
                                                        <strong>{module.name}:</strong> {module.description}
                                                    </li>
                                                ))}
                                            </ul>
                                        </Accordion.Body>
                                    </Accordion.Item>
                                ))}
                            </Accordion>
                        </Accordion.Body>
                    </Accordion.Item>
                )) : <p>Loading categories...</p>}
            </Accordion>
        </Container>
    );
};

export default DepartmentsDisplay;
