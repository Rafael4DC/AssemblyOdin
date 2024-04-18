import * as React from 'react';
import { useState } from 'react';
import { Form, Table } from 'react-bootstrap';
import DetailView from './DetailView';


const coursesMock: Course[] = [
  {
    id: 1,
    title: 'Code VI',
    description: 'An advanced programming course covering various software engineering principles.',
    classes: [
      {
        id: 101,
        teacher: 'Tomás Santos',
        date: '10/10/2024',
        summary: 'Covered basic syntax and script execution.',
        course: null,
        students: [
          { id: 1, name: 'Manuel Tobias', attendance: null },
          { id: 2, name: 'Jakelino Zé', attendance: null },
          { id: 3, name: 'MadsaTobias', attendance: null },
          { id: 4, name: 'Jakafddasfdelino Zé', attendance: null },
        ],
      },
      {
        id: 102,
        teacher: 'Tomás Santos',
        date: '15/10/2024',
        summary: 'Covered basic syntax and script execution.',
        course: null,
        students: [
          { id: 1, name: 'Manuel Tobias', attendance: null },
          { id: 2, name: 'Jakelino Zé', attendance: null },
        ],
      },
    ],
  },
  {
    id: 2,
    title: 'Python Game Maker',
    description: 'Learn game development fundamentals using Python and Pygame.',
    classes: [
      {
        id: 201,
        teacher: 'Ana Silva',
        date: '11/10/2024',
        summary: 'Covered basic syntax and script execution.',
        course: null,
        students: [
          { id: 3, name: 'Ormonda Luis', attendance: null },
          { id: 4, name: 'Alina Costa', attendance: null },
        ],
      },
    ],
  },
];


enum FilterOptions {
  Legacy = 'Legacy',
  Ongoing = 'Ongoing',
  ToStart = 'Upcoming'
}


function CurricularUnitManager() {
  const [filter, setFilter] = useState(FilterOptions.Ongoing);
  const [courses, setCourses] = useState(coursesMock);
  const [selectedCourse, setSelectedCourse] = useState<Course | null>(null);

  const saveCourse = (updatedCourse: Course) => {
    const updatedCourses = courses.map(course =>
      course.id === updatedCourse.id ? updatedCourse : course,
    );
    setCourses(updatedCourses);
    setSelectedCourse(updatedCourse);
    console.log(selectedCourse);
  };

  const renderCourseRow = (course: Course) => (
    <tr key={course.title} onClick={() => setSelectedCourse(course)}>
      <td>{course.title}</td>
      <td>{getUniqueTeachers(course)}</td>
      <td>{getUniqueWeekdays(course)}</td>
    </tr>
  );

  const getUniqueTeachers = (course: Course) => {
    const teacherSet = new Set(course.classes.map(cls => cls.teacher));
    return Array.from(teacherSet).join(', ');
  };

  const getUniqueWeekdays = (course: Course) => {
    const dateSet = new Set(course.classes.map(cls => {
      const date = new Date(cls.date.split('/').reverse().join('-'));
      return date.toLocaleString('en-US', { weekday: 'long' });
    }));
    return Array.from(dateSet).join(', ');
  };

  return (
    <div>
      <h3>Tec Courses Manager</h3>
      <Form.Select
        aria-label="Filter courses"
        className="mb-3"
        value={filter}
        onChange={(e) => setFilter(e.target.value as FilterOptions)}
      >
        {Object.values(FilterOptions).map(option => (
          <option key={option} value={option}>{option}</option>
        ))}
      </Form.Select>
      <div style={{ maxHeight: '250px', overflowY: 'auto' }}>

        <Table striped bordered hover responsive>

          <thead>
          <tr>
            <th>Title</th>
            <th>Teachers</th>
            <th>Dates</th>
          </tr>
          </thead>
          <tbody>
          {courses.map(renderCourseRow)}
          </tbody>
        </Table>
      </div>

      {
        selectedCourse && (
          <DetailView course={selectedCourse} onSave={saveCourse} />
        )}
    </div>
  );
}

export default CurricularUnitManager;
