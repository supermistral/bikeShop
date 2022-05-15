import { Box, Typography, Divider } from "@mui/material";
import React from "react";
import CatalogBreadcrumb from "./CatalogBreadcrumb";
import CatalogRoutes from "./CatalogRoutes";


const Catalog = ({ categories }) => {
    return (
        <>
            <Box>
                <Typography variant="h4" sx={{ py: 2 }}>Каталог</Typography>
                <Divider />
                {categories &&
                    <>
                        <CatalogBreadcrumb items={categories} />
                        <CatalogRoutes items={categories} />
                    </>
                }
            </Box>
        </>
        
    )
}

export default Catalog;