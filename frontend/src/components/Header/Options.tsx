import { Stack, Fab } from "@mui/material";
import { useContext } from "react";
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import PersonOutlineOutlinedIcon from "@mui/icons-material/PersonOutlineOutlined";
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import RouteLink from "../DOM/RouteLink";
import UserAuthContext from "../DOM/UserAuthContext";


const Options = () => {
    const userAuthData = useContext(UserAuthContext);

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
            <RouteLink to="/cart">
                <Fab 
                    variant="extended" 
                    sx={{ textTransform: "none" }} 
                    color="primary"
                >
                    Корзина
                    <ShoppingCartOutlinedIcon sx={{ ml: 1 }} />
                </Fab>
            </RouteLink>
            {userAuthData.isAuthorized ? (
                <>
                    <RouteLink to="/profile">
                        <Fab
                            variant="extended" 
                            sx={{ 
                                textTransform: "none",
                                backgroundColor: "#fff"
                            }}
                        >
                            <AccountCircleOutlinedIcon sx={{ mr: 1 }} />
                            Профиль
                        </Fab>
                    </RouteLink>
                    <RouteLink to="/logout">
                        <Fab 
                            size="small" 
                            aria-label="logout"
                        >
                            <LogoutOutlinedIcon fontSize="small" />
                        </Fab>
                    </RouteLink>
                </>
            ) : (
                <RouteLink to="/login">
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
                </RouteLink>
            )}
        </Stack>
    )
}

export default Options;