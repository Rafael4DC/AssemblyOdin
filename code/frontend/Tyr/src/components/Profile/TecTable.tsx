import * as React from 'react';
import { useState } from 'react';
import { Button, Form, Table } from 'react-bootstrap';
import TextModal from '../Shared/TextModal';
import { TecClass } from '../../model/TecClass';



enum FilterOptions {
  Happened = 'Happened',
  ToHappen = 'To Happen',
  Upcoming = 'Upcoming (Next Week)'
}

const maxRows = 5; // The threshold for when to start scrolling
const fixedRowHeight = '60px';
const tableMaxHeight = maxRows * parseInt(fixedRowHeight, 10);

const scrollableTableStyle: React.CSSProperties = {
  maxHeight: `${tableMaxHeight}px`,
  overflowY: 'auto',
};

interface TecTableProps {
  courses: TecClass[];
}
const TecTable: React.FC<TecTableProps> = ({ courses }) => {
  const [filter, setFilter] = useState(FilterOptions.Happened);
  const [modalShow, setModalShow] = useState(false);
  const [currentSummary, setCurrentSummary] = useState('');

  const renderCourseRow = (course: TecClass) => (
    <tr key={course.course.title + course.date} style={{ height: fixedRowHeight }}>
      <td>{course.course.title}</td>
      <td>{course.teacher.name}</td>
      <td>{course.date.toLocaleDateString()}</td>
      <td>{course.personal_attendance ? '✓' : '—'}</td>
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
      <div style={scrollableTableStyle}>
      <Table striped bordered hover responsive>
        <thead>
        <tr style={{ height: fixedRowHeight }}>
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
      </div>
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