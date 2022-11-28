import { Grid } from "@mui/material";
import { CategoryWithoutParentData } from "../../constants/types";
import CatalogItem from "./CatalogItem";


export interface CatalogSectionProps {
    items: CategoryWithoutParentData[];
}


const CatalogSection = ({ items }: CatalogSectionProps) => {
    return (
        <Grid 
            container 
            spacing={{ xs: 2, md: 2 }} 
            columns={{ xs: 4, sm: 8, md: 12}}
        >
            {items && items.map(item => 
                <Grid item xs={2} sm={4} key={item.id}>
                    <CatalogItem key={item.id} item={item} />
                </Grid>
            )}
        </Grid>
    )
}

export default CatalogSection;