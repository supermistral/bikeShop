import { AnyProps, DataGridColData } from "../../constants/types";
import DataGrid, { ColsGetter, IsCellEditableGetter, RequestBodyGetter, RowsGetter } from "./DataGrid/DataGrid";


type ColsGetterProps = {
    roleOptions: string[]
}


const getCols = ({ roleOptions }: ColsGetterProps): DataGridColData[] => [
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

const getRows: RowsGetter = data => data.map(item => ({
    ...item,
}));

const getRequestBody: RequestBodyGetter = ({ item }) => ({
    email: item.email,
    name: item.name,
    role: item.role,
    password: item.password,
});

const getIsCellEditable: IsCellEditableGetter = () => params => params.row.role !== "ROLE_USER";


const UserDataGrid = () => {
    const getColumns: ColsGetter<AnyProps> = item => getCols({ 
        roleOptions: (item.roles as AnyProps[]).map(item => item.name)
    });

    return (
        <DataGrid
            url="users"
            getCols={getColumns}
            getRows={getRows}
            initialRow={initialRow}
            getRequestBody={getRequestBody}
            responseKey={"users"}
            getIsCellEditable={getIsCellEditable}
        />
    )
}

export default UserDataGrid;