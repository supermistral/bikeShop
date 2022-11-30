import { Box, Button } from "@mui/material";
import {
    GridRowModes, DataGrid as MuiDataGrid, GridToolbarContainer,
    GridActionsCellItem, GridRowEditStartParams,
    GridRowEditStopParams, MuiEvent, MuiBaseEvent,
    GridRowModesModel, GridRowModel, GridRowId, GridCellParams 
} from '@mui/x-data-grid';
import React, { useEffect, useState } from "react";
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import SaveIcon from '@mui/icons-material/Save';
import CancelIcon from '@mui/icons-material/Close';
import axiosInstance from "../../../constants/axios";
import { randomId } from "@mui/x-data-grid-generator";
import { AnyProps, DataGridColData } from "../../../constants/types";


export interface EditToolBarProps {
    setRows: React.Dispatch<React.SetStateAction<any[]>>;
    setRowModesModel: React.Dispatch<React.SetStateAction<GridRowModesModel>>;
    initialRow: GridRowModel;
}

export interface RequestBodyProps {
    item: AnyProps;
    rowId: GridRowId;
}

export type RowsGetter<R extends AnyProps = AnyProps[]> = (data: R) => GridRowModel[];
export type ColsGetter<R extends AnyProps = AnyProps[]> = (data: R) => DataGridColData[];
export type RequestBodyGetter = (data: RequestBodyProps) => AnyProps;
export type IsCellEditableGetter<R extends AnyProps = AnyProps[]> = (data: R) => (params: GridCellParams<any>) => boolean;

export type ChangeHandler = (id: GridRowId) => (event: React.ChangeEvent<HTMLInputElement>) => void;
export type CloseHandler = (id: GridRowId) => () => void;

export interface DataGridProps {
    url: string;
    urlUpdate?: string;
    urlCreate?: string;
    urlDelete?: string;
    getCols: ColsGetter<AnyProps> | ColsGetter<AnyProps[]> | ColsGetter<AnyProps | AnyProps[]>;
    getRows: RowsGetter<AnyProps> | RowsGetter<AnyProps[]>;
    initialRow: GridRowModel;
    getRequestBody: RequestBodyGetter;
    getIsCellEditable?: IsCellEditableGetter<AnyProps> | IsCellEditableGetter<AnyProps[]>;
    responseKey?: string;
}

type RowEditStartEvent = MuiEvent<React.MouseEvent<HTMLElement> | React.KeyboardEvent<HTMLElement>>;
type RowEditStopEvent = MuiEvent<MuiBaseEvent>;


export const defaultManyToOneFormatter = (data: AnyProps[]) => ({ value }: { value: any }) => {
    const dataItem = data.find(i => i.id === value);
    if (dataItem) return dataItem.id + " - " + dataItem.name;
    return "";
}

export const defaultManyToOneOptions = (data: AnyProps[]): number[] => data.map(item => item.id);

export const defaultManyToOneOptionsField = (data: AnyProps[]) => ({
    valueOptions: defaultManyToOneOptions(data),
    valueFormatter: defaultManyToOneFormatter(data),
});


const EditToolBar = ({ setRows, setRowModesModel, initialRow }: EditToolBarProps) => {
    
    const handleClick = () => {
        const rowId = randomId();
        setRows((oldRows) => [...oldRows, { rowId, ...initialRow, isNew: true }]);
        setRowModesModel((oldModel) => ({
            ...oldModel,
            [rowId]: { mode: GridRowModes.Edit },
        }));
    };

    return (
        <GridToolbarContainer>
            <Button color="primary" onClick={handleClick} startIcon={<AddIcon />}>
                Создать
            </Button>
        </GridToolbarContainer>
    )
}


