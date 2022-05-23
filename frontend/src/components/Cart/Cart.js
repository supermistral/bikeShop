import { Box, Button, Stack, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { getCartFromLocalStorage } from "../../actions/cart";
import { formatPrice } from "../../utils/product";
import CartItem from "./CartItem";


const Cart = () => {
    const isAuthorized = false;

    const [itemsData, setItemsData] = useState();
    const [items, setItems] = useState();

    useEffect(() => {
        // Local storage
        if (!isAuthorized) {
            setItemsData(getCartFromLocalStorage());
        }
    }, []);

    useEffect(() => {
        if (itemsData && itemsData.length !== 0) {
            fetch(`/api/items/instances?id=${itemsData.map(item => item.id).join(';')}`)
                .then(res => res.json())
                .then(data => setItems(data))
                .catch(e => console.log(e));
        }
    }, [itemsData]);

    const totalAmount = itemsData && itemsData.map(i => i.amount).reduce((a, b) => a + b, 0);
    const totalPrice = items && items.map((item, i) => item.item.price * itemsData[i].amount)
        .reduce((a, b) => a + b, 0);

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', pb: 1 }}>
            <Typography variant="h4" sx={{ py: 2 }}>Корзина</Typography>
            {items ? (
                <>
                    <Stack spacing={2} alignItems="stretch">
                        {items.map((item, i) => 
                            <CartItem 
                                key={i} 
                                itemInstance={item} 
                                amount={itemsData[i].amount} 
                            />
                        )}
                    </Stack>
                    <Box sx={{ display: 'flex', justifyContent: 'flex-end', my: 2 }}>
                        <Box sx={{ display: 'flex', alignItems: 'center', mx: 1, boxShadow: '0 0 0 2px #eef', px: 1 }}>
                            <Typography variant="subtitle2">Товаров</Typography>
                            <Typography variant="h6" sx={{ px: 1 }}>{totalAmount}</Typography>
                            <Typography variant="subtitle2">на сумму</Typography>
                            <Typography variant="h6" sx={{ px: 1, fontWeight: 700 }}>{formatPrice(totalPrice)} ₽</Typography>
                        </Box>
                        <Button variant="contained" size="large">
                            Заказать все
                        </Button>
                    </Box>
                </>
            ) : itemsData && (
                <Box>
                    <Typography variant="h5">Корзина пуста</Typography>
                </Box>
            )}
        </Box>
    )
}

export default Cart;