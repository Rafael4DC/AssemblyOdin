import * as React from "react";
import useSections from "../../hooks/useSections";
import {Card, CardContent, Container, Grid, ListItem, Modal, IconButton} from "@mui/material";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItemText from "@mui/material/ListItemText";
import {Spinner} from "../../utils/Spinner";
import CloseIcon from '@mui/icons-material/Close';
import {Section} from "../../services/section/models/Section";

/**
 * Page to view all sections and their users
 */
function SectionDisplay() {
    const {sections} = useSections();
    const [open, setOpen] = React.useState(false);
    const [selectedSection, setSelectedSection] = React.useState(null);

    const handleOpen = (section: Section) => {
        setSelectedSection(section);
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setSelectedSection(null);
    };

    if (sections == null) return <Spinner/>

    return (
        <Container>
            <Grid container spacing={4}>
                {sections.map(section => (
                    <Grid item xs={12} sm={6} md={4} key={section.id}>
                        <Card sx={{maxWidth: 600, margin: '20px auto'}}>
                            <CardContent>
                                <Typography sx={{color: '#000'}} variant="h5" component="div">
                                    {section.name}
                                </Typography>
                                <Typography sx={{color: '#000'}} variant="subtitle1" color="text.secondary">
                                    Module: {section.module?.name}
                                </Typography>
                                <Box sx={{maxHeight: 300, overflow: 'auto', marginTop: 2}}>
                                    <Typography sx={{color: '#000'}} variant="button" display="block"
                                                onClick={() => handleOpen(section)}>
                                        View All Students
                                    </Typography>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-title"
                aria-describedby="modal-description"
            >
                <Box sx={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: 400,
                    bgcolor: 'background.paper',
                    boxShadow: 24,
                    p: 4,
                    maxHeight: '80vh',
                    overflow: 'auto',
                }}>
                    <Box sx={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                        <Typography sx={{color: '#000'}} id="modal-title" variant="h6" component="h2">
                            Students in {selectedSection?.name}
                        </Typography>
                        <IconButton onClick={handleClose}>
                            <CloseIcon/>
                        </IconButton>
                    </Box>
                    <List>
                        {selectedSection?.students?.map((student: {
                            id: React.Key;
                            username: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal;
                        }) => (
                            <ListItem sx={{color: '#000'}} key={student.id}>
                                <ListItemText sx={{color: '#000'}} primary={student.username}/>
                            </ListItem>
                        )) || <Typography sx={{color: '#000'}} variant="body2" color="text.secondary">No students
                            enrolled.</Typography>}
                    </List>
                </Box>
            </Modal>
        </Container>
    );
};

export default SectionDisplay;
