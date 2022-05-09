import { Container } from "@mui/material";
import { Box } from "@mui/system";
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";
import Home from "./components/Home/Home";


const App = () => {
    return (
        <Box 
            sx={{ 
                display: 'flex', 
                flexDirection: 'column', 
                minHeight: "100vh",
            }}
        >
            <Container maxWidth="lg" sx={{ my: 3, flex: 1 }}>
                <Header />
                <BrowserRouter>
                    <Routes>
                        <Route exact path="/" element={<Home />} />
                    </Routes>
                </BrowserRouter>
            </Container>
            <Box sx={{ backgroundColor: "#eee" }}>
                <Container maxWidth="lg" sx={{ my: 0 }}>
                    <Footer />
                </Container>
            </Box>
        </Box>
    )
}

export default App;