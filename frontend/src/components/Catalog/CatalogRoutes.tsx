import { useRoutes } from "react-router-dom";
import { CategoryData, CategoryWithoutParentData } from "../../constants/types";
import CatalogSection from "./CatalogSection";
import ProductDetail from "./ProductDetail/ProductDetail";
import ProductList from "./ProductList/ProductList";


export interface CatalogRoutesProps {
    items: CategoryData[];
}

type RouteData = {
    path: string;
    element: JSX.Element;
}


const getRoutesRecursive = (items: CategoryWithoutParentData[]) => {
    let routes: RouteData[] = [];

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

const getRoutes = (items: CategoryData[]) => [
    {
        path: '',
        element: <CatalogSection items={items} />
    },
    {
        path: ':categoryId/items',
        element: <ProductList />
    },
    {
        path: 'item/:itemId',
        element: <ProductDetail />
    },
    ...getRoutesRecursive(items)
];


const CatalogRoutes = ({ items }: CatalogRoutesProps) => {
    const element = useRoutes(getRoutes(items));
    return element;
}

export default CatalogRoutes;