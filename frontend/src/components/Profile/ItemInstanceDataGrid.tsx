import { AnyProps, DataGridColData } from "../../constants/types";
import DataGrid, { ColsGetter, defaultManyToOneOptionsField, RequestBodyGetter, RowsGetter } from "./DataGrid/DataGrid";


type ColsGetterProps = {
    items: AnyProps[];
}


const getCols = ({ items }: ColsGetterProps): DataGridColData[] => [
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

const getRows: RowsGetter = (data) => data.map(item => ({
    ...item,
    item: item.item.id,
}));

const getRequestBody: RequestBodyGetter = ({ item }) => ({
    itemId: item.item,
    stock: item.stock
});


const ItemInstanceDataGrid = () => {
    const getColumns: ColsGetter<AnyProps> = (item) => getCols({ 
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