import { Box, Stack, Typography } from "@mui/material";
import React from "react";
import SearchBar from "./SearchBar";
import Options from "./Options";
import Menu from "./Menu/Menu";


const Header = ({ categories }) => {
    return (
        <Box>
            <Stack 
                direction="row"
                spacing={2}
                sx={{ justifyContent: 'center', py: 2 }}
            >
                <Typography sx={{ fontSize: "30px" }}>
                    Bike Shop
                </Typography>
                <SearchBar />
                <Options />
            </Stack>
            <Menu categories={categories} />
        </Box>
        
    )
}

export default Header