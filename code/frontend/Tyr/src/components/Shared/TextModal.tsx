import * as React from 'react';
import {Button, Modal} from 'react-bootstrap';

/**
 * Props for the TextModal component
 *
 * @param title - the title of the modal
 * @param content - the content of the modal
 * @param show - boolean to show the modal
 * @param handleClose - function to close the modal
 */
interface TextModalProps {
    title: any;
    content: any;
    show: any;
    handleClose: any;
}

/**
 * Modal to show the tech sumaary
 */
const TextModal: React.FC<TextModalProps> = ({title, content, show, handleClose}) => {
    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>{content}</Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default TextModal;
