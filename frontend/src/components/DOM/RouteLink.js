import { Link } from "@mui/material";
import React from "react";
import { Link as RouterDomLink } from "react-router-dom";


const RouteLink = (props) => (
    <Link component={RouterDomLink} {...props} />
)

export default RouteLink;