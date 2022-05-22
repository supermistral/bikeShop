import { IconButton, Stack, Fab } from "@mui/material";
import React from "react";
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import PersonOutlineOutlinedIcon from "@mui/icons-material/PersonOutlineOutlined";
import RouteLink from "../DOM/RouteLink";


const Options = () => {
    return (
        <Stack direction="row" sx={{ alignItems: 'center' }} spacing={1}>
            <RouteLink to="/favorites">
                <Fab 
                    size="small" 
                    aria-label="like"
                >
                    <FavoriteBorderOutlinedIcon fontSize="small" />
                </Fab>
            </RouteLink>
            <Fab 
                variant="extended" 
                sx={{ textTransform: "none" }} 
                color="primary"
            >
                Корзина
                <ShoppingCartOutlinedIcon sx={{ ml: 1 }} />
            </Fab>
            <Fab
                variant="extended" 
                sx={{ 
                    textTransform: "none",
                    backgroundColor: "#fff"
                }}
            >
                <PersonOutlineOutlinedIcon sx={{ mr: 1 }} />
                Вход / Регистрация
            </Fab>
        </Stack>
    )
}

export default Options;