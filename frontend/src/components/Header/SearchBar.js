import { Box, Divider, IconButton, InputBase, Paper } from "@mui/material";
import React from "react";
import MenuIcon from "@mui/icons-material/Menu";
import SearchIcon from "@mui/icons-material/Search";


const SearchBar = () => {
    return (
        <Paper 
            component="form"
            sx={{
                display: 'flex',
                flex: 1,
            }}
        >
            <Divider orientation="vertical" />
            <InputBase 
                placeholder="Поиск"
                sx={{ ml: 1, width: '100%' }}
                inputProps={{ "aria-label": "search-field" }}
            />
            <Divider orientation="vertical" />
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
                <IconButton aria-label="search">
                    <SearchIcon />
                </IconButton>
            </Box>
            
        </Paper>
    )
}

export default SearchBar;