import { Box, Button } from "@mui/material";
import { GridRowModes, DataGrid as MuiDataGrid, GridToolbarContainer, GridActionsCellItem } from '@mui/x-data-grid';
import React, { useEffect, useState } from "react";
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import SaveIcon from '@mui/icons-material/Save';
import CancelIcon from '@mui/icons-material/Close';
import axiosInstance from "../../../constants/axios";
import { randomId } from "@mui/x-data-grid-generator";


export const defaultManyToOneFormatter = data => ({ value }) => {
    const dataItem = data.find(i => i.id === value);
    if (dataItem) {
        return dataItem.id + " - " + dataItem.name;
    }
    return "";
}

export const defaultManyToOneOptions = data => data.map(item => item.id);

export const defaultManyToOneOptionsField = data => ({
    valueOptions: defaultManyToOneOptions(data),
    valueFormatter: defaultManyToOneFormatter(data),
});


const EditToolBar = ({ setRows, setRowModesModel, initialRow }) => {
    
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
    responseKey,
    ...props
}) => {
    const [items, setItems] = useState();
    const [rows, setRows] = useState([]);
    const [rowModesModel, setRowModesModel] = useState({});

    useEffect(() => {
        axiosInstance
            .get(url)
            .then(res => setItems(res.data));

    }, []);

    useEffect(() => {
        if (items)
            setRows(
                getRows(responseKey ? items[responseKey] : items).map(row => ({
                    ...row,
                    rowId: randomId(),
                }))
            );
    }, [items]);

    const handleRowEditStart = (params, event) => {
        event.defaultMuiPrevented = true;
    };
    
    const handleRowEditStop = (params, event) => {
        event.defaultMuiPrevented = true;
    };
    
    const handleEditClick = rowId => () => {
        setRowModesModel({ ...rowModesModel, [rowId]: { mode: GridRowModes.Edit } });
    };
    
    const handleSaveClick = rowId => () => {
        setRowModesModel({ ...rowModesModel, [rowId]: { mode: GridRowModes.View } });
    };
    
    const handleDeleteClick = rowId => () => {
        const itemId = rows.find(row => row.rowId === rowId).id;

        axiosInstance
            .delete(`${(urlDelete || url)}/${itemId}`);

        setRows(rows.filter((row) => row.rowId !== rowId));
    };
    
    const handleCancelClick = rowId => () => {
        setRowModesModel({
            ...rowModesModel,
            [rowId]: { mode: GridRowModes.View, ignoreModifications: true },
        });
    
        const editedRow = rows.find((row) => row.rowId === rowId);
        
        if (editedRow.isNew) {
            setRows(rows.filter((row) => row.rowId !== rowId));
        }
    };
    
    const processRowUpdate = (newRow) => {
        console.log(newRow);
        const requestBody = getRequestBody({ 
            item: newRow, 
            rowId: newRow.rowId, 
            data: items 
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
                header: {
                    'Content-Type': 'multipart/form-data',
                }
            }
        } else {
            formData = requestBody;
            options = {};
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

    const columns = items && [
        ...getCols(items),
        {
            field: "actions",
            type: "actions",
            headerName: "Действия",
            width: 100,
            cellClassName: "actions",
            getActions: ({ id }) => {
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
                    {...props}
                />
            }
        </Box>
    )
}

export default DataGrid;