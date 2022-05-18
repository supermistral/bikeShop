import { Box, Grid } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";
import ProductCard from "./ProductCard/ProductCard";
import ProductFilters from "./ProductFilters/ProductFilters";
import ProductTopBar from "./ProductTopBar/ProductTopBar";


const ProductList = () => {
    const [category, setCategory] = useState();
    const [items, setItems] = useState([]);
    const { categoryId } = useParams();

    const [searchParams, setSearchParams] = useSearchParams();

    useEffect(() => {
        fetch(`/api/category/${categoryId}`)
            .then(res => res.json())
            .then(data => setCategory(data))
            .catch(e => console.log(e));
    }, [categoryId])

    useEffect(() => {
        fetch(`/api/category/${categoryId}/items?${searchParams.toString()}`)
            .then(res => res.json())
            .then(data => setItems(data))
            .catch(e => console.log(e));
    }, [searchParams]);

    return (
        <Box sx={{ display: 'flex' }}>
            {category &&
                <>
                    <ProductFilters category={category} items={items} />
                    <Box sx={{ flex: 1, px: 4 }}>
                        <ProductTopBar />
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
                    </Box>
                </>
            }
        </Box>
    )
}

export default ProductList;