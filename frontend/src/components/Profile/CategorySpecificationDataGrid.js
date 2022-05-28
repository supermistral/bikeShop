import React from "react";
import DataGrid, { defaultManyToOneOptionsField } from "./DataGrid/DataGrid";


const getCols = ({ categories }) => [
    {
        field: "id",
        headerName: "Id",
        width: 50,
        type: 'number',
    },
    {
        field: "category",
        headerName: "Категория",
        width: 150,
        type: 'singleSelect',
        editable: true,
        ...defaultManyToOneOptionsField(categories),
    },
    {
        field: "name",
        headerName: "Название",
        width: 150,
        editable: true,
    },
    {
        field: "choices",
        headerName: "Возможные варианты",
        helperText: "Возможные варианты через ';' (опционально)",
        width: 150,
        editable: true,
    }
];

const initialRow = {
    id: null,
    category: null,
    name: "",
    choices: "",
};

const getRows = data => data.map(item => ({
    ...item,
    category: item.category.id,
}));

const getRequestBody = ({ item, data }) => ({
    categoryId: item.category,
    name: item.name,
    choices: item.choices || null,
});


const CategorySpecificationDataGrid = () => {
    const getColumns = item => getCols({ 
        categories: item.categories,
    });

    return (
        <DataGrid
            url="categorySpecifications"
            getCols={getColumns}
            getRows={getRows}
            initialRow={initialRow}
            getRequestBody={getRequestBody}
            responseKey={"categorySpecifications"}
        />
    )
}

export default CategorySpecificationDataGrid;