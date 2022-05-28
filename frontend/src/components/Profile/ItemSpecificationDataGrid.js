import React from "react";
import DataGrid, { defaultManyToOneOptionsField } from "./DataGrid/DataGrid";


const getCols = ({ categorySpecifications, items, valueEditable }) => [
    {
        field: "id",
        headerName: "Id",
        width: 50,
        type: 'number',
    },
    {
        field: "categorySpecification",
        headerName: "Характеристики категории",
        width: 200,
        type: 'singleSelect',
        editable: true,
        ...defaultManyToOneOptionsField(categorySpecifications),
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
        field: "valueChoice",
        headerName: "Значение с выбором",
        width: 150,
        type: 'singleSelect',
        valueOptions: (params) => {
            const categorySpecification = categorySpecifications.find(c => 
                c.id === params.row.categorySpecification);

            if (categorySpecification && categorySpecification.choices) {
                const choices = categorySpecification.choices.split(";");
                return choices;
            }

            return [''];
        },
        editable: true,
    },
    {
        field: "value",
        headerName: "Значение",
        width: 150,
        editable: true,
    },
];

const initialRow = {
    id: null,
    categorySpecification: null,
    item: null,
    valueChoice: "",
    value: "",
};

const getRows = data => data.map(item => ({
    ...item,
    categorySpecification: item.categorySpecification?.id,
    item: item.item?.id,
    value: item.categorySpecification.choices ? "" : item.value,
    valueChoice: item.categorySpecification.choices ? item.value : "",
}));

const getRequestBody = ({ item, data }) => ({
    categorySpecificationId: item.categorySpecification,
    itemId: item.item,
    value: item.valueChoice || item.value,
});


const ItemSpecificationDataGrid = () => {
    const getColumns = item => getCols({
        items: item.items,
        categorySpecifications: item.categorySpecifications,
    });

    return (
        <DataGrid
            url="itemSpecifications"
            getCols={getColumns}
            getRows={getRows}
            initialRow={initialRow}
            getRequestBody={getRequestBody}
            responseKey={"itemSpecifications"}
        />
    )
}

export default ItemSpecificationDataGrid;