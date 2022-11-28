import { Snackbar, AlertProps, SnackbarCloseReason } from "@mui/material";
import React, { forwardRef } from "react";
import MuiAlert from '@mui/material/Alert';
import { AnyProps } from "../../constants/types";


export interface SavingSnackbarProps {
    open: boolean;
    close: () => void;
    message: string;
}


const Alert = forwardRef<typeof MuiAlert, AlertProps>((props, ref) => {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


const SavingSnackbar = ({ open, close, message }: SavingSnackbarProps) => {
    
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
            <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                {message}
            </Alert>
        </Snackbar>
    )
}

export default SavingSnackbar;