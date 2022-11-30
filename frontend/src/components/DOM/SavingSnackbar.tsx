import { Snackbar, AlertProps, SnackbarCloseReason } from "@mui/material";
import React, { forwardRef } from "react";
import MuiAlert from '@mui/material/Alert';


export interface SavingSnackbarProps {
    open: boolean;
    close: () => void;
    message: string;
    isSuccess: boolean;
}


const Alert = forwardRef<HTMLDivElement, AlertProps>((props, ref) => {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


const SavingSnackbar = ({ open, close, message, isSuccess }: SavingSnackbarProps) => {
    
    const handleClose = (event: Event | React.SyntheticEvent<Element, Event>, reason?: SnackbarCloseReason) => {
        if (reason !== "clickaway")
            close();
    }
    
    return (
        <Snackbar
            open={open}
            autoHideDuration={5000}
            onClose={handleClose}
        >
            <Alert
                onClose={handleClose}
                severity={isSuccess ? 'success' : 'error'}
                sx={{ width: '100%' }}
            >
                {message}
            </Alert>
        </Snackbar>
    )
}

export default SavingSnackbar;