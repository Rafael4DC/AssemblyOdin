import * as React from "react";
import {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {TechService} from "../../services/TechService";
import {WebUris} from "../../utils/WebUris";
import MANAGE_CLASSES = WebUris.MANAGE_CLASSES;
import useSections from "../../hooks/useSections";

/**
 * Page to create a tech class
 */
const CreateTechClass = () => {
    const navigate = useNavigate();
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [techData, setTechData] = useState({
        section: {id: 1},
        date: "",
        summary: ""
    });

    const { sections } = useSections();

    if (!sections) return <p>Loading sections...</p>;

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setIsSubmitting(true);
        try {
            await TechService.save(
                {
                    section: techData.section.id,
                    date: techData.date,
                    summary: techData.summary,
                    missTech: []
                }
            );
            navigate(MANAGE_CLASSES);
        } catch (error) {
            console.error('Error creating tech class:', error);
            setIsSubmitting(false);
        }
    };

    const handleSectionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setTechData((prevTechData) => ({
            ...prevTechData,
            section: {id: Number(e.target.value)},
        }));
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target;
        setTechData((prevTechData) => ({
            ...prevTechData,
            [name]: value,
        }));
    };

    return (
        <Container>
            <h1>Create Tech Class</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Date</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        name="date"
                        required
                        value={techData.date}
                        onChange={handleChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Summary</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        name="summary"
                        required
                        value={techData.summary}
                        onChange={handleChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Module</Form.Label>
                    <Form.Select
                        name="module"
                        required
                        value={techData.section.id}
                        onChange={handleSectionChange}
                    >
                        <option value="">Choose The Module</option>
                        {sections.map(section => (
                            <option key={section.id} value={section.id}>{section.name}</option>
                        ))}
                    </Form.Select>
                </Form.Group>

                <Button variant="primary" type="submit" disabled={isSubmitting}>
                    {isSubmitting ? 'Submitting...' : 'Create Tech Class'}
                </Button>
            </Form>
        </Container>
    );
};

export default CreateTechClass;
