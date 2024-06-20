import {Button, Form, Modal} from 'react-bootstrap';
import * as React from 'react';
import {Voc} from '../../services/voc/models/Voc';

/**
 * Props for the VocApprovalModal component
 *
 * @param show - boolean to show the modal
 * @param onHide - function to hide the modal
 * @param voc - the voc to approve
 * @param onApproveChange - function to handle the approved change
 * @param onSave - function to save the changes
 * @param loading - boolean to show the loading state
 */
interface VocApprovalModalProps {
    show: boolean;
    onHide: () => void;
    voc: Voc | null;
    onApproveChange: (approved: boolean) => void;
    onSave: () => void;
    loading: boolean;
}

/**
 * Modal to approve the voc class
 */
const VocApprovalModal: React.FC<VocApprovalModalProps> = (
    {
        show,
        onHide,
        voc,
        onApproveChange,
        onSave,
        loading,
    }) => {
    return (
        <Modal show={show} onHide={onHide}>
            <Modal.Header closeButton>
                <Modal.Title>Approve VOC Class</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="voc-approved">
                        <Form.Check
                            type="checkbox"
                            label="Approved"
                            checked={voc?.approved || false}
                            onChange={(e) => onApproveChange(e.target.checked)}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>
                    Close
                </Button>
                <Button variant="primary" onClick={onSave} disabled={loading}>
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default VocApprovalModal;
