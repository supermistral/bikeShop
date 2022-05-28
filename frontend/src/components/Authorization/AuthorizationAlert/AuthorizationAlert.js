import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button } from "@mui/material";
import React, { useState } from "react";
import RouteLink from "../../DOM/RouteLink";


const AuthorizationAlert = ({ isOpen, close }) => {
    return (
        <Dialog
            open={isOpen}
            onClose={close}
            aria-labelledby="authorization-alert-title"
            aria-describedby="authorization-alert-description"
        >
            <DialogTitle id="alert-dialog-title">
                Для продолжения действия необходима авторизация
            </DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={close}>Ок</Button>
                <RouteLink to="/login">
                    <Button variant="outlined" autoFocus>
                        Войти
                    </Button>
                </RouteLink>
            </DialogActions>
        </Dialog>
    )
}

export default AuthorizationAlert;