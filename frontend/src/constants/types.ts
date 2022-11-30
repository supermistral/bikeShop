/*
    Request and response types
*/

import { GridRenderCellParams, GridRenderEditCellParams, GridValidRowModel } from "@mui/x-data-grid";

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

export type AnyProductItemInstanceData = ProductItemInstanceReducedData |
    ProductItemInstanceData |
    ProductItemInstanceWithAmountData;

export interface ProductItemReducedData {
    id: number;
    name: string;
    price: number;
}

export interface ProductItemInCategoryData extends ProductItemReducedData {
    image: string | null;
    stock: number;
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

export type UserRole = "manager" | "admin" | "user";

export interface UserRoles {
    roles?: UserRole[];
}

export interface UserAutorizedRoles extends UserRoles {
    isAuthorized: boolean;
};

export interface QuantityItemData {
    itemInstance: {
        id: number;
        itemId: number;
        categoryId: number;
        name: string;
        price: number;
        image: string | null;
        specifications: ProductSpecficiationReducedData[];
    },
    quantity: number;
}

export interface OrderData {
    id: number;
    status: string;
    createdAt: string;
    quantityItems: QuantityItemData[];
    price: number;
    user?: {
        email: string;
        name: string;
    }
}

export interface DataGridColData {
    field: string;
    type?: string;
    headerName: string;
    width: number;
    cellClassName?: string;
    editable?: boolean;
    helperText?: string;
    getActions?: ({ id }: { id: number }) => JSX.Element[];
    renderCell?: (params: GridRenderCellParams<string, GridValidRowModel, any>) => JSX.Element;
    renderEditCell?: (params: GridRenderEditCellParams) => JSX.Element;
    getValue?: (params: AnyProps) => any;
    valueOptions?: (string | number)[] | ((params: AnyProps) => (string | number)[]);
    valueFormatter?: (data: { value: string | number }) => string | number;
}