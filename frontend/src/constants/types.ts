/*
    Request and response types
*/

export interface AnyProps extends Record<string, any> {}

export type AnyApiId = string | number | undefined;

export interface JWTokensData {
    accessToken: string;
    refreshToken: string;
}

export interface JWTData extends JWTokensData {
    email: string;
    roles: string[];
    tokenType?: string;
}

export interface ProductSpecficiationReducedData {
    key: string;
    value: string;
}

export interface ProductSpecficiationData extends ProductSpecficiationReducedData {
    id: number;
}

export interface ProductSpecificationWithValuesData {
    id: number;
    key: string;
    values: string[];
}

export interface ProductItemInstanceReducedData {
    id: number;
    stock: number;
    images: string[];
    specifications: ProductSpecficiationData[];
}

export interface ProductItemReducedData {
    id: number;
    name: string;
    price: number;
}

export interface ProductItemInCategoryData extends ProductItemReducedData {
    image: string | null;
    stock: number;
}

export interface ProductItemInstanceData {
    id: number;
    stock: number;
    image: string | null;
    specifications: ProductSpecficiationData[];
    item: ProductItemInCategoryData;
}

export interface ProductItemInstanceWithAmountData extends ProductItemInstanceData {
    amount: number;
}

export interface ProductItemData extends ProductItemReducedData {
    instances: ProductItemInstanceReducedData[]
    specifications: ProductSpecficiationReducedData[]
    description: string;
}

export type AnyProductItemData = ProductItemData | ProductItemInCategoryData;

export interface CategoryWithoutParentData {
    id: number;
    name: string;
    children: CategoryWithoutParentData[];
    image: string | null;
}

export interface CategoryData extends CategoryWithoutParentData {
    parent: CategoryData | null;
}

export interface CategoryDetailData {
    name: string;
    lastChildren: {
        id: number;
        name: string;
    }[];
    specifications: ProductSpecificationWithValuesData[];
    price: {
        min: number;
        max: number;
    }
}

export interface FavoritesData {
    ids: number[];
}

export interface CartItemData {
    id: number;
    amount: number;
}

export type CartData = CartItemData[];