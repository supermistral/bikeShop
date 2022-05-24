import { Link } from "@mui/material";
import React from "react";
import { Link as RouterDomLink } from "react-router-dom";


const RouteLink = (props) => (
    <Link 
        component={RouterDomLink}
        {...{
            underline: 'hover',
            variant: 'body2',
        }}
        {...props} 
    />
)

export default RouteLink;