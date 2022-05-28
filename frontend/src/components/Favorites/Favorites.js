import { Box, Grid, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import ShopOutlinedIcon from '@mui/icons-material/ShopOutlined';
import ProductCard from "../Catalog/ProductList/ProductCard/ProductCard";
import RouteLink from "../DOM/RouteLink";
import { getFavoritesFromLocalStorage } from "../../actions/favorites";


const Favorites = () => {
    const isAuthorized = false;
    const [itemsData, setItemsData] = useState();
    const [items, setItems] = useState();

    useEffect(() => {
        // Local storage
        if (!isAuthorized) {
            setItemsData(getFavoritesFromLocalStorage());
        }
    }, []);

    useEffect(() => {
        console.log(itemsData);
        if (itemsData && itemsData.ids.length !== 0) {
            fetch(`/api/items?id=${itemsData.ids.join(';')}`)
                .then(res => res.json())
                .then(data => setItems(data))
                .catch(e => console.log(e));
        }

    }, [itemsData]);

    return (
        <Box>
            <Typography variant="h4" sx={{ py: 2 }}>Избранное</Typography>
            {items ? (
                <Grid
                    container
                    spacing={{ xs: 2, md: 3 }}
                    columns={{ xs: 4, sm: 8, md: 12 }}
                    sx={{ py: 2 }}
                >
                    {items.map((item, i) =>
                        <Grid key={i} item xs={2} sm={4} md={4}>
                            <ProductCard item={item} />
                        </Grid>
                    )}
                </Grid>
            ) : (
                <Box sx={{ 
                    display: 'flex', 
                    flexDirection: 'column', 
                }}>
                    <Typography variant="h6">Избранное пусто</Typography>
                    <Typography variant="body1">
                        <RouteLink to="/catalog">
                            Перейти в каталог<ShopOutlinedIcon sx={{ ml: 0.5, verticalAlign: 'top' }} />
                        </RouteLink>
                    </Typography>
                </Box>
            )}
        </Box>
    )
}

export default Favorites;