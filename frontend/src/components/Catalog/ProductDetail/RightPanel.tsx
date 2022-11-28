import { Box, Button, Typography } from "@mui/material";
import RadioSpecifications from "./RadioSpecifications";
import { formatPrice } from "../../../utils/product";
import CartIcon from "../../Icons/CartIcon";
import { ProductItemData, ProductItemInstanceReducedData } from "../../../constants/types";


export interface RightPanelProps {
    item: ProductItemData;
    setInstanceId: (id: number) => void;
    selectedInstance: ProductItemInstanceReducedData | undefined;
}


const RightPanel = ({ item, setInstanceId, selectedInstance }: RightPanelProps) => {
    return (
        <Box>
            <Box sx={{
                display: 'flex',
                py: 2,
                px: 1,
                backgroundColor: '#eef', '& > *': { width: '50%' }
            }}>
                <Typography variant="h4" sx={{ textAlign: 'center' }}>
                    {formatPrice(item.price)} 
                    <Typography component="span" sx={{ fontSize: '0.75em' }}> ₽</Typography>
                </Typography>
                <Box>
                    {selectedInstance && (
                        <CartIcon
                            item={selectedInstance}
                            component={Button}
                            componentProps={{
                                variant: "outlined",
                                size: "large",
                                sx: { textTransform: "none" },
                            }}
                            componentActiveProps={{
                                variant: "contained"
                            }}
                            text="Купить"
                            variant="body1"
                            sx={{ fontSize: '1.25em' }}
                        />
                    )}
                </Box>
            </Box>
            <RadioSpecifications
                setInstanceId={setInstanceId}
                item={item}
            />
        </Box>
    )
}

export default RightPanel;