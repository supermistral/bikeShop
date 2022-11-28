import { Divider, Stack, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React, { useEffect, useState } from "react";
import { CategoryDetailData, ProductItemInCategoryData } from "../../../../constants/types";
import CategoryFilter from "./CategoryFilter";
import PriceFilter from "./PriceFilter";
import SpecificationFilter from "./SpecificationFilter";


export interface ProductFiltersProps {
    category: CategoryDetailData;
    items: ProductItemInCategoryData[];
}

export interface FilterData {
    name?: string;
    filter: JSX.Element;
}


const getFilters = (category: CategoryDetailData): FilterData[] => [
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


const ProductFilters = ({ category, items }: ProductFiltersProps) => {
    const [filters, setFilters] = useState<FilterData[]>([]);

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