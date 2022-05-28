import React from "react";
import DataGrid from "./DataGrid/DataGrid";


const getCols = ({ roleOptions }) => [
    {
        field: "id",
        headerName: "Id",
        width: 50,
        type: 'number',
    },
    {
        field: "email",
        headerName: "E-mail",
        width: 150,
        editable: true,
    },
    {
        field: "name",
        headerName: "Имя",
        width: 150,
        editable: true,
    },
    {
        field: "role",
        headerName: "Права",
        width: 150,
        type: 'singleSelect',
        valueOptions: roleOptions,
        editable: true,
    },
    {
        field: "password",
        headerName: "Пароль",
        width: 150,
        editable: true,
    },
];

const initialRow = {
    id: null,
    email: "",
    name: "",
    role: "ROLE_MANAGER",
    password: "",
};

const getRows = data => data.map(item => ({
    ...item,
}));

const getRequestBody = ({ item, data }) => ({
    email: item.email,
    name: item.name,
    role: item.role,
    password: item.password,
});


const UserDataGrid = () => {
    const getColumns = item => getCols({ 
        roleOptions: item.roles.map(item => item.name)
    });

    return (
        <DataGrid
            url="users"
            getCols={getColumns}
            getRows={getRows}
            initialRow={initialRow}
            getRequestBody={getRequestBody}
            responseKey={"users"}
            isCellEditable={params => params.row.role !== "ROLE_USER"}
        />
    )
}

export default UserDataGrid;