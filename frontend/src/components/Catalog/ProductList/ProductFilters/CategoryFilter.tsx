import React, { useEffect } from "react";
import { CategoryDetailData } from "../../../../constants/types";
import ListFilter from "./ListFilter";


export interface CategoryFilterProps {
    category: CategoryDetailData;
}


const CategoryFilter = ({ category }: CategoryFilterProps) => {
    return ListFilter({ 
        name: 'category',
        items: category.lastChildren,
        searchParamKey: 'category',
        values: category.lastChildren.map(item => item.id),
    });
}

export default CategoryFilter;