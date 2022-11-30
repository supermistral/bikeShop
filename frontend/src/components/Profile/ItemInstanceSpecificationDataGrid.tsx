import { AnyProps, DataGridColData } from "../../constants/types";
import DataGrid, { ColsGetter, IsCellEditableGetter, RequestBodyGetter, RowsGetter } from "./DataGrid/DataGrid";


type ColsGetterProps = {
    itemInstances: AnyProps[];
    categorySpecifications: AnyProps[];
}


const getCols = ({ 
    itemInstances, 
    categorySpecifications,
}: ColsGetterProps): DataGridColData[] => [
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
        valueOptions: itemInstances.map(i => i.id),
        valueFormatter: ({ value }) => {
            const itemInstance = itemInstances.find(i => i.id === value);
            if (itemInstance)
                return `${itemInstance.id} - ${itemInstance.name}`;
            return '';
        } ,
        editable: true,
    },
    {
        field: "categorySpecification",
        headerName: "Характеристика категории",
        width: 200,
        type: 'singleSelect',
        valueOptions: categorySpecifications.map(i => i.id),
        valueFormatter: ({ value }) => categorySpecifications.find(i => i.id === value)?.name,
        editable: true,
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
    id: "",
    itemSpecification: "",
    categorySpecification: "",
    valueChoice: "",
    value: "",
};

const getRows: RowsGetter = data => data.map(item => ({
    ...item,
    itemInstance: item.itemInstance.id,
    categorySpecification: item.categorySpecification.id,
    value: item.choice ? "" : item.value,
    valueChoice: item.choice ? item.value : "",
}));

const getRequestBody: RequestBodyGetter = ({ item }) => ({
    itemInstanceId: item.itemInstance,
    categorySpecificationId: item.categorySpecification,
    value: item.valueChoice || item.value,
});


const ItemInstanceSpecificationDataGrid = () => {
    const getColumns: ColsGetter<AnyProps> = item => getCols({ 
        itemInstances: (item.itemInstances as AnyProps[]).map(i => ({ id: i.id, name: i.item.name })),
        categorySpecifications: item.categorySpecifications,
    });

    const getIsCellEditable: IsCellEditableGetter<AnyProps> = (data) => (params) => {
        const { field } = params;
        if (field === "id")
            return false;

        if (field === "valueChoice" || field === "value") {
            const rowCategorySpec = params.value

            const categorySpec = (data.categorySpecifications as AnyProps[]).find(c => 
                    c.id === rowCategorySpec);

            if (field === "valueChoice") {
                return !!(categorySpec?.choices)
            }

            return !(categorySpec?.choices);
        }

        return true;
    }

    return (
        <DataGrid
            url="itemInstanceSpecifications"
            getCols={getColumns}
            getRows={getRows}
            initialRow={initialRow}
            getRequestBody={getRequestBody}
            responseKey={"itemInstanceSpecifications"}
            getIsCellEditable={getIsCellEditable}
        />
    )
}

export default ItemInstanceSpecificationDataGrid;