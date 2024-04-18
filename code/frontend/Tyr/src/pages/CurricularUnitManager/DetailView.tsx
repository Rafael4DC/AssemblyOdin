// DetailView.js
import { Button, Table } from 'react-bootstrap';
import * as React from 'react';
import { useState } from 'react'; // Assuming you've created this component in a separate file
import ClassEdit from './ClassEdit';


interface DetailViewProps {
  course: Course;
  onSave: (updatedCourse: Course) => void;
}

const DetailView: React.FC<DetailViewProps> = ({ course, onSave }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editedCourse, setEditedCourse] = useState(course);
  const [selectedClass, setSelectedClass] = useState<TecClass | null>(null);
  const [showEditModal, setShowEditModal] = useState(false);

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEditedCourse({ ...editedCourse, title: e.target.value });
  };

  const handleDescriptionChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setEditedCourse({ ...editedCourse, description: e.target.value });
  };

  const saveCourse = () => {
    onSave(editedCourse);
    setIsEditing(false);
  };

  const handleEditClassClick = (cls: TecClass) => {
    setSelectedClass(cls);
    setShowEditModal(true);
  };

  return (
    <div style={{ border: '1px solid grey', padding: '20px', marginTop: '20px' }}>
      {/* Course Editing Form */}
      {/* ... */}
      <h5>Currently Scheduled Classes</h5>
      <Table striped bordered hover>
        {/* Table head */}
        <tbody>
        {course.classes.map((cls, index) => (
          <tr key={index}>
            <td>{cls.teacher}</td>
            <td>{cls.date}</td>
            <td>
              <Button variant="outline-primary" onClick={() => handleEditClassClick(cls)}>Edit</Button>
            </td>
          </tr>
        ))}
        </tbody>
      </Table>

      <ClassEdit
        show={showEditModal}
        onHide={() => setShowEditModal(false)}
        classInfo={selectedClass}
        onSave={(updatedClass: any) => {
          // Update the class within the course
          const updatedClasses = editedCourse.classes.map(c => c === selectedClass ? updatedClass : c);
          onSave({ ...editedCourse, classes: updatedClasses });
          setSelectedClass(null);
        }}
        onDelete={() => {
        }
        }
      />
    </div>
  );
};

export default DetailView;
