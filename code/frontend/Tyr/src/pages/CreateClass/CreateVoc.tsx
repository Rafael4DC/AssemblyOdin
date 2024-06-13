import * as React from "react";
import {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import useVocs from "../../hooks/useVocs";
import {Voc} from "../../model/Voc";
import useCategories from "../../hooks/useCategories";
import {useNavigate} from "react-router-dom";
import {WebUris} from "../../utils/WebUris";
import PROFILE = WebUris.PROFILE;
import useUserInfo from "../../hooks/useUserInfo";

/**
 * Page to create a voc class
 */
const CreateVocClass = () => {
    /*const navigate = useNavigate();
    const [isSubmitting, setIsSubmitting] = useState(false);
    const {userInfo} = useUserInfo();
    const role = userInfo?.role;

    const [vocData, setVocData] = useState<Voc>({
        description: "",
        started: "",
        ended: "",
        approved: false,
        section: {id: 1}
    });

    const {handleSaveVocClass, error} = useVocs();
    const {categories} = useCategories();
    const {students} = useStudents();

    const handleSubmit = async (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        setIsSubmitting(true);
        await handleSaveVocClass(vocData);
        if (error == null) navigate(PROFILE)
        else setIsSubmitting(false);
    };

    const handleCurriUnitChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            section: {id: Number(e.target.value)},
        }));
    };

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: "started" | "ended") => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            [key]: e.target.value,
        }));
    };

    const handleStudentChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            user: {id: Number(e.target.value)},
        }));
    };

    return (
        <Container>
            <h1>Create VOC Class</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        type="text"
                        required
                        value={vocData.description}
                        onChange={(e) => setVocData({...vocData, description: e.target.value})}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Start</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        required
                        value={vocData.started}
                        onChange={(e) => handleDateChange(e, 'started')}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>End</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        required
                        value={vocData.ended}
                        onChange={(e) => handleDateChange(e, 'ended')}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Curricular Unit</Form.Label>
                    <Form.Select
                        required
                        value={vocData.section.id.toString()}
                        onChange={handleCurriUnitChange}
                    >
                        <option>Choose The Curricular Unit</option>
                        {categories && categories.map(unit => (
                            <option key={unit.id} value={unit.id}>{unit.name}</option>
                        ))}
                    </Form.Select>
                </Form.Group>

                {role.name === 'TEACHER' && (
                    <>
                        <Form.Group className="mb-3">
                            <Form.Label>Student</Form.Label>
                            <Form.Select
                                required
                                value={vocData.user?.id?.toString() || ''}
                                onChange={handleStudentChange}
                            >
                                <option>Choose a Student</option>
                                {students && students.map(student => (
                                    <option key={student.id} value={student.id}>{student.username}</option>
                                ))}
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Check
                                type="checkbox"
                                label="Approved"
                                checked={vocData.approved}
                                onChange={(e) => setVocData({...vocData, approved: e.target.checked})}
                            />
                        </Form.Group>
                    </>
                )}

                <Button variant="primary" type="submit" disabled={isSubmitting}>
                    {isSubmitting ? 'Submitting...' : 'Create VOC Class'}
                </Button>
            </Form>
        </Container>
    );*/
};

export default CreateVocClass;
