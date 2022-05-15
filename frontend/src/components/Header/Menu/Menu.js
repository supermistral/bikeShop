import { Grid, Link, Stack, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React, { useEffect, useState } from "react";
import MenuItem from "./MenuItem";

import "./Menu.css";


const Menu = ({ categories }) => {
    // const [categories, setCategories] = useState();

    // useEffect(() => {
    //     fetch("/api/category")
    //         .then(res => res.json())
    //         .then(data => setCategories(data))
    //         .catch(e => console.log(e));
    // }, []);
    
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
                    style={{
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