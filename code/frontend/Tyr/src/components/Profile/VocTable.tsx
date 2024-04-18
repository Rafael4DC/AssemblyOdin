import * as React from 'react';
import { useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { TecClassPersonal } from '../../model/TecClassPersonal';





enum FilterOptions {
  Happened = 'Happened',
  ToHappen = 'To Happen',
  Upcoming = 'Upcoming (Next Week)'
}


const maxRows = 5;
const fixedRowHeight = '60px';
const tableMaxHeight = maxRows * parseInt(fixedRowHeight, 10);

const scrollableTableStyle: React.CSSProperties = {
  maxHeight: `${tableMaxHeight}px`,
  overflowY: 'auto',
};

interface VocTableProps {
  courses: VocClass[];
}

const VocTable : React.FC<VocTableProps> = ({courses}) => {
  const [filter, setFilter] = useState(FilterOptions.Happened);

  const handleEditClick = (course: VocClass) => {
    console.log('Edit course:', course);
  };

  const renderCourseRow = (course: VocClass) => (
    <tr key={course.description + course.date} style={{ height: fixedRowHeight }}>
      <td>{course.description}</td>
      <td>{course.date}</td>
      <td>{course.approved ? '✓' : '—'}</td>
    </tr>
  );

  return (
    <div>
      <h3>Voc Courses</h3>
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
          <th>Date</th>
          <th>Approved</th>
        </tr>
        </thead>
        <tbody>
        {courses.map(renderCourseRow)}
        </tbody>
      </Table>
      </div>
    </div>
  );
}

export default VocTable;
