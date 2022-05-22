import { Box, Button, IconButton, Typography } from "@mui/material";
import React from "react";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import RadioSpecifications from "./RadioSpecifications";


const RightPanel = ({ item, setInstanceId }) => {
    return (
        <Box>
            <Box sx={{
                display: 'flex',
                py: 2,
                px: 1,
                backgroundColor: '#eef', '& > *': { width: '50%' }
            }}>
                <Typography variant="h4" sx={{ textAlign: 'center' }}>
                    {item.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ")} 
                    <Typography component="span" sx={{ fontSize: '0.75em' }}> ₽</Typography>
                </Typography>
                <Box>
                    <Button variant="outlined" size="large" sx={{ textTransform: 'none' }}>
                        <ShoppingCartOutlinedIcon />
                        <Typography
                            variant="body1"
                            sx={{ ml: 1, fontSize: '1.25em' }}
                        >
                            Купить
                        </Typography>
                    </Button>
                </Box>
            </Box>
            <RadioSpecifications
                instances={item.instances}
                setInstanceId={setInstanceId}
            />
        </Box>
    )
}

export default RightPanel;