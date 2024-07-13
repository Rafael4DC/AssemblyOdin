import {styled} from "@mui/material/styles";
import {Box} from "@mui/material";

/**
 * Custom scrollbar component
 */
export const CustomScrollbar = styled(Box)(({theme}) => ({
    maxHeight: 400,
    overflowY: 'auto',
    '&::-webkit-scrollbar': {
        width: '10px',
    },
    '&::-webkit-scrollbar-track': {
        background: '#555',
    },
    '&::-webkit-scrollbar-thumb': {
        backgroundColor: theme.palette.azulEscuro.main,
        borderRadius: '10px',
    },
    '&::-webkit-scrollbar-thumb:hover': {
        background: '#888',
    },
}));
