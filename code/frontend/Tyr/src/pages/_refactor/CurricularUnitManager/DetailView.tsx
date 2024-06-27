// DetailView.js
import * as React from 'react';
import {useState} from 'react';
import {Tech} from '../../../services/tech/models/Tech';
import {Module} from "../../../services/module/models/Module";


interface DetailViewProps {
    course: Module;
    onSave: (updatedCourse: Module) => void;
}

const DetailView: React.FC<DetailViewProps> = ({course, onSave}) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editedCourse, setEditedCourse] = useState(course);
    const [selectedClass, setSelectedClass] = useState<Tech | null>(null);
    const [showEditModal, setShowEditModal] = useState(false);

    const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEditedCourse({...editedCourse, name: e.target.value});
    };

    const handleDescriptionChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setEditedCourse({...editedCourse, description: e.target.value});
    };

    const saveCourse = () => {
        onSave(editedCourse);
        setIsEditing(false);
    };

    const handleEditClassClick = (cls: Tech) => {
        setSelectedClass(cls);
        setShowEditModal(true);
    };

    return (
        <div style={{border: '1px solid grey', padding: '20px', marginTop: '20px'}}>
            {/* Course Editing Form
       ...
      <h5>Currently Scheduled Classes</h5>
      <Table striped bordered hover>
         Table head
        <tbody>
        {course.classes.map((cls, index) => (
          <tr key={index}>
            <td>{cls.teacher.username}</td>
            <td>{cls.date.toLocaleString()}</td>
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
      />*/}
        </div>
    );
};

export default DetailView;
