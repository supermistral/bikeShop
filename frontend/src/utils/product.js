/*
    Utils for work with Product's related components
*/

export const formatPrice = price => 
    price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");