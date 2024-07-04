import {Section} from "../../../services/section/models/Section";
import {Module} from "../../../services/module/models/Module";

const SectionMngDialog = (props: SectionMngDialogProps) => {


    /*return (
        <Dialog open={!!selectedSection} onClose={handleClose} fullWidth maxWidth="sm">
            <DialogTitle>Edit Section</DialogTitle>
            <DialogContent>
                <form onSubmit={handleSubmit}>
                    <TextField
                        label="Name"
                        type="text"
                        name="name"
                        required
                        value={sectionData.name}
                        onChange={handleInputChange}
                        {...commonTextFieldProps}
                    />
                    <TextField
                        label="Module"
                        select
                        name="module"
                        required
                        value={sectionData.module.id.toString()}
                        onChange={handleModuleChange}
                        {...commonTextFieldProps}
                    >
                        <MenuItem value="">
                            <em>Choose The Module</em>
                        </MenuItem>
                        {modules.map(module => (
                            <MenuItem key={module.id} value={module.id}>
                                {module.name}
                            </MenuItem>
                        ))}
                    </TextField>
                    <Box sx={{display: 'flex', alignItems: 'center', marginBottom: 2}}>
                        <InputBase
                            placeholder="Search filteredStudents"
                            value={searchQuery}
                            onChange={handleSearchChange}
                            sx={{flex: 1, paddingLeft: 1}}
                        />
                        <IconButton sx={{p: '10px'}}>
                            <SearchIcon/>
                        </IconButton>
                    </Box>
                    <List>
                        {filteredStudents.map(student => (
                            <ListItem key={student.id} sx={{color: '#000'}}>
                                <Checkbox
                                    checked={sectionData.students.includes(student.id)}
                                    onChange={() => handleStudentSelection(student.id)}
                                    sx={{color: '#000'}}
                                />
                                <ListItemText primary={student.username} sx={{color: '#000'}}/>
                            </ListItem>
                        ))}
                    </List>
                </form>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} color="primary" sx={{color: '#000'}}>
                    Cancel
                </Button>
                <Button onClick={handleSubmit} color="primary" sx={{color: '#000'}}>
                    Save Changes
                </Button>
            </DialogActions>
        </Dialog>
    )*/
}

export interface SectionMngDialogProps {
    selectedSection: Section;
    modules: Module[];
    filteredStudents: any[];
    searchQuery: string;

}

export default SectionMngDialog;
