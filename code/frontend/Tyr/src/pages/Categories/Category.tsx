import {Accordion, Card, Container} from 'react-bootstrap';
import {Category} from "../../model/Category";
import * as React from 'react';
import useCategories from "../../hooks/useCategories";

/**
 * Page to display the categories
 */
const CategoriesDisplay: React.FC = () => {
    const {categories} = useCategories();

    debugger;
    return (
        <Container>
            <h1>Categories</h1>
            <Accordion defaultActiveKey="0">
                {categories ? categories.map((category: Category, index: number) => (
                    <Accordion.Item eventKey={index.toString()} key={category.id}>
                        <Accordion.Header>{category.name}</Accordion.Header>
                        <Accordion.Body>
                            <Card.Text>{category.description}</Card.Text>
                            <Accordion>
                                {category.subCategories.map((subcategory, subIndex) => (
                                    <Accordion.Item eventKey={subIndex.toString()} key={subcategory.id}>
                                        <Accordion.Header>{subcategory.name}</Accordion.Header>
                                        <Accordion.Body>
                                            <Card.Text>{subcategory.description}</Card.Text>
                                            <ul>
                                                {subcategory.modules.map((module) => (
                                                    <li key={module.id}>
                                                        <strong>{module.name}:</strong> {module.description}
                                                    </li>
                                                ))}
                                            </ul>
                                        </Accordion.Body>
                                    </Accordion.Item>
                                ))}
                            </Accordion>
                        </Accordion.Body>
                    </Accordion.Item>
                )) : <p>Loading categories...</p>}
            </Accordion>
        </Container>
    );
};

export default CategoriesDisplay;
