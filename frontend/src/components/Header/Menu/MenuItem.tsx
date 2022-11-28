import React from "react";
import styled from "@emotion/styled";
import { Box, Grid, Typography } from "@mui/material";
import { CategoryWithoutParentData } from "../../../constants/types";
import RouteLink from "../../DOM/RouteLink";


export interface MenuItemProps {
    item: CategoryWithoutParentData;
    first?: boolean;
    styles: React.CSSProperties;
}


const Img = styled('img')({
    display: 'block',
    margin: 'auto',
    width: '100%',
    maxHeight: '100%',
    objectFit: 'cover'
});


const MenuItem = ({ item, first, styles }: MenuItemProps) => {
    const hasChild = item.children.length !== 0;

    return (
        <RouteLink 
            key={item.id}
            to={`/catalog/${item.id}/items`}
            style={styles} 
            color="inherit" 
            className="menu-item" 
            underline="none"
        >
            <Box 
                className={`menu-${hasChild ? 'multi' : 'single'}-item`} 
                sx={{ px: 2 }}
            >
                <Grid container spacing={2} sx={{ m: 0, justifyContent: 'center', flexWrap: 'nowrap' }}>
                    {item.image && !first &&
                        <div className="image-container">
                            <Img src={item.image} alt="" />
                        </div>
                    }
                    <Typography sx={{ py: 1 }} className="menu-item-text" variant="subtitle1">
                        <div className="inner-text">{item.name}</div>
                    </Typography>
                </Grid>
                {hasChild && 
                    <div className="menu-item-container">
                        {item.children.map((child, _) => 
                            <MenuItem
                                item={child}
                                styles={{
                                    flexBasis: item.children.length <= 4
                                        ? 100 / item.children.length + "%"
                                        : '25%'
                                }}
                            />
                        )}
                    </div>
                }
            </Box>
        </RouteLink>
    );
}

export default MenuItem;