import { GridRowId, GridRowModel } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { AnyProps, DataGridColData } from "../../constants/types";
import DataGrid, {
    ChangeHandler, CloseHandler, defaultManyToOneOptionsField,
    RequestBodyGetter
} from "./DataGrid/DataGrid";
import ImageField from "./DataGrid/ImageField";


type ColsGetterProps = {
    itemInstances: AnyProps[];
    imageUrls?: Record<GridRowId, string>;
    images?: Record<string, File | null>;
    handleChange?: ChangeHandler;
    handleClose?: CloseHandler;
}


const getCols = ({
    itemInstances,
    imageUrls = {},
    images= {},
    handleChange,
    handleClose
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
        editable: true,
        ...defaultManyToOneOptionsField(itemInstances),
    },
    {
        field: "image",
        headerName: "Изображение",
        width: 250,
        editable: true,
        getValue: params => {
            return images[params.id] ? images[params.id]?.name : params.value
        },
        renderEditCell: params => (
            <ImageField
                imageUrl={imageUrls[params.id]}
                currentImage={params.row.image} 
                handleChange={handleChange && handleChange(params.id)}
                handleClose={handleClose && handleClose(params.id)}
            />
        ),
        renderCell: params => (
            <ImageField
                imageUrl={imageUrls[params.id]}
                currentImage={params.value}
                disabled={true}
            />
        )
    }
];

const initialRow = {
    id: "",
    itemInstance: "",
    image: ""
};

const getRows = (data: AnyProps[]): GridRowModel[] => data.map(item => ({
    ...item,
    itemInstance: item.itemInstance?.id
}));


const ItemInstanceImageDataGrid = () => {
    const [image, setImage] = useState<Record<string, File | null>>({});
    const [getColumns, setGetColumns] = useState(() => (item: AnyProps) => getCols({
        itemInstances: item.itemInstances,
    }));
    
    const handleChange: ChangeHandler = id => e =>
        e.target.files && setImage(prev => ({ ...prev, [id]: e.target.files![0] }));

    const handleClose: CloseHandler = id => () =>
        setImage(prev => ({ ...prev, [id]: null }));

    useEffect(() => {
        const imageUrls: Record<GridRowId, string> = {}
        Object.keys(image)
            .filter(id => image[id])
            .forEach(id => imageUrls[id] = URL.createObjectURL(image[id]!));
        
        setGetColumns(() => (item: AnyProps) => getCols({
            itemInstances: item.itemInstances,
            images: image,
            imageUrls,
            handleChange,
            handleClose
        }));
    }, [image]);

    const getRequestBody: RequestBodyGetter = ({ item, rowId }) => ({
        itemInstanceId: item.itemInstance,
        image: image[rowId]?.name || item.image,
        imageData: image[rowId]
    });

    return (
        <DataGrid 
            url={"itemInstances/images"}
            getCols={getColumns} 
            initialRow={initialRow}
            getRows={getRows}
            getRequestBody={getRequestBody}
            responseKey={"images"}
        />
    )
}

export default ItemInstanceImageDataGrid;