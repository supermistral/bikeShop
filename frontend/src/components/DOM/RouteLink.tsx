import { Link } from "@mui/material";
import React from "react";
import { Link as RouterDomLink, LinkProps } from "react-router-dom";
import { AnyProps } from "../../constants/types";


export interface RouteLinkProps extends AnyProps {}


const RouteLink = (props: RouteLinkProps) => (
    <Link
        component={RouterDomLink as React.ElementType<LinkProps>}
        underline="hover"
        variant="body2"
        {...props}
    />
)

export default RouteLink;