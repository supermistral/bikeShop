import { Stack, Typography } from "@mui/material";
import React from "react";
import SearchBar from "./SearchBar";
import Options from "./Options";


const Header = () => {
    return (
        <Stack 
            direction="row"
            spacing={2}
            sx={{ justifyContent: 'center' }}
        >
            <Typography sx={{ fontSize: "30px" }}>
                Bike Shop
            </Typography>
            <SearchBar />
            <Options />
        </Stack>
    )
}

export default Header