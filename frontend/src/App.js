import { Container } from "@mui/material";
import { Box } from "@mui/system";
import React, { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Catalog from "./components/Catalog/Catalog";
import Favorites from "./components/Favorites/Favorites";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";
import Home from "./components/Home/Home";


const App = () => {
    const [categories, setCategories] = useState();

    useEffect(() => {
        fetch("/api/category")
            .then(res => res.json())
            .then(data => setCategories(data))
            .catch(e => console.log(e));
    }, []);

    return (
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
    )
}

export default App;