import { Box, Button, FormControl, MenuItem, Select } from "@mui/material";
import React, { useState } from "react";


const SORTING_KEY = 'sorting';

const sortingItems = [
    { value: 'price_asc', name : 'Сначала дешевле' },
    { value: 'price_desc', name: 'Сначала дороже' },
]


const SortingButton = () => {
    const [sorting, setSorting] = useState(sortingItems[0].value);
    const [open, setOpen] = useState(false);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const handleChange = (e) => setSorting(e.target.value);

    return (
        <Box sx={{ display: 'flex' }}>
            <Button sx={{ display: 'block', mr: 1 }} size="small" onClick={handleOpen}>
                Сортировать
            </Button>
            <FormControl size="small">
                <Select
                    labelId="product-sorting-label"
                    id="product-sorting-select"
                    open={open}
                    onOpen={handleOpen}
                    onClose={handleClose}
                    onChange={handleChange}
                    value={sorting}
                >
                    {sortingItems.map((item, i) => 
                        <MenuItem key={i} value={item.value}>{item.name}</MenuItem>
                    )}
                </Select>
            </FormControl>
        </Box>
    )
}

export default SortingButton;