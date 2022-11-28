import { List, ListItem, ListItemButton, ListItemIcon, Checkbox, ListItemText, Collapse, Button } from "@mui/material";
import React, { useEffect, useState } from "react";
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import { useSearchParams } from "react-router-dom";


export interface ListFilterProps {
    name: string;
    items: string[] | {
        id: number;
        name: string;
    }[];
    label?: string;
    collapsed?: boolean;
    searchParamKey: string;
    values?: number[];
}


const ListFilter = ({ name, items, label, collapsed, searchParamKey, values }: ListFilterProps) => {
    const checkedValues: number[] = values ? values : [...Array(items.length).keys()];

    const [checked, setChecked] = useState<number[]>([]);
    const [open, setOpen] = useState<boolean>(false);

    const [searchParams, setSearchParams] = useSearchParams();

    const handleToggle = (value: number) => () => {
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

    const getChecked = (value: number) => checked.indexOf(value) !== -1;

    useEffect(() => {
        if (checked.length === 0)
            searchParams.delete(searchParamKey)
        else
            searchParams.set(searchParamKey, checked.join(";"));
        setSearchParams(searchParams);
    }, [checked]);

    const handleClick = () => setOpen(!open);

    const list = (
        <List dense sx={{ width: '100%', bgcolor: 'background.paper' }}>
            {items.map((item, i) => {
                const id = `${name}-list-label-${i}`;

                return (
                    <ListItem
                        key={i}
                        disablePadding
                        color="text.secondary"
                    >
                        <ListItemButton 
                            onClick={handleToggle(checkedValues[i])}
                            role={undefined}
                            sx={{ p: 0 }}
                        >
                            <ListItemIcon sx={{ minWidth: 'auto' }}>
                                <Checkbox
                                    edge="start"
                                    size="small"
                                    checked={getChecked(checkedValues[i])}
                                    inputProps={{ 'aria-labelledby': id }}
                                    sx={{ py: 0 }}
                                />
                            </ListItemIcon>
                            <ListItemText 
                                id={id} 
                                primary={typeof item === "string" ? item : item.name} 
                                primaryTypographyProps={{ color: 'text.secondary' }}
                            />
                        </ListItemButton>
                    </ListItem>
                )
            })}
        </List>
    )

    return (
        collapsed ? (
            <>
                <Button
                    endIcon={open ? <ExpandLess /> : <ExpandMore />}
                    onClick={handleClick}
                    color="inherit"
                    fullWidth={true}
                    sx={{
                        display: 'flex',
                        justifyContent: 'space-between',
                        textTransform: 'none',
                        py: 0.5,
                    }}
                >
                    {label}
                </Button>
                <Collapse in={open} timeout="auto" unmountOnExit>
                    {list}
                </Collapse>
            </>
            
        ) : list
    )
}

export default ListFilter;