import { Container } from "@mui/material";
import { Box } from "@mui/system";
import React, { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Cart from "./components/Cart/Cart";
import Catalog from "./components/Catalog/Catalog";
import UserAuthContext from "./components/DOM/UserAuthContext";
import Favorites from "./components/Favorites/Favorites";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";
import Home from "./components/Home/Home";
import Login from "./components/Authorization/Login/Login";
import Registration from "./components/Authorization/Registration/Registration";
import axiosInstance from "./constants/axios";
import Logout from "./components/Authorization/Logout/Logout";
import OrderList from "./components/Profile/OrderList/OrderList";


const getUserAuthData = () => {
    const accessToken = window.localStorage.getItem("accessToken");
        
    if (accessToken === null)
        return { isAuthorized: false };

    const tokenParts = JSON.parse(atob(accessToken.split('.')[1]));
    const roles = tokenParts.roles?.map(role => {
        switch (role) {
            case "ROLE_MANAGER":
                return "manager";
            case "ROLE_ADMIN":
                return "admin";
            default:
                return "user";
        }
    });

    const isAuthorized = Array.isArray(roles) && roles.length > 0;

    return {
        roles: roles,
        isAuthorized: isAuthorized,
    }
}


const App = () => {
    const [categories, setCategories] = useState();
    const [userAuthData, setUserAuthData] = useState(getUserAuthData()); 

    useEffect(() => {
        fetch("/api/category")
            .then(res => res.json())
            .then(data => setCategories(data))
            .catch(e => console.log(e));
    }, []);

    useEffect(() => {
        setUserAuthData(getUserAuthData());

    }, [axiosInstance.defaults.headers['Authorization']])

    return (
        <UserAuthContext.Provider value={userAuthData}>
            <Box 
                sx={{ 
                    display: 'flex', 
                    flexDirection: 'column', 
                    minHeight: '100vh',
                }}
            >
                <BrowserRouter>
                    <Container maxWidth="lg" sx={{ mb: 3, flex: 1 }}>
                        <Header categories={categories} />
                        <Routes>
                            <Route exact path="/" element={<Home />} />
                            <Route path="/catalog/*" element={<Catalog categories={categories} />} />
                            <Route path="/favorites" element={<Favorites />} />
                            <Route path="/cart" element={<Cart />} />
                            <Route path="/login" element={<Login />} />
                            <Route path="/registration" element={<Registration />} />
                            <Route path="/logout" element={<Logout />} />
                            {/* Search path */}
                        </Routes>
                    </Container>
                    <Box sx={{ backgroundColor: "#eee" }}>
                        <Container maxWidth="lg" sx={{ my: 0 }}>
                            <Footer />
                        </Container>
                    </Box>
                </BrowserRouter>
            </Box>
        </UserAuthContext.Provider>
    )
}

export default App;