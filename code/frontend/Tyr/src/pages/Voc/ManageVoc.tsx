import * as React from 'react';
import {
    Box,
    Container,
    FormControl,
    InputAdornment,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Typography
} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import useManageVoc from "../../hooks/Voc/useManageVoc";
import {Spinner} from "../../utils/Spinner";
import {AlertDialog} from "../../utils/AlertDialog";
import VocMngDialog from "../../components/Voc/Dialog/VocMngDialog";
import VocTableContainer from "../../components/Voc/Table/VocTableContainer";
import {useTheme} from "@mui/material/styles";

/**
 * Page to manage Voc
 */
const ManageVoc = () => {
    const theme = useTheme();
    const customColor = theme.palette.custom.main;
    const {
        state,
        selectedVoc,
        handleVocClick,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        handleSubmit,
        handleClose,
        filter,
        setFilter,
        searchQuery,
        setSearchQuery,
        handleDeleteVoc,
        handleApprovedChange
    } = useManageVoc();

    switch (state.type) {
        case 'loading':
            return <Spinner/>;

        case 'error':
            return <AlertDialog alert={state.message}/>;

        case 'success':
            const {filteredVocs, sections} = state;
            return (
                <Container>
                    <Typography variant="h4" component="h1" gutterBottom align={"center"} sx={{color: customColor}}>
                        Manage VOC Classes
                    </Typography>
                    <Box sx={{backgroundColor: 'white', padding: 3, borderRadius: 2}}>
                        <Box display="flex" justifyContent="space-between" marginBottom={2}>
                            <TextField
                                label="Search by stu"
                                value={searchQuery}
                                onChange={(e) => setSearchQuery(e.target.value)}
                                InputProps={{
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            <SearchIcon/>
                                        </InputAdornment>
                                    ),
                                }}
                                sx={{width: '48%'}}
                            />
                            <FormControl sx={{width: '48%'}}>
                                <InputLabel>Date Filter</InputLabel>
                                <Select
                                    value={filter}
                                    onChange={(e) => setFilter(e.target.value)}
                                >
                                    <MenuItem value="all">All</MenuItem>
                                    <MenuItem value="past">Past Classes</MenuItem>
                                    <MenuItem value="future">Future Classes</MenuItem>
                                    <MenuItem value="nextWeek">Next Week</MenuItem>
                                    <MenuItem value="pastWeek">Past Week</MenuItem>
                                </Select>
                            </FormControl>
                        </Box>
                        <VocTableContainer
                            filteredVocs={filteredVocs}
                            handleVocClick={handleVocClick}
                            handleDeleteVoc={handleDeleteVoc}
                            handleApprovedChange={handleApprovedChange}
                            loading={state.loading}
                        />
                        {selectedVoc && (
                            <VocMngDialog
                                selectedVoc={selectedVoc}
                                sections={sections}
                                handleInputChange={handleInputChange}
                                handleDateChange={handleDateChange}
                                handleTimeChange={handleTimeChange}
                                handleSectionChange={handleSectionChange}
                                handleClose={handleClose}
                                handleSubmit={handleSubmit}
                                loading={state.loading}
                            />
                        )}
                    </Box>
                </Container>
            );
    }
}

export default ManageVoc;
