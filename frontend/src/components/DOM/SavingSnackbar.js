import { Snackbar } from "@mui/material";
import React, { forwardRef } from "react";
import MuiAlert from '@mui/material/Alert';


const Alert = forwardRef((props, ref) => {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


const SavingSnackbar = ({ open, close, message }) => {
    
    const handleClose = (e, reason) => {
        if (reason !== "clickaway")
            close();
    }
    
    return (
        <Snackbar
            open={open}
            autoHideDuration={5000}
            onClose={handleClose}
        >
            <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                {message}
            </Alert>
        </Snackbar>
    )
}

export default SavingSnackbar;