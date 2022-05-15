import React from "react";
import { useRoutes } from "react-router-dom";
import CatalogSection from "./CatalogSection";
import ProductList from "./ProductList/ProductList";


const getRoutesRecursive = items => {
    let routes = [];

    items.forEach(item => {
        if (item.children.length !== 0) {
            routes = [
                ...routes,
                {
                    path: item.id.toString(),
                    element: <CatalogSection items={item.children} />
                },
                ...getRoutesRecursive(item.children)
            ];
        }
    })

    return routes;
}

const getRoutes = items => [
    {
        path: '',
        element: <CatalogSection items={items} />
    },
    {
        path: ':categoryId/items',
        element: <ProductList categories={items} />
    },
    ...getRoutesRecursive(items)
];


const CatalogRoutes = ({ items }) => {
    const element = useRoutes(getRoutes(items));
    return element;
}

export default CatalogRoutes;