/*
    Functions for working with cart
*/

const CART_KEY = "cart";


const initialState = [];


export const getCartFromLocalStorage = () => 
    JSON.parse(window.localStorage.getItem(CART_KEY)) || initialState;


export const saveCartInLocalStorage = cart =>
    window.localStorage.setItem(CART_KEY, JSON.stringify(cart));


export const addItemToCartInLocalStorage = (item, amount) => {
    let cart = getCartFromLocalStorage();
    const isAdded = cart.findIndex(cartItem => cartItem.id === item.id) !== -1;

    if (isAdded)
        return true;

    cart.push({
        id: item.id,
        amount: amount
    });

    saveCartInLocalStorage(cart);

    return true;
}


export const changeItemInCartInLocalStorage = (item, amount) => {
    let cart = getCartFromLocalStorage();
    const cartItemIndex = cart.findIndex(c => c.id === item.id);

    if (cartItemIndex !== -1) {
        cart[cartItemIndex] = {
            id: item.id,
            amount: amount
        };
    }

    saveCartInLocalStorage(cart);
}


export const cartLocalStorageContainsItem = item => {
    const cart = getCartFromLocalStorage();

    return cart.findIndex(c => c.id === item.id) !== -1;
}