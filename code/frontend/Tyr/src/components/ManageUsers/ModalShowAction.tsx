import {Button, Form, Modal} from "react-bootstrap";
import {User} from "../../services/user/models/User";
import * as React from "react";
import {UserInputModel} from "../../services/user/models/UserInputModel";

/**
 * Props for the ModalShowAction component
 *
 * @param show - boolean to show the modal
 * @param onHide - function to hide the modal
 * @param selectedUser - the selected user to edit
 * @param setSelectedUser - function to set the selected user
 * @param handleSaveUser - function to handle the save user
 * @param handleDeleteUser - function to handle the delete user
 * @param isSubmitting - boolean to show the submitting state
 * @param setIsSubmitting - function to set the submitting state
 */
interface ModalShowActionProps {
    show: boolean;
    onHide: () => void;
    selectedUser: User | null;
    setSelectedUser: (user: User) => void;
    handleSaveUser: (user: UserInputModel) => Promise<void>;
    handleDeleteUser: (id: number) => Promise<void>;
    isSubmitting: boolean;
    setIsSubmitting: (submitting: boolean) => void;
}

/**
 * Modal to show the user action
 */
export function ModalShowAction(props: ModalShowActionProps) {
    const {
        show,
        onHide,
        selectedUser,
        setSelectedUser,
        handleSaveUser,
        handleDeleteUser,
        isSubmitting,
    } = props;

    const handleDelete = () => {
        if (selectedUser) {
            handleDeleteUser(selectedUser.id);
        }
    };

    return (
        <Modal show={show} onHide={() => onHide()}>
            <Modal.Header closeButton>
                <Modal.Title>Edit User</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {selectedUser && (
                    <Form>
                        <Form.Group className="mb-3" controlId="formUserName">
                            <Form.Label>Name</Form.Label>
                            <Form.Control
                                type="text"
                                value={selectedUser.username}
                                onChange={(e) => setSelectedUser({
                                    ...selectedUser,
                                    username: e.target.value
                                })}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formUserRole">
                            <Form.Label>Role</Form.Label>
                            {/*<Form.Select
                                value={selectedUser.role}
                                onChange={(e) => setSelectedUser({
                                    ...selectedUser,
                                    role: e.target.value as RoleOptions
                                })}
                            >
                                {Object.values(RoleOptions).map(role => (
                                    <option key={role} value={role}>{role}</option>
                                ))}
                            </Form.Select>*/}
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formUserEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                value={selectedUser.email}
                                onChange={(e) => setSelectedUser({...selectedUser, email: e.target.value})}
                            />
                        </Form.Group>

                        {selectedUser.credits !== undefined && (
                            <Form.Group className="mb-3" controlId="formUserPoints">
                                <Form.Label>Points</Form.Label>
                                <Form.Control
                                    type="number"
                                    value={selectedUser.credits}
                                    onChange={(e) => setSelectedUser({
                                        ...selectedUser,
                                        credits: parseInt(e.target.value) || 0
                                    })}
                                />
                            </Form.Group>
                        )}
                    </Form>
                )}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={() => onHide()}>
                    Close
                </Button>
                <Button
                    variant="danger"
                    onClick={handleDelete}
                    disabled={isSubmitting}
                >
                    Delete
                </Button>
                <Button
                    variant="primary"
                    onClick={() => handleSaveUser({
                        username: selectedUser?.username,
                        email: selectedUser?.email,
                        role: selectedUser?.role.id,
                        credits: selectedUser?.credits
                    })}
                    disabled={isSubmitting}
                >
                    {isSubmitting ? 'Saving...' : 'Save Changes'}
                </Button>
            </Modal.Footer>
        </Modal>
    );
}
