import { 
    Button, Card, CardActionArea, CardActions, 
    CardContent, CardMedia, Typography, Box, Chip, IconButton, Link
} from "@mui/material";
import React from "react";
import { Link as RouteLink } from "react-router-dom";
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import { getItemByIdUrl } from "../../../../constants/requests";
import FavoriteIcon from "../../../Icons/FavoriteIcon";
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import { formatPrice } from "../../../../utils/product";
import { ProductItemInCategoryData } from "../../../../constants/types";


export interface ProductCardProps {
    item: ProductItemInCategoryData;
}


const ProductCard = ({ item }: ProductCardProps) => {
    return (
        <Card sx={{ display: 'flex', flexDirection: 'column', justifyContent: 'stretch',  width: '100%', height: 350 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', p: 1 }}>
                <Chip
                    sx={{ py: 1, fontSize: '0.75em' }}
                    size="small"
                    {...(item.stock ? {
                        icon: <CheckCircleOutlineIcon />,
                        color: "success",
                        label: "в наличии",
                    } : {
                        icon: <RemoveCircleOutlineIcon />,
                        color: "default",
                        label: "нет в наличии",
                    })}
                />
                <FavoriteIcon
                    item={item}
                    component={IconButton}
                    componentProps={{
                        size: "small",
                        color: "error",
                        "aria-label": "Добавить в избранное",
                        sx: { p: 0 },
                    }}
                />
            </Box>
            <CardActionArea sx={{ flex: 1 }}>
                <Link 
                    component={RouteLink} 
                    to={getItemByIdUrl(item.id)}
                    sx={{ textDecoration: 'none', color: 'inherit' }}
                >
                    <CardMedia
                        component="img"
                        image={item.image || undefined}
                        alt={item.name}
                        sx={{ height: 180, width: '100%', objectFit: 'contain' }}
                    />
                    <CardContent sx={{ paddingBottom: '16px !important' }}>
                        <Typography variant="subtitle1" sx={{ fontWeight: 600 }}>{item.name}</Typography>
                        <Typography variant="body2"></Typography>
                        
                    </CardContent>
                </Link>
            </CardActionArea>
            <CardActions sx={{
                display: 'flex',
                justifyContent: 'space-between',
                p: 0,
                borderTop: '1px solid #ddd',
            }}>
                <Typography variant="subtitle1" sx={{ backgroundColor: '#eef', px: 2, py: 0.5 }}>
                    {formatPrice(item.price)} ₽
                </Typography>
                <Button size="small" color="primary" sx={{ textTransform: 'none' }}>
                    В корзину
                    <AddShoppingCartIcon sx={{ ml: 0.5, fontSize: '1.5em'}} />
                </Button>
            </CardActions>
        </Card>
    )
}

export default ProductCard;