import { AnyProps, DataGridColData } from "../../constants/types";
import DataGrid, {
    ColsGetter, defaultManyToOneOptionsField, RequestBodyGetter, RowsGetter
} from "./DataGrid/DataGrid";


type ColsGetterProps = {
    categories: AnyProps[];
}


const getCols = ({ categories }: ColsGetterProps): DataGridColData[] => [
    {
        field: "id",
        headerName: "Id",
        width: 50,
        type: 'number',
    },
    {
        field: "category",
        headerName: "Категория",
        width: 200,
        type: 'singleSelect',
        editable: true,
        ...defaultManyToOneOptionsField(categories),
    },
    {
        field: "name",
        headerName: "Название",
        width: 200,
        editable: true,
    },
    {
        field: "price",
        headerName: "Цена",
        width: 150,
        type: "number",
        editable: true,
    },
    {
        field: "description",
        headerName: "Описание",
        width: 300,
        editable: true,
    }
];

const initialRow = {
    id: null,
    category: null,
    name: "",
    price: 0,
    description: ""
};

const getRows: RowsGetter = (data) => data.map(item => ({
    ...item,
    category: item.category?.id
}));

const getRequestBody: RequestBodyGetter = ({ item }) => ({
    categoryId: item.category,
    name: item.name,
    price: item.price,
    description: item.description,
});


const ItemDataGrid = () => {
    const getColumns: ColsGetter<AnyProps> = (item) => getCols({ 
        categories: item.categories,
    });

    return (
        <DataGrid
            url="items"
            getCols={getColumns}
            getRows={getRows}
            initialRow={initialRow}
            getRequestBody={getRequestBody}
            responseKey={"items"}
        />
    )
}

export default ItemDataGrid;