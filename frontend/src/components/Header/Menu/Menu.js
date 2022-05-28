import { Grid, Link, Stack, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React, { useEffect, useState } from "react";
import MenuItem from "./MenuItem";

import "./Menu.css";


const Menu = ({ categories }) => {
    
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