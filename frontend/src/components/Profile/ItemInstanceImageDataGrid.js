import React, { useEffect, useState } from "react";
import DataGrid, { defaultManyToOneOptionsField } from "./DataGrid/DataGrid";
import ImageField from "./DataGrid/ImageField";


const getCols = ({ itemInstances, imageUrls, images, handleChange, handleClose }) => [
    {
        field: "id",
        headerName: "Id",
        width: 50,
        type: 'number',
    },
    {
        field: "itemInstance",
        headerName: "Модификация товара",
        width: 200,
        type: 'singleSelect',
        editable: true,
        ...defaultManyToOneOptionsField(itemInstances),
    },
    {
        field: "image",
        headerName: "Изображение",
        width: 250,
        editable: true,
        getValue: params => {
            return images[params.id] ? images[params.id].name : params.value
        },
        renderEditCell: params => (
            <ImageField 
                params={params}
                imageUrl={imageUrls[params.id]}
                currentImage={params.row.image} 
                handleChange={handleChange(params.id)}
                handleClose={handleClose(params.id)}
            />
        ),
        renderCell: params => (
            <ImageField 
                params={params}
                imageUrl={imageUrls[params.id]}
                currentImage={params.value}
                disabled={true}
            />
        )
    }
];

const initialRow = {
    id: "",
    itemInstance: "",
    image: ""
};

const getRows = data => data.map(item => ({
    ...item,
    itemInstance: item.itemInstance?.id
}));


const ItemInstanceImageDataGrid = () => {
    const [image, setImage] = useState({});
    const [getColumns, setGetColumns] = useState(() => item => getCols({
        itemInstances: item.itemInstances,
    }));
    
    const handleChange = id => e =>
        setImage(prev => ({ ...prev, [id]: e.target.files[0] }));

    const handleClose = id => () =>
        setImage(prev => ({ ...prev, [id]: null }));

    useEffect(() => {
        const imageUrls = {}
        Object.keys(image)
            .filter(id => image[id])
            .forEach(id => imageUrls[id] = URL.createObjectURL(image[id]));
        
        setGetColumns(() => item => getCols({
            itemInstances: item.itemInstances,
            images: image,
            imageUrls: imageUrls,
            handleChange: handleChange,
            handleClose
        }));
    }, [image]);

    const getRequestBody = ({ item, data, rowId }) => ({
        itemInstanceId: item.itemInstance,
        image: image[rowId]?.name || item.image,
        imageData: image[rowId]
    });

    return (
        <DataGrid 
            url={"itemInstances/images"}
            getCols={getColumns} 
            initialRow={initialRow}
            getRows={getRows}
            getRequestBody={getRequestBody}
            responseKey={"images"}
        />
    )
}

export default ItemInstanceImageDataGrid;