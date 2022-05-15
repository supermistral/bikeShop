import { Box, Button, Card, CardActionArea, CardActions, CardContent, CardMedia, Grid, Link, Typography } from "@mui/material";
import React from "react";
import { Link as RouteLink } from "react-router-dom";


const getCategoryUrl = item => `/catalog/${item.id}`;
const getCategoryItemsUrl = item => `/catalog/${item.id}/items`;

const getCategoryLinkUrl = item => item.children.length !== 0 
    ? getCategoryUrl(item)
    : getCategoryItemsUrl(item);


const CatalogItem = ({ item }) => {
    return (
        <Link component={RouteLink} to={getCategoryLinkUrl(item)} underline="none">
            <Card sx={{ maxWidth: 345 }}>
                <CardActionArea>
                    <CardMedia
                        component="img"
                        height="140"
                        image={item.image}
                        alt=""
                    />
                    <CardContent sx={{ py: 1 }}>
                        <Box sx={{ display: 'flex', jusitfyContent: 'space-between' }}>
                            <Typography variant="body1">
                                {item.name}
                            </Typography>
                        </Box>
                    </CardContent>
                </CardActionArea>
                <CardActions sx={{ display: 'flex', justifyContent: 'flex-end', p: 0, pr: 0.5, pb: 0.5 }}>
                    <Button size="small" sx={{ textTransform: 'none' }}>
                        <Link 
                            component={RouteLink}
                            to={getCategoryItemsUrl(item)}
                            underline="none"
                            variant="body2"
                        >
                            Посмотреть все
                        </Link>
                    </Button>
                </CardActions>
            </Card>
        </Link>
    )
}

export default CatalogItem;