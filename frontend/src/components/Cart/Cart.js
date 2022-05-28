import { Box, Button, Stack, Typography } from "@mui/material";
import React, { useCallback, useContext, useEffect, useState } from "react";
import CheckCircleOutlinedIcon from '@mui/icons-material/CheckCircleOutlined';
import { changeItemInCartInLocalStorage, cleanCartInLocalStorage, deleteItemFromCartInLocalStorage, getCartFromLocalStorage } from "../../actions/cart";
import axiosInstance from "../../constants/axios";
import { formatPrice } from "../../utils/product";
import CartItem from "./CartItem";
import RouteLink from "../DOM/RouteLink";
import UserAuthContext from "../DOM/UserAuthContext";
import AuthorizationAlert from "../Authorization/AuthorizationAlert/AuthorizationAlert";


const Cart = () => {
    const { isAuthorized } = useContext(UserAuthContext);

    const [itemsData, setItemsData] = useState();
    const [items, setItems] = useState();
    const [success, setSuccess] = useState(false);
    const [authAlertOpen, setAuthAlertOpen] = useState(false);

    useEffect(() => {
        // Local storage
        setItemsData(getCartFromLocalStorage());
    }, []);

    useEffect(() => {
        if (itemsData && itemsData.length !== 0) {
            fetch(`/api/items/instances?id=${itemsData.map(item => item.id).join(';')}`)
                .then(res => res.json())
                .then(data => setItems(
                    data.map((d, i) => ({ ...d, amount: itemsData[i].amount }))
                ))
                .catch(e => console.log(e));
        }
    }, [itemsData]);

    const handleCloseClick = item => () => {
        const itemIndex = deleteItemFromCartInLocalStorage(item);
        
        if (itemIndex !== -1) {
            setItems(prev => {
                const newItems = [...prev];
                newItems.splice(itemIndex, 1);

                return newItems;
            });
        }   
    }

    const handleAmountChange = item => newAmount => {
        changeItemInCartInLocalStorage(item, newAmount);
        setItems(prev => {
            const newItems = [...prev];
            newItems[newItems.findIndex(i => i.id === item.id)].amount = newAmount;
            return newItems;
        });
    }

    const handleCreateOrderClick = () => {
        const quanityItems = items.map(item => ({
            itemInstance: {
                id: item.id
            },
            quantity: item.amount
        }));

        axiosInstance
            .post("orders", quanityItems)
            .then(res => {
                cleanCartInLocalStorage();
                setSuccess(true);
            })
            .catch(err => setAuthAlertOpen(true));
    }

    const totalAmount = items && items.map(i => i.amount)
        .reduce((a, b) => a + b, 0);
    const totalPrice = items && items.map((item, i) => item.item.price * item.amount)
        .reduce((a, b) => a + b, 0);

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', pb: 1 }}>
            <Typography variant="h4" sx={{ py: 2 }}>Корзина</Typography>
            {success ? (
                <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <CheckCircleOutlinedIcon color="success" sx={{ 
                            fontSize: '3em', 
                            p: 1,
                            borderRadius: '50%',
                        }}/>
                        <Typography component="span" variant="h5">
                            Заказ успешно создан
                        </Typography>
                    <RouteLink to="/profile" sx={{ mt: 2 }}>
                        <Button variant="outlined">
                            Мои заказы
                        </Button>
                    </RouteLink>
                </Box>
            ) : items && items.length !== 0 ? (
                <>
                    <Stack spacing={2} alignItems="stretch">
                        {items.map((item, i) => 
                            <CartItem 
                                key={i} 
                                itemInstance={item} 
                                itemAmount={item.amount}
                                closeClick={handleCloseClick(item)}
                                changeAmount={handleAmountChange(itemsData[i])}
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
                        <Button variant="contained" size="large" onClick={handleCreateOrderClick}>
                            Заказать все
                        </Button>
                    </Box>
                </>
            ) : itemsData && (
                <Box>
                    <Typography variant="h6">Корзина пуста</Typography>
                </Box>
            )}
            {authAlertOpen && <AuthorizationAlert close={() => setAuthAlertOpen(false)} isOpen={true} />}
        </Box>
    )
}

export default Cart;