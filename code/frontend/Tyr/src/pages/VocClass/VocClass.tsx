import * as React from "react";
import { useState } from "react";
import { Form, Button, Container } from "react-bootstrap";

const CreateVocClass = () => {
  const [vocData , setVocData] = useState<VocClass>({
    description: "",
    date: "",
    length: 0,
    approved: false,
    studentId: 0,
    curricularUnitId: 0,
  });

  // Mock data
  const students = [{ id: '1', name: 'John Doe' },{ id: '2', name: 'Doe John' }];
  const curricularUnits = [{ id: '1', name: 'Computer Science' },{ id: '2', name: 'Design' }];

  const handleSubmit = (event: { preventDefault: () => void; }) => {
    event.preventDefault();
    console.log(vocData);
  };

  return (
    <Container>
      <h1>Create VOC Class</h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Description</Form.Label>
          <Form.Control
            type="text"
            required
            value={vocData.description}
            onChange={(e) => setVocData({ ...vocData, description: e.target.value})}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Date</Form.Label>
          <Form.Control
            type="date"
            required
            value={vocData.date}
            onChange={(e) => setVocData({ ...vocData, date: e.target.value })}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Length (in minutes)</Form.Label>
          <Form.Control
            type="number"
            required
            value={vocData.length}
            onChange={(e) => setVocData({ ...vocData, length:  Number(e.target.value) })}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Student</Form.Label>
          <Form.Select
            required
            value={vocData.studentId}
            onChange={(e) => setVocData({ ...vocData, studentId:  Number(e.target.value) })}
          >
            {students.map(student => (
              <option key={student.id} value={student.id}>{student.name}</option>
            ))}
          </Form.Select>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Curricular Unit</Form.Label>
          <Form.Select
            required
            value={vocData.curricularUnitId}
            onChange={(e) => setVocData({ ...vocData, curricularUnitId:  Number(e.target.value) })}
          >
            {curricularUnits.map(unit => (
              <option key={unit.id} value={unit.id}>{unit.name}</option>
            ))}
          </Form.Select>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Check
            type="checkbox"
            label="Approved"
            checked={vocData.approved}
            onChange={(e) => setVocData({ ...vocData, approved: e.target.checked })}
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Create VOC Class
        </Button>
      </Form>
    </Container>
  );
};

export default CreateVocClass;
