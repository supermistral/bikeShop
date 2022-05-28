import { Box, Stack, Typography } from "@mui/material";
import React from "react";
import SearchBar from "./SearchBar";
import Options from "./Options";
import Menu from "./Menu/Menu";
import RouteLink from "../DOM/RouteLink";


const Header = ({ categories }) => {
    return (
        <Box>
            <Stack 
                direction="row"
                spacing={2}
                sx={{ justifyContent: 'center', py: 2 }}
            >
                <RouteLink to="/" color="inherit">
                    <Typography sx={{ fontSize: "30px" }}>
                        Bike Shop
                    </Typography>
                </RouteLink>
                <SearchBar />
                <Options />
            </Stack>
            <Menu categories={categories} />
        </Box>
        
    )
}

export default Header