import { Card, CardMedia, Stack, Typography } from "@mui/material";
import { makeStyles } from "@mui/styles"
import React from "react";
import banner from "../../media/home-banner.jpg"


const useStyles = makeStyles(theme => ({
    root: {
        position: "relative"
    },
    text: {
        position: "absolute",
        top: '10%',
        left: '5%',
        width: '40%',
        color: '#fff'
    }
}))


const UpperBanner = () => {
    const classes = useStyles();

    return (
        <Card className={classes.root}>
            <CardMedia
                component="img"
                image={banner}
                alt="Banner"
                sx={{ maxHeight: "500px" }}
            />
            <Typography 
                variant="p" 
                className={classes.text}
            >
                <Typography variant="h2" sx={{ fontSize: "50px" }}>Lorem ipsum</Typography>
                <Typography variant="h5">Lorem ipsum dolor sit amet, consectetur adipiscing elit</Typography>
            </Typography>
        </Card>
    )
}

export default UpperBanner;