const DataGrid = ({ 
    url,
    urlUpdate,
    urlCreate,
    urlDelete,
    getCols, 
    getRows, 
    initialRow,
    getRequestBody,
    getIsCellEditable, 
    responseKey,
    ...props
}: DataGridProps) => {
    const [items, setItems] = useState<AnyProps | AnyProps[]>();
    const [rows, setRows] = useState<GridRowModel[]>([]);
    const [rowModesModel, setRowModesModel] = useState<GridRowModesModel>({});

    useEffect(() => {
        axiosInstance
            .get(url)
            .then(res => setItems(res.data));

    }, []);

    useEffect(() => {
        if (items)
            setRows(
                getRows(responseKey ? (items as AnyProps)[responseKey] : items).map(row => ({
                    ...row,
                    rowId: randomId(),
                }))
            );
    }, [items]);

    const handleRowEditStart = (params: GridRowEditStartParams, event: RowEditStartEvent) => {
        event.defaultMuiPrevented = true;
    };
    
    const handleRowEditStop = (params: GridRowEditStopParams, event: RowEditStopEvent) => {
        event.defaultMuiPrevented = true;
    };
    
    const handleEditClick = (rowId: GridRowId) => () => {
        setRowModesModel({ ...rowModesModel, [rowId]: { mode: GridRowModes.Edit } });
    };
    
    const handleSaveClick = (rowId: GridRowId) => () => {
        setRowModesModel({ ...rowModesModel, [rowId]: { mode: GridRowModes.View } });
    };
    
    const handleDeleteClick = (rowId: GridRowId) => () => {
        const itemId = rows.find(row => row.rowId === rowId).id;

        axiosInstance
            .delete(`${(urlDelete || url)}/${itemId}`);

        setRows(rows.filter((row) => row.rowId !== rowId));
    };
    
    const handleCancelClick = (rowId: GridRowId) => () => {
        setRowModesModel({
            ...rowModesModel,
            [rowId]: { mode: GridRowModes.View, ignoreModifications: true },
        });
    
        const editedRow = rows.find((row) => row.rowId === rowId);
        
        if (editedRow.isNew) {
            setRows(rows.filter((row) => row.rowId !== rowId));
        }
    };
    
    const processRowUpdate = (newRow: GridRowModel): GridRowModel => {
        const requestBody = getRequestBody({
            item: newRow,
            rowId: newRow.rowId
        });

        let formData, options;

        if (requestBody.hasOwnProperty("imageData")) {
            const { imageData, ...data } = requestBody;

            formData = new FormData();
            formData.append("data", new Blob([JSON.stringify(data)], {
                type: 'application/json',
            }));
            formData.append("image", imageData);

            options = {
                headers: {
                    'Content-Type': 'multipart/form-data',
                }
            };
        } else {
            formData = requestBody;
            options = {
                headers: {
                    'Content-Type': 'application/json',
                }
            };
        }

        if (newRow.isNew) {
            axiosInstance
                .post(urlCreate || url, formData, options)
                .catch(err => console.log(err));
        } else {
            axiosInstance
                .put(`${urlUpdate || url}/${newRow.id}`, formData, options)
                .catch(err => console.log(err));
        }

        const updatedRow = { ...newRow, isNew: false };
        setRows(rows.map((row) => (row.rowId === newRow.rowId ? updatedRow : row)));
        
        return updatedRow;
    };

    const getColumns = getCols as ColsGetter<AnyProps | AnyProps[]>;
    const getCellEditable = getIsCellEditable as IsCellEditableGetter<AnyProps | AnyProps[]> | undefined;

    const columns = items && [
        ...getColumns(items),
        {
            field: "actions",
            type: "actions",
            headerName: "Действия",
            width: 100,
            cellClassName: "actions",
            getActions: ({ id }: { id: GridRowId }) => {
                const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;

                if (isInEditMode) {
                    return [
                        <GridActionsCellItem
                            icon={<SaveIcon />}
                            label="Save"
                            onClick={handleSaveClick(id)}
                            color="primary"
                        />,
                        <GridActionsCellItem
                            icon={<CancelIcon />}
                            label="Cancel"
                            className="textPrimary"
                            onClick={handleCancelClick(id)}
                            color="inherit"
                        />,
                    ];
                }

                return [
                    <GridActionsCellItem
                        icon={<EditIcon />}
                        label="Edit"
                        className="textPrimary"
                        onClick={handleEditClick(id)}
                        color="inherit"
                    />,
                    <GridActionsCellItem
                        icon={<DeleteIcon />}
                        label="Delete"
                        onClick={handleDeleteClick(id)}
                        color="inherit"
                    />,
                ];
            },
        }
    ];

    return (
        <Box sx={{
            height: 1000,
            width: '100%',
            display: 'flex',
            '& .actions': { color: 'text.secondary' },
            '& .textPrimary': { color: 'text.primary' },
        }}>
            {columns && rows &&
                <MuiDataGrid
                    rows={rows}
                    columns={columns}
                    editMode="row"
                    getRowId={row => row.rowId}
                    rowModesModel={rowModesModel}
                    onRowEditStart={handleRowEditStart}
                    onRowEditStop={handleRowEditStop}
                    processRowUpdate={processRowUpdate}
                    components={{ Toolbar: EditToolBar  }}
                    componentsProps={{ toolbar: { setRows, setRowModesModel, initialRow } }}
                    experimentalFeatures={{ newEditingApi: true }}
                    rowHeight={65}
                    isCellEditable={getCellEditable && getCellEditable(items)}
                    {...props}
                />
            }
        </Box>
    )
}

export default DataGrid;