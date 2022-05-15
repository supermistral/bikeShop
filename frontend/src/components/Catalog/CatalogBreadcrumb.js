import { Breadcrumbs, Link, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { Link as RouterLink, useLocation } from "react-router-dom";


const getBreadcrumbMapRecursive = (items) => {
    let breadcrumbMap = {};

    items.forEach(item => {
        if (true) {
            const path = item.id.toString();
            breadcrumbMap[path] = item.name;

            breadcrumbMap = {
                ...breadcrumbMap,
                ...getBreadcrumbMapRecursive(item.children)
            };
        }
    })

    return breadcrumbMap;
}

const getBreadcrumbMap = items => ({
    '': 'Все',
    ...getBreadcrumbMapRecursive(items)
});

const fillPathById = (id, items, path) => {
    for (const item of items) {
        const itemId = item.id.toString()
        const itemIdIndex = path.length;

        path.push(itemId);

        if (itemId === id) 
            return true;
        
        const success = fillPathById(id, item.children, path);

        if (success)
            return true;

        path.splice(itemIdIndex, 1);
    }

    return false;
}

const createPathById = (id, items) => {
    let path = [''];

    if (id === path[0])
        return path;

    const success = fillPathById(id, items, path);
    return success ? path : [];
}


const CatalogBreadcrumb = ({ items }) => {
    const [breadcrumbMap, setBreadcrumbMap] = useState({});
    
    const location = useLocation();

    const pathnames = location.pathname.split('/').filter(x => x);
    const categoryId = pathnames[1] || '';
    const catalogPath = createPathById(categoryId, items);

    useEffect(() => setBreadcrumbMap(getBreadcrumbMap(items)), [items]);
    
    return (
        <Breadcrumbs aria-label="breadcrumb" sx={{ py: 2 }}>
            {catalogPath.map((pathname, i) => {
                const to = `/catalog${pathname ? "/" + pathname : ""}`;
                const isLast = i === catalogPath.length - 1;

                return isLast ? (
                    <Typography key={to} color="text.primary">
                        {breadcrumbMap[pathname]}
                    </Typography>
                ) : (
                    <Link key={to} component={RouterLink} to={to} color="inherit" underline="hover">
                        {breadcrumbMap[pathname]}
                    </Link>
                )
            })}
        </Breadcrumbs>
    )
}

export default CatalogBreadcrumb;