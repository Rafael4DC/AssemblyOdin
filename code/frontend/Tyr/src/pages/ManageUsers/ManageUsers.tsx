import {Button, Col, Container, FormControl, InputGroup, Row, Table} from 'react-bootstrap';
import * as React from 'react';
import useUsers from "../../hooks/useUsers";
import {User} from "../../services/user/models/User";
import {Spinner} from "../../utils/Spinner";
import {ModalShowAction} from "../../components/ManageUsers/ModalShowAction";
import {AlertError} from "../../utils/AlertError";

/**
 * Page to manage users
 */
const UserManager: React.FC = () => {
    const {
        filteredUsers,
        selectedUser,
        error,
        showEditModal,
        setSearchTerm,
        setShowEditModal,
        handleSaveUser,
        handleDeleteUser,
        handleEditUserClick,
        setSelectedUser,
        isSubmitting,
        setIsSubmitting,
    } = useUsers();

    if (error) return <AlertError error={error}/>;

    const students = filteredUsers?.filter(user => user.credits != null) || [];
    const teachersAndAdmins = filteredUsers?.filter(user => user.credits == null) || [];

    const renderUserRow = (user: User) => (
        <tr key={user.id}>
            <td>{user.username}</td>
            {user.credits == null && <td>{user.role.name}</td>}
            <td>{user.email}</td>
            {user.credits != null && <td>{user.credits}</td>}
            <td>
                <Button variant="outline-primary" onClick={() => handleEditUserClick(user)}>Edit/Delete</Button>
            </td>
        </tr>
    );

    return (
        <Container fluid className="p-5 text-center">
            <Row className="justify-content-center my-3">
                <InputGroup className="mb-3">
                    <FormControl
                        placeholder="Search Users"
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </InputGroup>
            </Row>

            <Row>
                <Col className="mb-3">
                    <div className="text-center mb-4">
                        <h3>Students</h3>
                    </div>
                    <Table striped bordered hover responsive>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Points</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {students.length > 0 ? students.map(renderUserRow) : (
                            <tr>
                                <td colSpan={4} className="text-center my-5">
                                    <Spinner/>
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </Table>
                </Col>

                <Col className="mb-3">
                    <div className="text-center mb-4">
                        <h3>Teachers and Admins</h3>
                    </div>
                    <Table striped bordered hover responsive>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Role</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {teachersAndAdmins.length > 0 ? teachersAndAdmins.map(renderUserRow) : (
                            <tr>
                                <td colSpan={4} className="text-center my-5">
                                    <Spinner/>
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </Table>
                </Col>
            </Row>
            <ModalShowAction
                show={showEditModal}
                onHide={() => setShowEditModal(false)}
                selectedUser={selectedUser}
                setSelectedUser={setSelectedUser}
                handleSaveUser={handleSaveUser}
                handleDeleteUser={handleDeleteUser}
                isSubmitting={isSubmitting}
                setIsSubmitting={setIsSubmitting}
            />
        </Container>
    );
};

export default UserManager;
