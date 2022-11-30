import { GridRowId } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { AnyProps, DataGridColData } from "../../constants/types";
import DataGrid, {
    ChangeHandler, CloseHandler, ColsGetter,
    defaultManyToOneOptionsField, RequestBodyGetter, RowsGetter
} from "./DataGrid/DataGrid";
import ImageField from "./DataGrid/ImageField";


type ColsGetterProps = {
    parents?: {
        id: GridRowId;
        name: string;
    }[];
    imageUrls?: Record<GridRowId, string>;
    images?: Record<string, File | null>;
    handleChange?: ChangeHandler;
    handleClose?: CloseHandler;
}


const getCols = ({
    handleChange,
    handleClose,
    parents = [],
    imageUrls = {},
    images = {},
}: ColsGetterProps = {}): DataGridColData[] => [
    {
        field: "id",
        headerName: "Id",
        width: 50,
        type: 'number',
    },
    {
        field: "parent",
        headerName: "Родитель",
        width: 200,
        type: 'singleSelect',
        editable: true,
        ...defaultManyToOneOptionsField(parents)
    },
    {
        field: "name",
        headerName: "Название",
        width: 200,
        editable: true,
    },
    {
        field: "image",
        headerName: "Изображение",
        width: 250,
        editable: true,
        getValue: (params: any) => {
            return images[params.id] ? images[params.id]?.name : params.value
        },
        renderEditCell: params => (
            <ImageField
                imageUrl={imageUrls[params.id]}
                currentImage={params.row.image} 
                handleChange={handleChange!(params.id)}
                handleClose={handleClose!(params.id)}
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
    parent: "",
    name: "",
    image: ""
};

const getRows: RowsGetter = (data) => (data as AnyProps[]).map(item => ({
    ...item,
    parent: item.parent?.id || "",
}));


const CategoryDataGrid = () => {
    const [image, setImage] = useState<{ [key: string]: File | null }>({});
    const [getColumns, setGetColumns] = useState<ColsGetter>(() => getCols());
    
    const handleChange: ChangeHandler = (id) => (e) =>
        e.target.files && setImage(prev => ({ ...prev, [id]: e.target.files![0] }));

    const handleClose: CloseHandler = (id) => () =>
        setImage(prev => ({ ...prev, [id]: null }));

    useEffect(() => {
        const imageUrls: Record<GridRowId, string> = {}
        Object.keys(image)
            .filter(id => image[id])
            .forEach(id => imageUrls[id] = URL.createObjectURL(image[id]!));
        
        setGetColumns(() => (items: AnyProps[]) => getCols({
            parents: [
                { id: "", name: "" },
                ...items.map(item => ({ id: item.id, name: item.name })),
            ],
            images: image,
            imageUrls,
            handleChange,
            handleClose,
        }));
    }, [image]);

    const getRequestBody: RequestBodyGetter = ({ item, rowId }) => ({
        parentId: item.parent || null,
        name: item.name,
        image: image[rowId]?.name || item.image,
        imageData: image[rowId]
    });

    return (
        <DataGrid
            url={"category"}
            getCols={getColumns} 
            initialRow={initialRow}
            getRows={getRows}
            getRequestBody={getRequestBody}
            urlUpdate={"category"}
            urlCreate={"category"}
            urlDelete={"category"}
        />
    )
}

export default CategoryDataGrid;