import { Box, BoxTypeMap, Tab, Tabs } from "@mui/material";
import { DefaultComponentProps } from "@mui/material/OverridableComponent";
import React, { useContext, useState } from "react";
import { Navigate } from "react-router-dom";
import UserAuthContext from "../DOM/UserAuthContext";
import OrderFullList from "./OrderList/OrderFullList";
import OrderList from "./OrderList/OrderList";
import CategoryDataGrid from "./CategoryDataGrid";
import ItemDataGrid from "./ItemDataGrid";
import CategorySpecificationDataGrid from "./CategorySpecificationDataGrid";
import ItemSpecificationDataGrid from "./ItemSpecificationDataGrid";
import ItemInstanceDataGrid from "./ItemInstanceDataGrid";
import UserDataGrid from "./UserDataGrid";
import ItemInstanceSpecificationDataGrid from "./ItemInstanceSpecificationDataGrid";
import ItemInstanceImageDataGrid from "./ItemInstanceImageDataGrid";


export interface TabPanelProps extends DefaultComponentProps<BoxTypeMap> {
    children: React.ReactNode | React.ReactNode[];
    value: number;
    index: number;
}


const userTabs = [
    { label: "Мои заказы", component: <OrderList />, path: "" },
];
const managerTabs = [
    { label: "Заказы", component: <OrderFullList />, path: "orders" },
    { label: "Модификации товаров", component: <ItemInstanceDataGrid />, path: "" },
    { label: "Изображения модификаций товаров", component: <ItemInstanceImageDataGrid />, path: "imagesOfInstances"},
    { label: "Характеристики модификаций товаров", component: <ItemInstanceSpecificationDataGrid />, path: "specificationsOfInstances"},
    { label: "Товары", component: <ItemDataGrid />, path: "items" },
    { label: "Характеристики товаров", component: <ItemSpecificationDataGrid />, path: "specificationsOfItems" },
];
const adminTabs = [
    ...managerTabs,
    { label: "Категории", component: <CategoryDataGrid />, path: "categories" },
    { label: "Характеристики категорий", component: <CategorySpecificationDataGrid />, path: "categorySpecifications" },
    { label: "Пользователи", component: <UserDataGrid />, path: "users" },
];


const TabPanel = ({ children, value, index, ...props }: TabPanelProps) => {
    return (
        <Box
            sx={{
                flex: 1,
                visibility: value !== index ? 'hidden' : 'visible'
            }}
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

    const [value, setValue] = useState<number>(0);
    const [tabs, setTabs] = useState<typeof userTabs>(
        roles!.indexOf("admin") !== -1 ? adminTabs
            : roles!.indexOf("manager") !== -1 ? managerTabs
            : userTabs
    );

    const handleChange = (e: React.SyntheticEvent, newValue: number) => setValue(newValue);

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
        </Box>
    )
}

export default Profile;