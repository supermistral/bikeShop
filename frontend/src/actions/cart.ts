/*
    Functions for working with cart
*/

import { AnyProductItemInstanceData, CartData } from "../constants/types";


const CART_KEY = "cart";

const initialState: CartData = [];


export const getCartFromLocalStorage = (): CartData => 
    JSON.parse(window.localStorage.getItem(CART_KEY) || '') || initialState;


export const saveCartInLocalStorage = (cart: CartData) =>
    window.localStorage.setItem(CART_KEY, JSON.stringify(cart));


export const addItemToCartInLocalStorage = (item: AnyProductItemInstanceData, amount: number) => {
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


export const changeItemInCartInLocalStorage = (item: AnyProductItemInstanceData, amount: number) => {
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


export const cartLocalStorageContainsItem = (item: AnyProductItemInstanceData) => {
    const cart = getCartFromLocalStorage();

    return cart.findIndex(c => c.id === item.id) !== -1;
}


export const deleteItemFromCartInLocalStorage = (item: AnyProductItemInstanceData) => {
    let cart = getCartFromLocalStorage();
    const cartItemIndex = cart.findIndex(c => c.id === item.id);

    if (cartItemIndex !== -1) {
        cart.splice(cartItemIndex, 1);
        saveCartInLocalStorage(cart);
    }

    return cartItemIndex;
}


export const cleanCartInLocalStorage = () => {
    window.localStorage.removeItem(CART_KEY);
}