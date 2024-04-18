import * as React from 'react';
import { useEffect, useState } from 'react';
import { Button, Form, ListGroup, Modal } from 'react-bootstrap';

interface ClassEditModalProps {
  show: boolean;
  onHide: () => void;
  classInfo: TecClass | null;
  onSave: (updatedClass: TecClass) => void;
  onDelete: () => void;
}

const ClassEditModal: React.FC<ClassEditModalProps> = ({
                                                         show,
                                                         onHide,
                                                         classInfo,
                                                         onSave,
                                                         onDelete,
                                                       }) => {
  const [editedClass, setEditedClass] = useState<TecClass | null>(null);

  useEffect(() => {
    if (classInfo) {
      setEditedClass(classInfo);
    } else {
      setEditedClass(null);
    }
  }, [classInfo]);

  const handleTeacherChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (editedClass) {
      setEditedClass({ ...editedClass, teacher: e.target.value });
    }
  };

  const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (editedClass) {
      setEditedClass({ ...editedClass, date: e.target.value });
    }
  };

  const saveClass = () => {
    if (editedClass) {
      onSave(editedClass);
    }
    onHide();
  };

  if (!editedClass) {
    return null;
  }

  const handleAttendanceChange = (studentId: number, attended: boolean) => {
    setEditedClass((prevClass) => {
      if (prevClass) {
        return {
          ...prevClass,
          students: prevClass.students.map((s) =>
            s.id === studentId ? { ...s, attendance: attended } : s,
          ),
        };
      }
      return null;
    });
  };

  return (
    <Modal show={show} onHide={onHide} centered>
      <Modal.Header closeButton>
        <Modal.Title>Edit Class</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form.Group className="mb-3">
          <Form.Label>Teacher</Form.Label>
          <Form.Control
            type="text"
            value={editedClass.teacher}
            onChange={handleTeacherChange}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Date</Form.Label>
          <Form.Control
            type="text"
            value={editedClass.date}
            onChange={handleDateChange}
          />
        </Form.Group>
        <div style={{ maxHeight: '150px', overflowY: 'auto' }}>
          {editedClass.students.map((student) => (
            <Form.Group key={student.id} className="mb-3 d-flex align-items-center">
              <Form.Label className="mb-0 me-2">{student.name}</Form.Label>
              <Form.Check
                type="checkbox"
                id={`attendance-${student.id}`}
                inline
                checked={student.attendance === true}
                onChange={(e) => handleAttendanceChange(student.id, e.target.checked)}
              />
            </Form.Group>
          ))}
        </div>
        <ListGroup variant="flush">
          <ListGroup.Item>
            <Button variant="danger" onClick={onDelete}>Delete Class</Button>
          </ListGroup.Item>
        </ListGroup>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>Cancel</Button>
        <Button variant="primary" onClick={saveClass}>Save Changes</Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ClassEditModal;
