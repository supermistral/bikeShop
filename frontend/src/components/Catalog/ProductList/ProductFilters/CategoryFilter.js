import { Checkbox, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import ListFilter from "./ListFilter";


const buildCategoriesAtLastChildLevel = (category, categoriesArr = []) => {
    if (category.children.length === 0)
        categoriesArr.push(category);

    for (const childCategory of category.children) {
        buildCategoriesAtLastChildLevel(childCategory, categoriesArr);
    }

    return categoriesArr;
}


const CategoryFilter = ({ category }) => {
    const categories = category.lastChildren;
    const [checked, setChecked] = useState([]);

    const handleToggle = value => () => {
        setChecked(prev => {
            const currentIndex = prev.indexOf(value);
            const newChecked = [...prev];
            if (currentIndex === -1)
                newChecked.push(value);
            else
                newChecked.splice(currentIndex, 1);
            return newChecked;
        });
    }

    useEffect(() => {
        // Action: create new url params
    }, [checked])

    return ListFilter({ name: 'category', items: category.lastChildren });

    return (
        <List dense sx={{ width: '100%', bgcolor: 'background.paper' }}>
            {categories.map((item, i) => 
                <ListItem
                    key={i}
                    disablePadding
                    color="text.secondary"
                >
                    <ListItemButton 
                        onClick={handleToggle(i)}
                        role={undefined}
                        sx={{ p: 0 }}
                    >
                        <ListItemIcon sx={{ minWidth: 'auto' }}>
                            <Checkbox
                                edge="start"
                                size="small"
                                checked={checked.indexOf(i) !== -1}
                                inputProps={{ 'aria-labelledby': `category-list-label-${i}` }}
                                sx={{ py: 0 }}
                            />
                        </ListItemIcon>
                        <ListItemText 
                            id={`category-list-label-${i}`} 
                            primary={item.name} 
                            primaryTypographyProps={{ color: 'text.secondary' }}
                        />
                    </ListItemButton>
                </ListItem>
            )}
        </List>
    )
}

export default CategoryFilter;