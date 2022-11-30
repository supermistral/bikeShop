import {
    Box, Button, Card, CardActionArea, CardContent, CardMedia, Chip,
    Collapse, List, Typography
} from "@mui/material";
import { useState } from "react";
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import PermIdentityIcon from '@mui/icons-material/PermIdentity';
import RouteLink from "../../DOM/RouteLink";
import { formatPrice } from "../../../utils/product";
import OrderSelectStatus, { StatusChip } from "./OrderSelectStatus";
import { OrderData, QuantityItemData } from "../../../constants/types";


export interface OrderItemProps {
    item: QuantityItemData;
}

export interface OrderProps {
    order: OrderData;
    isAdmin?: boolean;
}


const OrderItem = ({ item }: OrderItemProps) => {
    return (
        <Card sx={{ display: 'flex', p: 1, mt: 3, position: 'relative' }}>
            <CardMedia
                component="img"
                image={item.itemInstance.image || undefined}
                sx={{ flexBasis: '30%', objectFit: 'contain', height: 150 }}
                alt={item.itemInstance.name}
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
                            to={`/catalog/item/${item.itemInstance.itemId}`}
                            underline="hover"
                        >
                            <Typography component="div" variant="h5">
                                {item.itemInstance.name}
                            </Typography>
                        </RouteLink>
                        <Box sx={{ py: 0.5, px: 1 }}>
                            {item.itemInstance.specifications.map((specification, i) =>
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
                            <Typography variant="body1" sx={{ lineHeight: 1, fontSize: '1.2em' }}>
                                {formatPrice(item.itemInstance.price)} ₽
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
                            <Typography variant="body1" sx={{ textAlign: 'right', lineHeight: 1, fontSize: '1.2em' }}>
                                {item.quantity}
                            </Typography>
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
                    </Box>
                </Box>
            </Box>
        </Card>
    )
}


const Order = ({ order, isAdmin }: OrderProps) => {
    const [open, setOpen] = useState<boolean>(false);

    const handleClick = () => setOpen(!open);

    const formatDatePart = (part: number) => ("0" + part).slice(-2);

    const date = new Date(order.createdAt);
    const dateParts = {
        day: formatDatePart(date.getDate()),
        month: formatDatePart(date.getMonth()),
        year: date.getFullYear(),
        hour: formatDatePart(date.getHours()),
        minute: formatDatePart(date.getMinutes())
    };

    return (
        <Card sx={{ width: '100%' }}>
            <CardContent>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                    <Box sx={{ display: 'flex', alignItems: 'center' }}>
                        <Typography variant="h6">Заказ №{order.id}</Typography>
                        {isAdmin && (
                            <Chip
                                avatar={<PermIdentityIcon />}
                                label={`${order.user?.name} / ${order.user?.email}`}
                                variant="outlined"
                                size="small"
                                sx={{ mx: 2 }}
                            />
                        )}
                    </Box>
                    <Typography variant="body1" variantMapping={{ body1: 'div' }}>
                        {isAdmin ? (
                            <OrderSelectStatus 
                                id={order.id}
                                status={order.status}
                            />
                        ) : (
                            <StatusChip value={order.status} />
                        )}
                    </Typography>
                </Box>
                <Box sx={{ display: 'flex', mt: 1 }}>
                    <Typography variant="body2" color="text.secondary" variantMapping={{ body2: 'div' }}>
                        {`${dateParts.day}.${dateParts.month}.${dateParts.year}`}
                        <div>
                            {`${dateParts.hour}:${dateParts.minute}`}
                        </div>
                    </Typography>
                    <Box sx={{ display: 'flex', flexDirection: 'column' }}>
                        {order.quantityItems.map((item, i) =>
                            <Box key={i} sx={{ display: 'flex', pl: 2, py: 0.5 }}>
                                <Typography 
                                    variant="body1"
                                    sx={{ 
                                        textAlign: 'center', 
                                        height: '2em',
                                        borderRadius: '1em', 
                                        border: '2px solid #eef',
                                        fontSize: '0.8em',
                                        px: 0.75
                                    }}
                                    variantMapping={{ body1: 'div' }}
                                >
                                    <Typography variant="caption" color="text.secondary">арт. </Typography>
                                    {item.itemInstance.id}
                                </Typography>
                                <Typography sx={{ px: 1}} variant="body1">{item.itemInstance.name}</Typography>
                                <Box sx={{ '& > *': {display: 'inline-block'} }}>
                                    {item.itemInstance.specifications.map((spec, specIndex) =>
                                        <Typography 
                                            component="div"
                                            sx={{ px: 1, borderRight: '1px solid #eee', '&:last-child': { borderRight: 'none'} }} 
                                            key={specIndex} 
                                            variant="caption"
                                            color="text.secondary"
                                        >
                                            {spec.value}
                                        </Typography>
                                    )}
                                </Box>
                            </Box>
                        )}
                    </Box>
                </Box>
                <Collapse in={open} timeout="auto" unmountOnExit>
                    <List component="div" disablePadding>
                        {order.quantityItems.map((item, i) =>
                            <OrderItem key={i} item={item} />
                        )}
                    </List>
                </Collapse>
            </CardContent>
            <CardActionArea onClick={handleClick} sx={{ display: 'flex', justifyContent: 'center' }}>
                <Button component="div" endIcon={open ? <ExpandLess /> : <ExpandMore />}>
                    {open ? "Скрыть" : "Развернуть"}
                </Button>
            </CardActionArea>
        </Card>
    )
}

export default Order;