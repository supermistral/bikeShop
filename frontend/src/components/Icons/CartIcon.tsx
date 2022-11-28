import React, { useEffect, useState } from "react";
import { addItemToCartInLocalStorage, cartLocalStorageContainsItem } from "../../actions/cart";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import ShoppingCartCheckoutIcon from '@mui/icons-material/ShoppingCartCheckout';
import { Typography, TypographyTypeMap } from "@mui/material";
import RouteLink from "../DOM/RouteLink";
import { AnyProps, ProductItemInstanceReducedData } from "../../constants/types";
import { DefaultComponentProps } from "@mui/material/OverridableComponent";


export interface CartIconProps extends DefaultComponentProps<TypographyTypeMap> {
    item: ProductItemInstanceReducedData;
    component: React.FC<{
        children: React.ReactNode | React.ReactNode[];
        onClick: () => void;
        disabled?: boolean;
    }>;
    componentProps: AnyProps;
    componentActiveProps: AnyProps;
    text: string;
}


const CartIcon = ({
    item,
    component,
    componentProps,
    componentActiveProps,
    text,
    ...props
}: CartIconProps) => {
    const [active, setActive] = useState<boolean>(cartLocalStorageContainsItem(item));

    useEffect(() => setActive(cartLocalStorageContainsItem(item)), [item]);

    const Component = component;
    const isDisabled = !item.stock;
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
        <Component {...componentProps} disabled={isDisabled} onClick={handleClick}>
            <ShoppingCartOutlinedIcon sx={{ mr: 1 }} />
            <Typography variant="body1" {...props}>{nonActiveText}</Typography>
        </Component>
    )
}

export default CartIcon;