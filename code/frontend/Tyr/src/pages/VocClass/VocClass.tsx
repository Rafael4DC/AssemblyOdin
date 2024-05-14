import * as React from "react";
import { useState } from "react";
import { Form, Button, Container } from "react-bootstrap";
import useVocs from "../../hooks/useVocs";
import {Voc} from "../../model/Voc";

const CreateVocClass = () => {
  const [vocData, setVocData] = useState<Voc>({
    description: "",
    //date: new Date(), // Use a valid default date, such as the current date
    //length: 0,
    approved: false,
    student: { id: 0 },
    //course: { id: 0 },
  });

  const {vocs, error, handleSaveVocClass, handleDeleteVocClass} = useVocs();

  // Mock data
  const students = [{ id: '1', name: 'John Doe' },{ id: '2', name: 'Doe John' }];
  const curricularUnits = [{ id: '1', name: 'Computer Science' },{ id: '2', name: 'Design' }];

  const handleSubmit = (event: { preventDefault: () => void; }) => {
    event.preventDefault();
    handleSaveVocClass(vocData);
    console.log(vocData);
  };

  const handleStudentChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setVocData((prevVocData) => ({
      ...prevVocData,
      student: { ...prevVocData.student, id: Number(e.target.value) },
    }));
  };

  const handleCourseChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setVocData((prevVocData) => ({
      ...prevVocData,
      course: { ...prevVocData, id: Number(e.target.value) },
    }));
  };

  const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setVocData((prevVocData) => ({
      ...prevVocData,
      date: e.target.value ? new Date(e.target.value) : new Date(),
    }));
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
            value={vocData.started instanceof Date ? vocData.started.value$kotlinx_datetime : ""}
            onChange={(e) => setVocData(prevState => prevState/*{ ...vocData, date: new Date(e.target.value) }*/)}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Length (in minutes)</Form.Label>
          <Form.Control
            type="number"
            required
            value={vocData.started.value$kotlinx_datetime}
            onChange={(e) => setVocData(prevState => prevState/*{ ...vocData, length:  Number(e.target.value) }*/)}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Student</Form.Label>
          <Form.Select
            required
            value={vocData.student.id.toString()} // Ensure the value is a string
            onChange={handleStudentChange} // Use the correct handler
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
            value={vocData.id} // Ensure the value is a string
            onChange={handleCourseChange} // Use the correct handler
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
