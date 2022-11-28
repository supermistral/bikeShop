import { Box } from "@mui/material";
import React from "react";
import ModeButton from "./ModeButton";
import SortingButton from "./SortingButton";


const ProductTopBar = () => {
    return (
        <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
            <SortingButton />
            <ModeButton />
        </Box>
    )
}

export default ProductTopBar;