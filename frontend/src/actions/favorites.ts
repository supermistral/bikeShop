/*
    Function for working with favorites
*/

import { AnyProductItemData, FavoritesData } from "../constants/types";


const FAVORITES_KEY = "favorites";

const initialState: FavoritesData = { ids: [] };


export const getFavoritesFromLocalStorage = (): FavoritesData => 
    JSON.parse(window.localStorage.getItem(FAVORITES_KEY) || '') || initialState;


export const saveFavoritesInLocalStorage = (favorites: FavoritesData) =>
    window.localStorage.setItem(FAVORITES_KEY, JSON.stringify(favorites));


export const addItemToFavoritesInLocalStorage = (item: AnyProductItemData) => {
    let favorites = getFavoritesFromLocalStorage();

    if (!Array.isArray(favorites.ids))
        favorites = { ...favorites, ids: [] };

    favorites.ids.push(item.id);
    window.localStorage.setItem(FAVORITES_KEY, JSON.stringify(favorites));
}


export const favoritesLocalStorageContainsItem = (item: AnyProductItemData) => {
    const favorites = getFavoritesFromLocalStorage();
    return favorites.ids.indexOf(item.id) !== -1;
}


export const toggleItemInFavoritesLocalStorage = (item: AnyProductItemData) => {
    const favorites = getFavoritesFromLocalStorage();
    const idIndex = favorites.ids.indexOf(item.id);

    const isAdded = idIndex !== -1;

    if (isAdded) {
        favorites.ids.splice(idIndex, 1);
    } else {
        if (!Array.isArray(favorites.ids))
            favorites.ids = [];

        favorites.ids.push(item.id);
    }

    saveFavoritesInLocalStorage(favorites);

    return !isAdded 
}