import { Box, Button, Card, CardContent, CardMedia, Typography, IconButton, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";
import { formatPrice } from "../../utils/product";
import RouteLink from "../DOM/RouteLink";
import CloseIcon from '@mui/icons-material/Close';
import { ProductItemInstanceWithAmountData } from "../../constants/types";


export interface CartItemProps {
    itemInstance: ProductItemInstanceWithAmountData;
    changeAmount: (amount: number) => void;
    itemAmount: number;
    closeClick: () => void;
}


const CartItem = ({ itemInstance, changeAmount, itemAmount, closeClick }: CartItemProps) => {
    const [amount, setAmount] = useState<number>(itemAmount);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;

        if (value === "")
            setAmount(0);
            // setAmount(value);

        if (parseInt(value) === +value) {
            const intValue = +value;
            if (intValue <= itemInstance.stock && intValue > 0) {
                setAmount(intValue);
            }
        }
    }

    const handleBlur = (e: React.FocusEvent<HTMLInputElement>) => {
        const value = e.target.value;

        if (parseInt(value) === +value) {
            const intValue = +value;
            if (intValue > itemInstance.stock || intValue <= 0)
                setAmount(itemAmount);
        } else {
            setAmount(itemAmount);
        } 
    }

    useEffect(() => {
        if (typeof amount !== "string") {
            changeAmount(amount);
        }
    }, [amount]);

    const totalPrice = itemInstance.item.price * amount;

    return (
        <Card sx={{ display: 'flex', p: 1, height: 200, position: 'relative' }}>
            <CardMedia
                component="img"
                image={itemInstance.image || undefined}
                sx={{ height: 150, flexBasis: '30%', objectFit: 'contain' }}
                alt={itemInstance.item.name}
            />
            <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
                <CardContent sx={{ 
                    display: 'flex', 
                    justifyContent: 'space-between', 
                    flex: 1, 
                    mb: 1, 
                    backgroundColor: 'rgba(40, 40, 40, .05)' 
                }}>
                    <Box>
                        <RouteLink 
                            to={`/catalog/item/${itemInstance.item.id}`}
                            underline="hover"
                        >
                            <Typography component="div" variant="h5">
                                {itemInstance.item.name}
                            </Typography>
                        </RouteLink>
                        <Box sx={{ py: 0.5, px: 1 }}>
                            {itemInstance.specifications.map((specification, i) =>
                                <Box key={i} sx={{ display: 'flex' }}>
                                    <Typography component="span" variant="body2" sx={{ width: '6em' }}>
                                        {specification.key}
                                    </Typography>
                                    <Typography component="span" variant="body2" sx={{ px: 1 }}>
                                        {specification.value}
                                    </Typography>
                                </Box>
                            )}
                        </Box>
                    </Box>
                    <Box>
                        <Box sx={{ py: 1 }}>
                            <Typography 
                                component="div" 
                                variant="subtitle2" 
                                sx={{ textAlign: 'right' }}
                            >
                                Цена
                            </Typography>
                            <Typography variant="body1" sx={{ textAlign: 'right', lineHeight: 1, fontSize: '1.2em' }}>
                                {formatPrice(itemInstance.item.price)} ₽
                            </Typography>
                        </Box>
                        <Box>
                            <Typography 
                                component="div" 
                                variant="subtitle2" 
                                sx={{ textAlign: 'right' }}
                            >
                                Количество
                            </Typography>
                            <Box sx={{ display: 'flex' }}>
                                <Typography variant="caption">доступно {itemInstance.stock} шт.</Typography>
                                <Typography 
                                    component="div" 
                                    variant="body1" 
                                    sx={{ textAlign: 'right', lineHeight: 1, fontSize: '1.2em', ml: 1 }}
                                >
                                    <TextField 
                                        id={`input-amount-${itemInstance.id}`} 
                                        variant="filled"
                                        value={amount}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        inputProps={{ style: { textAlign: 'center', fontSize: '0.85em', padding: '0px' } }}
                                        sx={{ maxWidth: '1.5em' }}
                                        size="small"
                                    />
                                </Typography>
                            </Box>
                        </Box>
                    </Box>
                </CardContent>
                <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                    <Box sx={{ 
                        display: 'flex', 
                        alignItems: 'center', 
                        mx: 1, 
                        px: 2, 
                        boxShadow: '0 0 0 2px #eef' 
                    }}>
                        <Typography 
                            component="div" 
                            variant="subtitle1"
                        >
                            Итого
                        </Typography>
                        <Typography component="div" variant="h6" sx={{ fontWeight: 700, ml: 2 }}>
                            {formatPrice(totalPrice)} ₽
                        </Typography>
                    </Box>
                    <Button variant="contained">
                        Оформить
                    </Button>
                </Box>
            </Box>
            <IconButton 
                aria-label="Удалить" 
                sx={{ 
                    position: 'absolute', 
                    right: '-5px', 
                    top: '-5px', 
                    fontSize: '1.25em',
                    backgroundColor: '#eee',
                    '& > svg': { 'fontSize': '1em' }
                }}
                onClick={closeClick}
            >
                <CloseIcon />
            </IconButton>
        </Card>
    )
}

export default CartItem;