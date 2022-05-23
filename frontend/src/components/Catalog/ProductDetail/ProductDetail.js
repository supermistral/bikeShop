import { Box, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";
import ProductSlider from "./ProductSlider";
import { getItemByIdApiUrl } from "../../../constants/requests";

import "./ProductDetail.css";
import ProductSpecifications from "./ProductSpecifications";
import RightPanel from "./RightPanel";


const ProductDetail = () => {
    const { itemId } = useParams();
    const [item, setItem] = useState();
    const [instanceId, setInstanceId] = useState();

    const [searchParams, setSearchParams] = useSearchParams();

    useEffect(() => {
        fetch(getItemByIdApiUrl(itemId))
            .then(res => res.json())
            .then(data => setItem(data))
            .catch(e => console.log(e));

    }, [itemId]);

    // useEffect(() => {
    //     const currentInstanceIdString = searchParams.get("instance");
    //     const colorIndexString = searchParams.get("color");
    //     if (colorIndexString) {
    //         const colorIndex = +colorIndexString;

    //         if (isNaN(colorIndex))
    //             return;

    //         setInstanceIndex(prev => {
    //             const foundIndex = item?.instances.findIndex(instance => instance.id === currentInstanceId);
    //             if (foundIndex >= 0)
    //                 return foundIndex;
    //             return prev;
    //         });
    //     }
    // }, [searchParams, item]);

    const selectedInstance = item?.instances.find(i => i.id === instanceId) ||
        item?.instances[0];

    return (
        <Box>
            {item &&
                <>
                    <Typography variant="h4">{item.name}</Typography>
                    <Box sx={{ display: 'flex', '& > *': { width: '50%' } }}>
                        <Box>
                            <ProductSlider images={selectedInstance.images} />
                            <ProductSpecifications item={item} />
                        </Box>
                        <Box>
                            <RightPanel
                                item={item}
                                setInstanceId={setInstanceId}
                                selectedInstance={selectedInstance}
                            />
                        </Box>
                    </Box>
                    
                </>
            }
        </Box>
    )
}

export default ProductDetail;