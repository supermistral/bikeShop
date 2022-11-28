import { Box, Divider } from "@mui/material";
import { CategoryData } from "../../constants/types";
import CatalogBreadcrumb from "./CatalogBreadcrumb";
import CatalogRoutes from "./CatalogRoutes";


export interface CatalogProps {
    categories: CategoryData[] | undefined ;
}


const Catalog = ({ categories }: CatalogProps) => {
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