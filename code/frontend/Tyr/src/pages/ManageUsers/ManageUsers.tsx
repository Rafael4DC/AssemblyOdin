import { Button, Table, Modal, Form, InputGroup, FormControl } from 'react-bootstrap';
import  { useState } from 'react';
import * as React from 'react';
import { RoleOptions, User } from '../../services/User/models/GetUserInfoOutputModel';


const mockUsers : User[] = [
  {
    id: 1,
    name: 'Tomas Santos',
    role: RoleOptions.Admin,
    email: 'tomas.santos@assembly.pt',
    credits: 10,
  },
  {
    id: 2,
    name: 'Marta Rebelo',
    role: RoleOptions.Student,
    email: 'marta.rebelo@domain.com',
    credits: 20,
  },
  {
    id: 3,
    name: 'João Almeida',
    role: RoleOptions.Teacher,
    email: 'joao.almeida@domain.com',
    credits: 30,
  },
  {
    id: 4,
    name: 'Ana Silva',
    role: RoleOptions.Student,
    email: 'ana.silva@domain.com',
    credits: 40,
  },
  {
    id: 5,
    name: 'Luís Soares',
    role: RoleOptions.Admin,
    email: 'luis.soares@domain.com',
    credits: 50,
  },
  {
    id: 6,
    name: 'Sofia Pereira',
    role: RoleOptions.Student,
    email: 'sofia.pereira@domain.com',
    credits: 60,
  }
];

const UserManager = () => {
  const [users, setUsers] = useState(mockUsers)
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [showEditModal, setShowEditModal] = useState(false);

  const handleEditUserClick = (user: User) => {
    setSelectedUser(user);
    setShowEditModal(true);
  };

  const handleSaveUser = (updatedUser: User) => {
    const updatedUsers = users.map(user => user.id === updatedUser.id ? updatedUser : user);
    setUsers(updatedUsers);
    setShowEditModal(false);
  };

  const filteredUsers = searchTerm
    ? users.filter(user => user.name.toLowerCase().includes(searchTerm.toLowerCase()))
    : users;

  const renderUserRow = (user: User) => (
    <tr key={user.id}>
      <td>{user.name}</td>
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
        <tbody>
        {filteredUsers.map(renderUserRow)}
        </tbody>
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
                  defaultValue={selectedUser.name}
                  onChange={(e) => setSelectedUser({ ...selectedUser, name: e.target.value })}
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="formUserRole">
                <Form.Label>Role</Form.Label>
                <Form.Select
                  defaultValue={selectedUser.role}
                  onChange={(e) => setSelectedUser({ ...selectedUser, role: e.target.value as RoleOptions })}
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
                  onChange={(e) => setSelectedUser({ ...selectedUser, email: e.target.value })}
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="formUserPoints">
                <Form.Label>Points</Form.Label>
                <Form.Control
                  type="number"
                  defaultValue={selectedUser.credits}
                  onChange={(e) => setSelectedUser({ ...selectedUser, credits: parseInt(e.target.value) })}
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