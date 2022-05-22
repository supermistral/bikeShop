import { Box, Typography, Divider } from "@mui/material";
import React from "react";
import CatalogBreadcrumb from "./CatalogBreadcrumb";
import CatalogRoutes from "./CatalogRoutes";


const Catalog = ({ categories }) => {
    return (
        <Box>
            {categories &&
                <>
                    <CatalogBreadcrumb items={categories} />
                    <Divider sx={{ mb: 2 }} />
                    <CatalogRoutes items={categories} />
                </>
            }
        </Box>
    )
}

export default Catalog;