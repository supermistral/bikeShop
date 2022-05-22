/*
    Function for working with favorites
*/

const FAVORITES_KEY = "favorites";


const initialState = { ids: [] };


export const getFavoritesFromLocalStorage = () => 
    JSON.parse(window.localStorage.getItem(FAVORITES_KEY)) || initialState;


export const saveFavoritesInLocalStorage = favorites =>
    window.localStorage.setItem(FAVORITES_KEY, JSON.stringify(favorites));


export const addItemToFavoritesInLocalStorage = item => {
    let favorites = getFavoritesFromLocalStorage();
    
    if (!Array.isArray(favorites.ids))
        favorites = { ...favorites, ids: [] };

    favorites.ids.push(item.id);
    window.localStorage.setItem(FAVORITES_KEY, JSON.stringify(favorites));
}


export const favoritesLocalStorageContainsItem = id => {
    const favorites = getFavoritesFromLocalStorage();
    return favorites.ids.indexOf(id) !== -1;
}


export const toggleItemInFavoritesLocalStorage = id => {
    const favorites = getFavoritesFromLocalStorage();
    const idIndex = favorites.ids.indexOf(id);

    const isAdded = idIndex !== -1;

    console.log(idIndex, isAdded);

    if (isAdded) {
        favorites.ids.splice(idIndex, 1);
    } else {
        if (!Array.isArray(favorites.ids))
            favorites.ids = [];

        favorites.ids.push(id);
    }

    saveFavoritesInLocalStorage(favorites);

    return !isAdded 
}