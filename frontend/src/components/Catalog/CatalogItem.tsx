import { Box, Button, Card, CardActionArea, CardActions, CardContent, CardMedia, Link, Typography } from "@mui/material";
import { Link as RouteLink } from "react-router-dom";
import { CategoryWithoutParentData } from "../../constants/types";


export interface CatalogItemProps {
    item: CategoryWithoutParentData;
}


const getCategoryUrl = (item: CategoryWithoutParentData) => `/catalog/${item.id}`;
const getCategoryItemsUrl = (item: CategoryWithoutParentData) => `/catalog/${item.id}/items`;

const getCategoryLinkUrl = (item: CategoryWithoutParentData) => item.children.length !== 0 
    ? getCategoryUrl(item)
    : getCategoryItemsUrl(item);


const CatalogItem = ({ item }: CatalogItemProps) => {
    return (
        <Link component={RouteLink} to={getCategoryLinkUrl(item)} underline="none">
            <Card sx={{ maxWidth: 345 }}>
                <CardActionArea>
                    <CardMedia
                        component="img"
                        height="140"
                        image={item.image || undefined}
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