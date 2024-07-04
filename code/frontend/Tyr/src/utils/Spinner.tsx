import * as React from "react";
import {CircularProgress} from "@mui/material";
import Box from "@mui/material/Box";

/**
 * Spinner component
 */
export function Spinner() {
    return (
        <Box sx={{
            paddingTop: 2,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
        }}>
            <CircularProgress sx={{color: '#fff'}}/>
        </Box>
    );
}
