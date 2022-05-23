import { Box, Button, IconButton, Typography } from "@mui/material";
import React from "react";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import RadioSpecifications from "./RadioSpecifications";
import { formatPrice } from "../../../utils/product";
import CartIcon from "../../Icons/CartIcon";


const RightPanel = ({ item, setInstanceId, selectedInstance }) => {
    return (
        <Box>
            <Box sx={{
                display: 'flex',
                py: 2,
                px: 1,
                backgroundColor: '#eef', '& > *': { width: '50%' }
            }}>
                <Typography variant="h4" sx={{ textAlign: 'center' }}>
                    {formatPrice(item.price)} 
                    <Typography component="span" sx={{ fontSize: '0.75em' }}> ₽</Typography>
                </Typography>
                <Box>
                    <CartIcon
                        item={selectedInstance}
                        component={Button}
                        componentProps={{
                            variant: "outlined",
                            size: "large",
                            sx: { textTransform: "none" },
                        }}
                        componentActiveProps={{
                            variant: "contained"
                        }}
                        text="Купить"
                        variant="body1"
                        sx={{ fontSize: '1.25em' }}
                    />
                </Box>
            </Box>
            <RadioSpecifications
                instances={item.instances}
                setInstanceId={setInstanceId}
                item={item}
            />
        </Box>
    )
}

export default RightPanel;