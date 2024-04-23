import * as React from 'react';
import { Button, Modal } from 'react-bootstrap';

class TextModal extends React.Component<{ title: any, content: any, show: any, handleClose: any }> {
  render() {
    let { title, content, show, handleClose } = this.props;
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
}

export default TextModal;
