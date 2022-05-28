import React from "react";
import DataGrid, { defaultManyToOneOptionsField } from "./DataGrid/DataGrid";


const getCols = ({ items }) => [
    {
        field: "id",
        headerName: "Id",
        width: 50,
        type: 'number',
    },
    {
        field: "item",
        headerName: "Товар",
        width: 200,
        type: 'singleSelect',
        editable: true,
        ...defaultManyToOneOptionsField(items)
    },
    {
        field: "image",
        headerName: "Изображение",
        width: 150,
        editable: false,
        renderCell: params => <img src={params.value} style={{ maxWidth: '100%', height: '100%' }} />
    },
    {
        field: "stock",
        headerName: "Количество",
        width: 100,
        type: 'number',
        editable: true,
    }
];

const initialRow = {
    id: "",
    item: "",
    image: "",
    stock: 0,
};

const getRows = data => data.map(item => ({
    ...item,
    item: item.item.id,
}));

const getRequestBody = ({ item, data }) => ({
    itemId: item.item,
    stock: item.stock
});


const ItemInstanceDataGrid = () => {
    const getColumns = item => getCols({ 
        items: item.items,
    });

    return (
        <DataGrid
            url="itemInstances"
            getCols={getColumns}
            getRows={getRows}
            initialRow={initialRow}
            getRequestBody={getRequestBody}
            responseKey={"itemInstances"}
        />
    )
}

export default ItemInstanceDataGrid;