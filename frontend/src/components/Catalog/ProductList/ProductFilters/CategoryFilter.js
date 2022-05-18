import React, { useEffect } from "react";
import ListFilter from "./ListFilter";


const CategoryFilter = ({ category }) => {
    return ListFilter({ 
        name: 'category',
        items: category.lastChildren,
        searchParamKey: 'category',
        values: category.lastChildren.map(item => item.id),
    });
}

export default CategoryFilter;