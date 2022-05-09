import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";


const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route exact path="/" element={<div>Test</div>} />
            </Routes>
        </BrowserRouter>
    )
}

export default App;