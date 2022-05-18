/*
    API URLS
*/

// Global api url
export const API_URL = "/api";

export const getCategoriesApiUrl = API_URL + "/category";

export const catalogReactUrl = "/catalog";

export const getCatalogByCategoryIdReactUrl = id => `${catalogReactUrl}/${id}`;