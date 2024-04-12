import * as React from 'react';
import { useState } from 'react';
import { Button, Form, Table } from 'react-bootstrap';
import TextModal from '../Shared/TextModal';

interface Course {
  name: string;
  teacher: string;
  date: string;
  attendance: boolean;
  summary: string;
}

const courses: Course[] = [
  {
    name: 'Code VI',
    teacher: 'Tomas Santos',
    date: '10/10/2002',
    attendance: true,
    summary: 'LOREM IPSUM',
  },
  {
    name: 'Code VI',
    teacher: 'Tomas Santos',
    date: '18/10/2002',
    attendance: false,
    summary: 'LOREM IPSUM',
  },
];

enum FilterOptions {
  Happened = 'Happened',
  ToHappen = 'To Happen',
  Upcoming = 'Upcoming (Next Week)'
}

function TecTable() {
  const [filter, setFilter] = useState(FilterOptions.Happened);
  const [modalShow, setModalShow] = useState(false);
  const [currentSummary, setCurrentSummary] = useState('');

  const renderCourseRow = (course: Course) => (
    <tr key={course.name + course.date}>
      <td>{course.name}</td>
      <td>{course.teacher}</td>
      <td>{course.date}</td>
      <td>
        <Form.Check
          type="checkbox"
          checked={course.attendance}
        />
      </td>
      <td>
        <Button variant="link" onClick={() => {
          setCurrentSummary(course.summary);
          setModalShow(true);
        }}>
          View Summary
        </Button>
      </td>
    </tr>
  );

  return (
    <div>
      <h3>Tec Courses</h3>
      <select
        className="form-select mb-3"
        value={filter}
        onChange={(e) => setFilter(e.target.value as FilterOptions)}
      >
        {Object.values(FilterOptions).map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>

      <Table striped bordered hover responsive>
        <thead>
        <tr>
          <th>Name</th>
          <th>Teacher</th>
          <th>Date</th>
          <th>Attendance</th>
          <th>Summary</th>
        </tr>
        </thead>
        <tbody>
        {courses.map(renderCourseRow)}
        </tbody>
      </Table>

      <TextModal
        title="Course Summary"
        content={currentSummary}
        show={modalShow}
        handleClose={() => setModalShow(false)}
      />
    </div>
  );
}

export default TecTable;