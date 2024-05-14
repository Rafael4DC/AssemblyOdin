import { Button, Table, Modal, Form } from 'react-bootstrap';
import * as React from 'react';
import { useState } from 'react';
import { Student } from '../../model/Student';

const mockTeacherClasses = [
  {
    id: 1,
    date: '04/16/2024',
    topic: 'CODE VI',
    summary: 'Covered basic syntax and script execution.',
    students: [
      { id: 1, name: 'Alice Johnson', attendance: true },
      { id: 2, name: 'Bob Smith', attendance: false },
      { id: 3, name: 'Charlie Rose', attendance: true },
      // ... more students
    ]
  },
  {
    id: 2,
    date: '04/24/2024',
    topic: 'Python Data Structures',
    summary: 'Discussed lists, dictionaries, sets, and tuples.',
    students: [
      { id: 1, name: 'Alice Johnson', attendance: true },
      { id: 2, name: 'Bob Smith', attendance: true },
      { id: 3, name: 'Charlie Rose', attendance: false },
      // ... more students
    ]
  },
  {
    id: 3,
    date: '05/01/2024',
    topic: 'Control Flow in Python',
    summary: 'Explored if statements, loops, and exception handling.',
    students: [
      { id: 1, name: 'Alice Johnson', attendance: true },
      { id: 2, name: 'Bob Smith', attendance: true },
      { id: 3, name: 'Charlie Rose', attendance: true },
    ]
  },
];
enum ClassFilterOptions {
  All = 'All',
  Previous = 'Previous',
  Future = 'Future'
}


interface TeacherClass {
  id: number;
  date: string;
  topic: string;
  summary: string;
  students: Student[];
}


const TeacherClassManager: React.FC = () => {
  const [filter, setFilter] = useState(ClassFilterOptions.All);
  const [classes, setClasses] = useState(mockTeacherClasses);
  const [isEditing, setIsEditing] = useState(false);
  const [selectedClass, setSelectedClass] = useState<TeacherClass | null>(null);
  const [showEditModal, setShowEditModal] = useState(false);

  const handleEditClassClick = (cls: TeacherClass) => {
    setSelectedClass(cls);
    setShowEditModal(true);
  };

  const handleSaveClass = (updatedClass: TeacherClass) => {
    console.log('Saving class:', updatedClass);
    setShowEditModal(false);
  };

  const handleAttendanceChange = (studentId: number, attendance: boolean) => {
    console.log(`Student ${studentId} attendance changed to ${attendance}`)
  };

  const handleSummaryChange = (text: string) => {
    console.log(text);
  };

  const renderClassRow = (cls: TeacherClass) => (
    <tr key={cls.id} onClick={() => handleEditClassClick(cls)}>
      <td>{cls.date}</td>
      <td>{cls.topic}</td>
      <td>{cls.summary}</td>
      <td>
        <Button variant="outline-primary" onClick={() => handleEditClassClick(cls)}>Manage</Button>
      </td>
    </tr>
  );

  const filteredClasses = classes.filter(cls => {
    if (filter === ClassFilterOptions.Previous) {
      return new Date(cls.date) < new Date(); // Assuming date format is 'MM/DD/YYYY'
    }
    if (filter === ClassFilterOptions.Future) {
      return new Date(cls.date) >= new Date();
    }
    return true;
  });

  return (
    <div>
      <h3>My Classes Manager</h3>
      <Form.Select
        aria-label="Filter classes"
        className="mb-3"
        value={filter}
        onChange={(e) => setFilter(e.target.value as ClassFilterOptions)}
      >
        {Object.values(ClassFilterOptions).map(option => (
          <option key={option} value={option}>{option}</option>
        ))}
      </Form.Select>
      <div style={{ maxHeight: '250px', overflowY: 'auto' }}>
        <Table striped bordered hover responsive>
          <thead>
          <tr>
            <th>Date</th>
            <th>Topic</th>
            <th>Summary</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          {filteredClasses.map(renderClassRow)}
          </tbody>
        </Table>
      </div>
      <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Class</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            {selectedClass && selectedClass.students.map(student => (
              <Form.Group controlId={`student-attendance-${student.id}`} key={student.id}>
                <Form.Check
                  type="checkbox"
                  label={student.username}
                  checked={student.attendance}
                  onChange={(e) => handleAttendanceChange(student.id, e.target.checked)}
                />
              </Form.Group>
            ))}
            <Form.Group controlId="class-summary">
              <Form.Label>Class Summary</Form.Label>
              <Form.Control as="textarea" rows={3} defaultValue={selectedClass ? selectedClass.summary : ''} onChange={(e) => handleSummaryChange(e.target.value)} />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowEditModal(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={() => selectedClass && handleSaveClass(selectedClass)}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default TeacherClassManager;
