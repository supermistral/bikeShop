import React, { useEffect, useState } from "react";
import DataGrid, { defaultManyToOneOptionsField } from "./DataGrid/DataGrid";
import ImageField from "./DataGrid/ImageField";


const getCols = ({ parents, imageUrls, images, handleChange, handleClose }) => [
    {
        field: "id",
        headerName: "Id",
        width: 50,
        type: 'number',
    },
    {
        field: "parent",
        headerName: "Родитель",
        width: 200,
        type: 'singleSelect',
        editable: true,
        ...defaultManyToOneOptionsField(parents),
    },
    {
        field: "name",
        headerName: "Название",
        width: 200,
        editable: true,
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
    parent: "",
    name: "",
    image: ""
};

const getRows = data => data.map(item => ({
    ...item,
    parent: item.parent?.id || "",
}));


const CategoryDataGrid = () => {
    const [image, setImage] = useState({});
    const [getColumns, setGetColumns] = useState(() => items => getCols({
        parentOptions: items.map(item => item.name),
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
        
        setGetColumns(() => items => getCols({
            parents: [
                { id: "", name: "" },
                ...items.map(item => ({ id: item.id, name: item.name })),
            ],
            images: image,
            imageUrls: imageUrls,
            handleChange: handleChange,
            handleClose
        }));
    }, [image]);

    const getRequestBody = ({ item, data, rowId }) => ({
        parentId: item.parent || null,
        name: item.name,
        image: image[rowId]?.name || item.image,
        imageData: image[rowId]
    });

    return (
        <DataGrid 
            url={"category/all"}
            getCols={getColumns} 
            initialRow={initialRow}
            getRows={getRows}
            getRequestBody={getRequestBody}
            urlUpdate={"category"}
            urlCreate={"category"}
            urlDelete={"category"}
        />
    )
}

export default CategoryDataGrid;