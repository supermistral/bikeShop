import { Box, Stack, Typography } from "@mui/material";
import SearchBar from "./SearchBar";
import Options from "./Options";
import Menu from "./Menu/Menu";
import RouteLink from "../DOM/RouteLink";
import { CategoryData } from "../../constants/types";


export interface HeaderProps {
    categories?: CategoryData[];
}


const Header = ({ categories }: HeaderProps) => {
    return (
        <Box>
            <Stack 
                direction="row"
                spacing={2}
                sx={{ justifyContent: 'center', py: 2 }}
            >
                <RouteLink to="/" color="inherit">
                    <Typography sx={{ fontSize: "30px" }}>
                        Bike Shop
                    </Typography>
                </RouteLink>
                <SearchBar />
                <Options />
            </Stack>
            <Menu categories={categories} />
        </Box>
        
    )
}

export default Header