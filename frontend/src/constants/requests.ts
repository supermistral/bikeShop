/*
    URLS
*/

import { AnyApiId } from "./types";


export const BASE_URL = "http://localhost:3000";

// API URLS

// Global api url
export const API_URL = "/api";

export const BASE_API_URL = BASE_URL + API_URL;

export const categoriesApiUrl = API_URL + "/category";

export const itemsApiUrl = API_URL + "/items";

export const getItemByIdApiUrl = (id: AnyApiId) => `${itemsApiUrl}/${id}`;


// REACT ROUTER URLS

export const catalogUrl = "/catalog";

export const getCatalogByCategoryIdUrl = (id: AnyApiId) => `${catalogUrl}/${id}`;

export const getItemByIdUrl = (id: AnyApiId) => `${catalogUrl}/item/${id}`;