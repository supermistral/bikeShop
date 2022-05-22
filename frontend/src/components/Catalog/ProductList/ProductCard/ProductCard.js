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


const ProductCard = ({ item }) => {
    return (
        <Card sx={{ width: '100%' }}>
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
            <CardActionArea>
                <Link component={RouteLink} to={getItemByIdUrl(item.id)}>
                    <CardMedia
                        component="img"
                        image={item.image}
                        alt={item.name}
                    />
                    <CardContent sx={{ pb: 0 }}>
                        <Typography variant="subtitle1" sx={{ fontWeight: 600 }}>{item.name}</Typography>
                        <Typography variant="body2"></Typography>
                        <Typography variant="subtitle1">{item.price} ₽</Typography>
                    </CardContent>
                </Link>
            </CardActionArea>
            <CardActions className="card-actions">
                <Button size="small" color="primary">Купить</Button>
            </CardActions>
        </Card>
    )
}

export default ProductCard;