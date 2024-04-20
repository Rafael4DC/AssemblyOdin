import {Button, Table, Modal, Form, InputGroup, FormControl} from 'react-bootstrap';
import {useState} from 'react';
import * as React from 'react';
import useUsers from "../../hooks/useUsers";
import {RoleOptions, User} from "../../model/User";

const UserManager = () => {
    const {users, handleSaveUser1, handleDeleteUser, error} = useUsers();
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedUser, setSelectedUser] = useState<User | null>(null);
    const [showEditModal, setShowEditModal] = useState(false);

    const handleEditUserClick = (user: User) => {
        setSelectedUser(user);
        setShowEditModal(true);
    };

    const handleSaveUser = async (updatedUser: User) => {
        await handleSaveUser1(updatedUser)
        setShowEditModal(false);
    };

    const filteredUsers = searchTerm
        ? users.filter(user => user.username.toLowerCase().includes(searchTerm.toLowerCase()))
        : users;

    const renderUserRow = (user: User) => (
        <tr key={user.id}>
            <td>{user.username}</td>
            <td>{user.role}</td>
            <td>{user.email}</td>
            <td>{user.credits}</td>
            <td>
                <Button variant="outline-primary" onClick={() => handleEditUserClick(user)}>Edit/Delete</Button>
            </td>
        </tr>
    );

    return (
        <div>
            <InputGroup className="mb-3">
                <FormControl
                    placeholder="Search Users"
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </InputGroup>
            <Table striped bordered hover responsive>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Role</th>
                    <th>Email</th>
                    <th>Points</th>
                    <th>Action</th>
                </tr>
                </thead>
                {filteredUsers ?
                    <tbody>
                    {filteredUsers.map(renderUserRow)}
                    </tbody>
                    : <div className="text-center my-5">
                        <div className="spinner-border" role="status"></div>
                    </div>
                }
            </Table>

            <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
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
                                    defaultValue={selectedUser.username}
                                    onChange={(e) => setSelectedUser({...selectedUser, username: e.target.value})}
                                />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formUserRole">
                                <Form.Label>Role</Form.Label>
                                <Form.Select
                                    defaultValue={selectedUser.role}
                                    onChange={(e) => setSelectedUser({
                                        ...selectedUser,
                                        role: e.target.value as RoleOptions
                                    })}
                                >
                                    {Object.values(RoleOptions).map(role => (
                                        <option key={role} value={role}>{role}</option>
                                    ))}
                                </Form.Select>
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formUserEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type="email"
                                    defaultValue={selectedUser.email}
                                    onChange={(e) => setSelectedUser({...selectedUser, email: e.target.value})}
                                />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formUserPoints">
                                <Form.Label>Points</Form.Label>
                                <Form.Control
                                    type="number"
                                    defaultValue={selectedUser.credits}
                                    onChange={(e) => setSelectedUser({
                                        ...selectedUser,
                                        credits: parseInt(e.target.value)
                                    })}
                                />
                            </Form.Group>

                            <Button variant="secondary" onClick={() => setShowEditModal(false)}>
                                Close
                            </Button>
                            <Button variant="primary" onClick={() => selectedUser && handleSaveUser(selectedUser)}>
                                Save Changes
                            </Button>
                        </Form>
                    )}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowEditModal(false)}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={() => selectedUser && handleSaveUser(selectedUser)}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default UserManager;