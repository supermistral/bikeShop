import { Stack } from "@mui/material";
import MenuItem from "./MenuItem";

import "./Menu.css";
import { CategoryData } from "../../../constants/types";


export interface MenuProps {
    categories?: CategoryData[];
}


const Menu = ({ categories }: MenuProps) => {
    
    return (
        <Stack 
            direction="row"
            spacing={1}
            className="menu"
            sx={{ 
                backgroundColor: "#eee",
                justifyContent: "space-around",
            }}
        >
            {categories && categories.map(item => 
                <MenuItem 
                    item={item} 
                    first={true} 
                    styles={{
                        flexBasis: categories 
                            ? 100 / categories.length + "%" 
                            : "none"
                        }} 
                />
            )}
        </Stack>
    )
}

export default Menu;