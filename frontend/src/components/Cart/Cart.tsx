import { Box, Button, Stack, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import CheckCircleOutlinedIcon from '@mui/icons-material/CheckCircleOutlined';
import { changeItemInCartInLocalStorage, cleanCartInLocalStorage, deleteItemFromCartInLocalStorage, getCartFromLocalStorage } from "../../actions/cart";
import axiosInstance from "../../constants/axios";
import { formatPrice } from "../../utils/product";
import CartItem from "./CartItem";
import RouteLink from "../DOM/RouteLink";
import AuthorizationAlert from "../Authorization/AuthorizationAlert/AuthorizationAlert";
import { CartData, ProductItemInstanceData, ProductItemInstanceWithAmountData } from "../../constants/types";


const Cart = () => {
    const [cartItems, setCartItems] = useState<CartData>();
    const [items, setItems] = useState<ProductItemInstanceWithAmountData[]>();
    const [success, setSuccess] = useState<boolean>(false);
    const [authAlertOpen, setAuthAlertOpen] = useState<boolean>(false);

    useEffect(() => {
        // Local storage
        setCartItems(getCartFromLocalStorage());
    }, []);

    useEffect(() => {
        if (cartItems && cartItems.length !== 0) {
            fetch(`/api/items/instances?id=${cartItems.map(item => item.id).join(';')}`)
                .then(res => res.json())
                .then((data: ProductItemInstanceData[]) => setItems(
                    data.map((d, i) => ({ ...d, amount: cartItems[i].amount }))
                ))
                .catch(e => console.log(e));
        }
    }, [cartItems]);

    const handleCloseClick = (item: ProductItemInstanceWithAmountData) => () => {
        const itemIndex = deleteItemFromCartInLocalStorage(item);
        
        if (itemIndex !== -1) {
            setItems(prev => {
                const newItems = [...(prev!)];
                newItems.splice(itemIndex, 1);

                return newItems;
            });
        }   
    }

    const handleAmountChange = (item: ProductItemInstanceWithAmountData) => (newAmount: number) => {
        changeItemInCartInLocalStorage(item, newAmount);
        setItems(prev => {
            const newItems = [...(prev!)];
            newItems[newItems.findIndex(i => i.id === item.id)].amount = newAmount;
            return newItems;
        });
    }

    const handleCreateOrderClick = () => {
        const quanityItems = items!.map(item => ({
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
                                changeAmount={handleAmountChange(item)}
                            />
                        )}
                    </Stack>
                    <Box sx={{ display: 'flex', justifyContent: 'flex-end', my: 2 }}>
                        <Box sx={{ display: 'flex', alignItems: 'center', mx: 1, boxShadow: '0 0 0 2px #eef', px: 1 }}>
                            <Typography variant="subtitle2">Товаров</Typography>
                            <Typography variant="h6" sx={{ px: 1 }}>{totalAmount}</Typography>
                            <Typography variant="subtitle2">на сумму</Typography>
                            <Typography variant="h6" sx={{ px: 1, fontWeight: 700 }}>{totalPrice ? formatPrice(totalPrice) : ''} ₽</Typography>
                        </Box>
                        <Button variant="contained" size="large" onClick={handleCreateOrderClick}>
                            Заказать все
                        </Button>
                    </Box>
                </>
            ) : cartItems && (
                <Box>
                    <Typography variant="h6">Корзина пуста</Typography>
                </Box>
            )}
            {authAlertOpen && <AuthorizationAlert close={() => setAuthAlertOpen(false)} isOpen={true} />}
        </Box>
    )
}

export default Cart;