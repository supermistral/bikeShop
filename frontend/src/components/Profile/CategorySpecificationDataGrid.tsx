import { AnyProps, DataGridColData } from "../../constants/types";
import DataGrid, { ColsGetter, defaultManyToOneOptionsField, RequestBodyGetter, RowsGetter } from "./DataGrid/DataGrid";


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

const getRows: RowsGetter = (data) => data.map(item => ({
    ...item,
    category: item.category.id,
}));

const getRequestBody: RequestBodyGetter = ({ item }) => ({
    categoryId: item.category,
    name: item.name,
    choices: item.choices || null,
});


const CategorySpecificationDataGrid = () => {
    const getColumns: ColsGetter<AnyProps> = (item) => getCols({ 
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