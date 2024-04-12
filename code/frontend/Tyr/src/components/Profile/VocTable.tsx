import * as React from 'react';
import { useState } from 'react';
import { Button, Table } from 'react-bootstrap';


interface VocCourse {
  name: string;
  date: string;
  approved: boolean;
}

const vocCourses: VocCourse[] = [
  {
    name: 'Code VI Practical',
    date: '11/10/2002',
    approved: true,
  },
  {
    name: 'Code VI Practical',
    date: '11/10/2002',
    approved: false,
  },
  // ... more courses
];

enum FilterOptions {
  Happened = 'Happened',
  ToHappen = 'To Happen',
  Upcoming = 'Upcoming (Next Week)'
}

function VocTable() {
  const [filter, setFilter] = useState(FilterOptions.Happened);

  const handleEditClick = (course: VocCourse) => {
    // Handle the edit action here, such as navigation or displaying a form
    console.log('Edit course:', course);
  };

  const renderCourseRow = (course: VocCourse) => (
    <tr key={course.name + course.date}>
      <td>{course.name}</td>
      <td>{course.date}</td>
      <td>{course.approved ? '✓' : '—'}</td>
      <td>
        <Button variant="link" onClick={() => {
          handleEditClick(course);
        }}>
          Edit
        </Button>
      </td>
    </tr>
  );

  return (
    <div>
      <h3>Voc Courses</h3>
      <select
        className="form-select mb-3" // Bootstrap class for styling select element
        value={filter}
        onChange={(e) => setFilter(e.target.value as FilterOptions)}
      >
        {Object.values(FilterOptions).map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
      <Table striped bordered hover responsive> {/* Bootstrap classes added */}
        <thead>
        <tr>
          <th>Name</th>
          <th>Date</th>
          <th>Approved</th>
          <th>Edit</th>
        </tr>
        </thead>
        <tbody>
        {vocCourses.map(renderCourseRow)}
        </tbody>
      </Table>
    </div>
  );
}

export default VocTable;
