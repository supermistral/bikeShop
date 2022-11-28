import React, { useEffect, useState } from "react";
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import { default as FavoriteFilledIcon } from '@mui/icons-material/Favorite';
import { favoritesLocalStorageContainsItem, toggleItemInFavoritesLocalStorage } from "../../actions/favorites";
import { AnyProductItemData, AnyProps } from "../../constants/types";
import { DefaultComponentProps } from "@mui/material/OverridableComponent";
import { SvgIconTypeMap } from "@mui/material";


export interface FavoriteIconProps extends DefaultComponentProps<SvgIconTypeMap> {
    item: AnyProductItemData;
    component: React.FC<{
        children: React.ReactNode | React.ReactNode[];
        onClick: () => void;
    }>;
    componentProps: AnyProps;
}


const FavoriteIcon = ({ item, component, componentProps, ...props }: FavoriteIconProps) => {
    const Component = component;

    const [active, setActive] = useState(
        favoritesLocalStorageContainsItem(item)
    );

    useEffect(() => {
        setActive(favoritesLocalStorageContainsItem(item))
    }, [item]);

    const handleClick = () => {
        setActive(toggleItemInFavoritesLocalStorage(item))
    }
    
    return (
        <Component {...componentProps} onClick={handleClick}>
            {componentProps.children}
            {active ? (
                <FavoriteFilledIcon {...props} />
            ) : (
                <FavoriteBorderOutlinedIcon {...props} />
            )}
        </Component>
    )
}

export default FavoriteIcon;