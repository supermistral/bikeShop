/*
    URLS
*/

// API URLS

// Global api url
export const API_URL = "/api";

export const categoriesApiUrl = API_URL + "/category";

export const itemsApiUrl = API_URL + "/items";

export const getItemByIdApiUrl = id => `${itemsApiUrl}/${id}`;


// REACT ROUTER URLS

export const catalogUrl = "/catalog";

export const getCatalogByCategoryIdUrl = id => `${catalogUrl}/${id}`;

export const getItemByIdUrl = id => `${catalogUrl}/item/${id}`;