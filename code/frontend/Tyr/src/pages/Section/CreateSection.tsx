import * as React from "react";
import { useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import useModules from "../../hooks/useModules";
import useStudents from "../../hooks/useStudents";
import { useNavigate } from "react-router-dom";
import { WebUris } from "../../utils/WebUris";
import PROFILE = WebUris.PROFILE;
import {SectionService} from "../../services/section/SectionService";

/**
 * Page to create a section
 */
const CreateSection = () => {
    const navigate = useNavigate();
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [sectionData, setSectionData] = useState({
        name: "",
        summary: "",
        module: { id: 1 },
        students: []
    });

    const { modules } = useModules();
    const { students } = useStudents();

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setIsSubmitting(true);
        try {
            await SectionService.save({
                name: sectionData.name,
                summary: sectionData.summary,
                module: sectionData.module.id,
                students: sectionData.students
            });
            navigate(PROFILE);
        } catch (error) {
            console.error('Error creating section:', error);
            setIsSubmitting(false);
        }
    };


    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setSectionData((prevSectionData) => ({
            ...prevSectionData,
            [name]: value,
        }));
    };

    const handleModuleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSectionData((prevSectionData) => ({
            ...prevSectionData,
            module: { id: Number(e.target.value) },
        }));
    };

    const handleStudentChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const studentId = Number(e.target.value);
        setSectionData((prevSectionData) => {
            const updatedStudents = e.target.checked
                ? [...prevSectionData.students, studentId]
                : prevSectionData.students.filter(id => id !== studentId);
            return {
                ...prevSectionData,
                students: updatedStudents
            };
        });
    };

    return (
        <Container>
            <h1>Create Section</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                        type="text"
                        name="name"
                        required
                        value={sectionData.name}
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Summary</Form.Label>
                    <Form.Control
                        type="text"
                        name="summary"
                        required
                        value={sectionData.summary}
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Module</Form.Label>
                    <Form.Select
                        required
                        value={sectionData.module.id.toString()}
                        onChange={handleModuleChange}
                    >
                        <option>Choose The Module</option>
                        {modules && modules.map(module => (
                            <option key={module.id} value={module.id}>{module.name}</option>
                        ))}
                    </Form.Select>
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Students</Form.Label>
                    {students && students.map(student => (
                        <Form.Check
                            key={student.id}
                            type="checkbox"
                            label={student.username}
                            value={student.id}
                            onChange={handleStudentChange}
                        />
                    ))}
                </Form.Group>

                <Button variant="primary" type="submit" disabled={isSubmitting}>
                    {isSubmitting ? 'Submitting...' : 'Create Section'}
                </Button>
            </Form>
        </Container>
    );
};

export default CreateSection;
