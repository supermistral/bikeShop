import { Box, Tab, Tabs, Typography } from "@mui/material";
import React, { useContext, useState } from "react";
import { Navigate, Outlet, Route, Routes } from "react-router-dom";
import UserAuthContext from "../DOM/UserAuthContext";
import OrderFullList from "./OrderList/OrderFullList";
import OrderList from "./OrderList/OrderList";
import CategoryDataGrid from "./CategoryDataGrid";
import RouteLink from "../DOM/RouteLink";
import ItemDataGrid from "./ItemDataGrid";
import CategorySpecificationDataGrid from "./CategorySpecificationDataGrid";
import ItemSpecificationDataGrid from "./ItemSpecificationDataGrid";
import ItemInstanceDataGrid from "./ItemInstanceDataGrid";
import UserDataGrid from "./UserDataGrid";
import ItemInstanceSpecificationDataGrid from "./ItemInstanceSpecificationDataGrid";
import ItemInstanceImageDataGrid from "./ItemInstanceImageDataGrid";


const userTabs = [
    { label: "Мои заказы", component: <OrderList />, path: "" },
];
const managerTabs = [
    { label: "Заказы", component: <OrderFullList />, path: "orders" },
    { label: "Модификации товаров", component: <ItemInstanceDataGrid />, path: "" },
    { label: "Изображения модификаций товаров", component: <ItemInstanceImageDataGrid />, },
    { label: "Характеристики модификаций товаров", component: <ItemInstanceSpecificationDataGrid /> },
    { label: "Товары", component: <ItemDataGrid />, path: "items" },
    { label: "Характеристики товаров", component: <ItemSpecificationDataGrid />, path: "" },
];
const adminTabs = [
    ...managerTabs,
    { label: "Категории", component: <CategoryDataGrid />, path: "categories" },
    { label: "Характеристики категорий", component: <CategorySpecificationDataGrid />, path: "categorySpecifications" },
    { label: "Пользователи", component: <UserDataGrid />, path: "users" },
];


const TabPanel = ({ children, value, index, ...props }) => {
    return (
        <Box
            rol="tabpanel"
            hidden={value !== index}
            id={`profile-tabpanel-${index}`}
            aria-labelledby={`profile-tab-${index}`}
            sx={{ flex: 1 }}
            {...props}
        >
            {value === index &&
                <Box sx={{ p: 1.5 }}>
                    {children}
                </Box>
            }
        </Box>
    )
}


const Profile = () => {
    const { roles, isAuthorized } = useContext(UserAuthContext);

    if (!isAuthorized) {
        return <Navigate to="/" />
    }

    const [value, setValue] = useState(0);
    const [tabs, setTabs] = useState(
        roles.indexOf("admin") !== -1 ? adminTabs
            : roles.indexOf("manager") !== -1 ? managerTabs
            : userTabs
    );

    const handleChange = (event, newValue) => setValue(newValue);

    return (
        <Box sx={{ my: 2, flexGrow: 1, bgcolor: 'background.paper', display: 'flex' }}>
            <Tabs
                orientation="vertical"
                variant="scrollable"
                value={value}
                onChange={handleChange}
                aria-label="Profile"
                sx={{ 
                    borderRight: 1,
                    borderColor: 'divider',
                    bgcolor: '#eee',
                    maxWidth: '25%',
                    minWidth: '200px',
                }}
            >
                {tabs.map((tab, i) =>
                    <Tab 
                        label={tab.label} 
                        id={`profile-tab-${i}`} 
                        aria-controls={`profile-tabpanel-${i}`} 
                        sx={{ textTransform: 'none', fontSize: '1em' }}
                    />
                    
                )}
            </Tabs>
            {tabs.map((tab, i) => 
                <TabPanel index={i} value={value}>
                    {tab.component}
                </TabPanel>
            )}
            {/* <Outlet />
            <Routes>
                {tabs.map((tab, i) =>
                    <Route 
                        key={i} 
                        path={tab.path} 
                        element={<TabPanel index={i} value={value} >{tab.component}</TabPanel>} 
                    />
                )}
            </Routes> */}
        </Box>
    )
}

export default Profile;