import { Box, List, ListItem, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import axiosInstance from "../../../constants/axios";
import Order from "./Order";


const OrderList = ({ isAdmin }) => {
    const [orders, setOrders] = useState();
    
    useEffect(() => {
        axiosInstance
            .get(isAdmin ? "orders/all" : "orders")
            .then(res => setOrders(res.data))
            .catch(err => console.log(err));
    
    }, []);
    
    return (
        <Box sx={{ px: 1 }}>
            {orders ? (
                <List spacing={2}>
                    {orders.map((order, i) =>
                        <ListItem key={i}>
                            <Order order={order} isAdmin={isAdmin} />
                        </ListItem>
                    )}
                </List>
            ) : (
                <Typography variant="h6">
                    {isAdmin 
                        ? "Заказов еще не поступало"
                        : "Вы еще не сделали ни одного заказа"
                    }
                </Typography>
            )}
        </Box>
    )
}

export default OrderList;