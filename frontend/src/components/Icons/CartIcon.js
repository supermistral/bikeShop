import React, { useState } from "react";
import { addItemToCartInLocalStorage, cartLocalStorageContainsItem } from "../../actions/cart";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import ShoppingCartCheckoutIcon from '@mui/icons-material/ShoppingCartCheckout';
import { Typography } from "@mui/material";
import RouteLink from "../DOM/RouteLink";


const CartIcon = ({
    item,
    component,
    componentProps,
    componentActiveProps,
    text,
    ...props
}) => {
    const [active, setActive] = useState(
        cartLocalStorageContainsItem(item)
    );

    const Component = component;
    const nonActiveText = text ? text : "В корзину";
    const activeText = "Уже в корзине"
    
    const handleClick = () => setActive(addItemToCartInLocalStorage(item, 1));

    return active ? (
        <RouteLink to="/cart">
            <Component {...componentProps} {...componentActiveProps} onClick={handleClick}>
                <Typography variant="body1" {...props}>{activeText}</Typography>
                <ShoppingCartCheckoutIcon sx={{ ml: 1 }} />
            </Component>
        </RouteLink>
    ) : (
        <Component {...componentProps} onClick={handleClick}>
            <ShoppingCartOutlinedIcon sx={{ mr: 1 }} />
            <Typography variant="body1" {...props}>{nonActiveText}</Typography>
        </Component>
    )
}

export default CartIcon;