import React, { Fragment } from "react";
import { Typography, Box, Divider } from "@mui/material";


const ProductSpecifications = ({ item }) => {
    return (
        <Box sx={{ p: 0.5 }}>
            <Typography variant="subtitle2" sx={{ py: 2, lineHeight: 1.4, fontSize: '0.9em' }}>{item.description}</Typography>
            <Box sx={{ p: 1, border: '1px solid #aaa' }}>
                {item.specifications.map((specification, i) =>
                    <Fragment key={i}>
                        <Box sx={{ 
                            display: 'flex',
                            width: '100%',
                            py: 0.5
                        }}>
                            <Typography variant="body2" sx={{ width: '30%' }}>{specification.key}</Typography>
                            <Typography variant="body2" sx={{ width: '70%' }}>{specification.value}</Typography>
                        </Box>
                        {i !== item.specifications.length - 1 && <Divider />}
                    </Fragment>
                )}
            </Box>
        </Box>
    )
}

export default ProductSpecifications;