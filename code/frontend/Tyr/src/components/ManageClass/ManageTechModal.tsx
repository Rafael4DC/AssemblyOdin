import {Button, Form, Modal} from "react-bootstrap";
import * as React from "react";
import {TechsAttendance} from "../../hooks/useClassManager";


/**
 * Props for the ManageTechModal component
 *
 * @param showEditModal - boolean to show the modal
 * @param setShowEditModal - function to set the showEditModal state
 * @param selectedClass - the selected class to edit
 * @param handleAttendanceChange - function to handle the attendance change
 * @param handleSummaryChange - function to handle the summary change
 * @param handleDateChange - function to handle the date change
 * @param handleSaveClass - function to handle the save class
 * @param loading - boolean to show the loading state
 */
interface ManageTechModalProps {
    showEditModal: boolean;
    setShowEditModal: (show: boolean) => void;
    selectedClass: TechsAttendance | null;
    handleAttendanceChange: (studentId: number, attendance: boolean) => void;
    handleSummaryChange: (text: string) => void;
    handleDateChange: (newDate: string) => void;
    handleSaveClass: () => void;
    loading: boolean;
}

/**
 * Modal to manage and edit the tech classes
 */
export const ManageTechModal: React.FC<ManageTechModalProps> = (
    {
        showEditModal,
        setShowEditModal,
        selectedClass,
        handleAttendanceChange,
        handleSummaryChange,
        handleDateChange,
        handleSaveClass,
        loading
    }) => {
    return (
        <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Edit Class</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    {selectedClass?.attendedStudents.map(student => (
                        <Form.Group controlId={`student-attendance-${student.id}`} key={student.id}>
                            <Form.Check
                                type="checkbox"
                                label={student.student.username}
                                checked={student.attended}
                                onChange={(e) => handleAttendanceChange(student.student.id, e.target.checked)}
                            />
                        </Form.Group>
                    ))}
                    <Form.Group controlId="class-summary">
                        <Form.Label>Class Summary</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={3}
                            value={selectedClass?.tech.summary || ''}
                            onChange={(e) => handleSummaryChange(e.target.value)}
                        />
                    </Form.Group>
                    <Form.Group controlId="class-date">
                        <Form.Label>Class Date</Form.Label>
                        <Form.Control
                            type="datetime-local"
                            value={selectedClass?.tech.date ? new Date(selectedClass.tech.date).toISOString().slice(0, 16) : ''}
                            onChange={(e) => handleDateChange(e.target.value)}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={() => setShowEditModal(false)}>
                    Close
                </Button>
                <Button variant="primary" onClick={handleSaveClass} disabled={loading}>
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    );
};