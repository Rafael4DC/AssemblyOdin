import * as React from 'react';
import Box from "@mui/material/Box";
import videoo from "../../assets/Assembly-Logo_azulEscuro.mp4";

/**
 * Page to display the home page
 */
function Home() {
    return (
        <Box
            sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '92vh',
                width: '92vw',
                margin: 0,
                overflow: 'hidden'
            }}
        >
            <video
                style={{
                    width: '100%',
                    height: '100%',
                    objectFit: 'cover'
                }}
                src={videoo}
                autoPlay
                /*loop*/
                muted
            />
        </Box>
    );
}
export default Home;