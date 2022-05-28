import React, { useEffect, useState } from "react";
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import { default as FavoriteFilledIcon } from '@mui/icons-material/Favorite';
import { favoritesLocalStorageContainsItem, toggleItemInFavoritesLocalStorage } from "../../actions/favorites";


const FavoriteIcon = ({ item, component, componentProps, ...props }) => {
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