import { Box, List, ListItem, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import axiosInstance from "../../../constants/axios";
import { OrderData } from "../../../constants/types";
import Order from "./Order";


export interface OrderListProps {
    isAdmin?: boolean;
}


const OrderList = ({ isAdmin }: OrderListProps) => {
    const [orders, setOrders] = useState<OrderData[]>();
    
    useEffect(() => {
        axiosInstance
            .get("orders")
            .then(res => setOrders(res.data))
            .catch(err => console.log(err));
    }, []);
    
    return (
        <Box sx={{ px: 1 }}>
            {orders ? (
                <List sx={{ margin: '1em 0' }}>
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