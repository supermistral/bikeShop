import { Divider, Stack, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React, { useEffect, useState } from "react";
import CategoryFilter from "./CategoryFilter";
import PriceFilter from "./PriceFilter";
import SpecificationFilter from "./SpecificationFilter";


const getFilters = (category) => [
    {
        name: "Категории",
        filter: <CategoryFilter category={category} />
    },
    {
        name: "Цена",
        filter: <PriceFilter price={category.price} />
    },
    ...SpecificationFilter({ specifications: category.specifications }),
]


const ProductFilters = ({ category, items }) => {
    const [filters, setFilters] = useState([]);

    useEffect(() => setFilters(getFilters(category)), [category]);

    return (
        <Stack sx={{ maxWidth: 245 }}>
            <Typography variant="h5" sx={{ py: 1 }}>Фильтр</Typography>
            {filters.map((filter, i) => 
                <Box key={i}>
                    <Box sx={{ py: 1 }}>
                        {filter.name ? (
                            <>
                                <Typography variant="subtitle1">{filter.name}</Typography>
                                <Box sx={{ py: 0.5 }}>
                                    {filter.filter}
                                </Box>
                            </>
                        ) : (
                            <Box>{filter.filter}</Box>
                        )}
                    </Box>
                    {i !== filters.length - 1 && <Divider />}
                </Box>
            )}
        </Stack>
    )
}

export default ProductFilters;