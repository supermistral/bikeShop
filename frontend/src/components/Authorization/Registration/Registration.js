import { Box, Typography, TextField, Button } from "@mui/material";
import React, { useContext, useState } from "react";
import BorderColorIcon from '@mui/icons-material/BorderColor';
import CheckCircleOutlinedIcon from '@mui/icons-material/CheckCircleOutlined';
import RouteLink from "../../DOM/RouteLink";
import axiosInstance from "../../../constants/axios";
import UserAuthContext from "../../DOM/UserAuthContext";
import { Navigate } from "react-router-dom";


const Registration = () => {
    const [formData, setFormData] = useState();
    const [successful, setSuccessful] = useState(true);
    const [formErrorData, setFormErrorData] = useState();

    const { isAuthorized } = useContext(UserAuthContext);

    if (isAuthorized) {
        return <Navigate to="/" />
    }

    const handleEmailChange = e => setFormData({ ...formData, email: e.target.value });
    const handlePasswordChange = e => setFormData({ ...formData, password: e.target.value});
    const handleNameChange = e => setFormData({ ...formData, name: e.target.value });

    const handleSubmit = e => {
        e.preventDefault();

        axiosInstance
            .post('/auth/signup', formData)
            .then(res => setSuccessful(true));
    }

    return (
        <Box sx={{ 
            display: 'flex', 
            justifyContent: 'center', 
            width: '100%',
            p: 2,
            mt: 2,
        }}>
            {successful ? (
                <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <CheckCircleOutlinedIcon color="success" sx={{ 
                            fontSize: '3em', 
                            p: 1,
                            borderRadius: '50%',
                        }}/>
                        <Typography component="span" variant="h5">
                            Учетная запись успешно создана
                        </Typography>
                    <RouteLink to="/login" sx={{ mt: 2 }}>
                        <Button variant="outlined">
                            Войти
                        </Button>
                    </RouteLink>
                </Box>
            ) : (
                <Box sx={{ 
                    display: 'flex', 
                    flexDirection: 'column', 
                    alignItems: 'center', 
                    justifyContent: 'center',
                    maxWidth: 450,
                }}>
                    <Box sx={{ display: 'flex', maxWidth: 400 }}>
                        <Typography variant="h4">Регистрация</Typography>
                        <BorderColorIcon sx={{ 
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
                            id="name"
                            label="Имя"
                            name="name"
                            onChange={handleNameChange}
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
                            <RouteLink to="/login" variant="body2">
                                Есть аккаунт? Войти
                            </RouteLink>
                        </Box>
                    </form>
                </Box> 
            )}
        </Box>
          
    )
}

export default Registration;