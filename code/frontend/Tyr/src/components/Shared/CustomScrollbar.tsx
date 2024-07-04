import {styled} from "@mui/material/styles";
import {Box} from "@mui/material";

export const CustomScrollbar = styled(Box)(({theme}) => ({
    maxHeight: 400,  // Increase the max height for the logs container
    overflowY: 'auto',  // Enable vertical scrolling
    '&::-webkit-scrollbar': {
        width: '10px',  // Width of the scrollbar
    },
    '&::-webkit-scrollbar-track': {
        background: '#555',  // Scrollbar track color (slightly lighter than the background)
    },
    '&::-webkit-scrollbar-thumb': {
        backgroundColor: theme.palette.azulEscuro.main,  // Scrollbar thumb color
        borderRadius: '10px',
    },
    '&::-webkit-scrollbar-thumb:hover': {
        background: '#888',  // Scrollbar thumb hover color
    },
}));
