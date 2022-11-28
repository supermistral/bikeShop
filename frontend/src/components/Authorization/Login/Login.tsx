import { Box, Button, TextField, Typography } from "@mui/material";
import React, { useContext, useState } from "react";
import RouteLink from "../../DOM/RouteLink";
import LoginIcon from '@mui/icons-material/Login';
import axiosInstance, { addAuthorizationData } from "../../../constants/axios";
import UserAuthContext from "../../DOM/UserAuthContext";
import { Navigate } from "react-router-dom";


type FormData = {
    email?: string;
    password?: string;
}

type ChangeEvent = React.ChangeEvent<HTMLInputElement>
type SubmitEvent = React.FormEvent<HTMLFormElement>


const Login = () => {
    const [formData, setFormData] = useState<FormData>();

    const { isAuthorized } = useContext(UserAuthContext);

    if (isAuthorized) {
        return <Navigate to="/" />
    }

    const handleEmailChange = (e: ChangeEvent) => setFormData({ ...formData, email: e.target.value });
    const handlePasswordChange = (e: ChangeEvent) => setFormData({ ...formData, password: e.target.value});

    const handleSubmit = (e: SubmitEvent) => {
        e.preventDefault();

        axiosInstance
            .post('/auth/signin', formData)
            .then(res => {
                addAuthorizationData(res.data);
            });
    }

    return (
        <Box sx={{ display: 'flex', justifyContent: 'center', width: '100%' }}>
            <Box sx={{ 
                display: 'flex', 
                flexDirection: 'column', 
                alignItems: 'center', 
                justifyContent: 'center',
                p: 2,
                mt: 2,
                maxWidth: 450
            }}>
                <Box sx={{ display: 'flex' }}>
                    <Typography variant="h4">Вход</Typography>
                    <LoginIcon sx={{ 
                        fontSize: '3em', 
                        ml: 1.5,
                        p: 1,
                        boxShadow: '0 0 0 5px #eef',
                        borderRadius: '50%',
                    }} />
                </Box>
                <form onSubmit={handleSubmit}>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        fullWidth
                        required
                        id="email"
                        label="Email"
                        name="email"
                        autoFocus
                        onChange={handleEmailChange}
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        fullWidth
                        required
                        id="password"
                        label="Пароль"
                        name="password"
                        type="password"
                        onChange={handlePasswordChange}
                    />
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{ mt: 3, mb: 2}}
                    >
                        Войти
                    </Button>
                    <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                        <RouteLink to="/registration" variant="body2">
                            Еще нет аккаунта? Регистрация
                        </RouteLink>
                    </Box>
                </form>
            </Box>
        </Box>
    )
}

export default Login;