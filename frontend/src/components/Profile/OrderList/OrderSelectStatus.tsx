import { Chip, MenuItem, Select, SelectChangeEvent } from "@mui/material";
import { useEffect, useState } from "react";
import TimelapseIcon from '@mui/icons-material/Timelapse';
import DoneOutlineIcon from '@mui/icons-material/DoneOutline';
import axiosInstance from "../../../constants/axios";
import SavingSnackbar from "../../DOM/SavingSnackbar";


export interface StatusChipProps {
    value: string;
    label?: string;
}

export interface OrderSelectStatusProps {
    id: number;
    status: string;
}


const statusChoices = [
    {
        label: "Создан",
        value: "Created"
    },
    {
        label: "Подтвержден",
        value: "Confirmed"
    },
    {
        label: "В обработке",
        value: "InProcessing"
    },
    {
        label: "Выполнен",
        value: "Completed",
        icon: <DoneOutlineIcon />
    }
];

export const getStatusLabel = (status: string) => 
    statusChoices.find(s => s.value === status)?.label;


export const StatusChip = ({ value, label }: StatusChipProps) => {
    const statusIcon = (label 
        ? statusChoices.find(s => s.label === label)!.icon
        : statusChoices.find(s => s.value === value)!.icon) || <TimelapseIcon />;

    return (
        <Chip
            avatar={statusIcon}
            label={label || statusChoices.find(s => s.value === value)?.label}
            variant="outlined"
        />
    )
}


const OrderSelectStatus = ({ id, status }: OrderSelectStatusProps) => {
    const [value, setValue] = useState<string>();
    const [open, setOpen] = useState<boolean>(false);
    const [snackbarData, setSnackbarData] = useState<{ isSuccess: boolean; isOpen: boolean }>({
        isSuccess: false,
        isOpen: false
    });

    const changeSnackbarIsOpen = (isOpen: boolean) => setSnackbarData(prev => ({ ...prev, isOpen }))

    const handleClose = () => setOpen(false);
    const handleOpen = () => setOpen(true);
    const handleChange = (e: SelectChangeEvent) => setValue(e.target.value);

    useEffect(() => {
        if (!value)
            return;

        const statusValue = statusChoices.find(s => s.label === value)!.value;

        axiosInstance
            .put(`orders/${id}/status?name=${statusValue}`)
            .then(res => setSnackbarData({ isOpen: true, isSuccess: true }))
            .catch(e => setSnackbarData({ isOpen: true, isSuccess: false }));

    }, [value]);

    return (
        <>
            <Select
                id={`status-select-${id}`}
                open={open}
                onClose={handleClose}
                onOpen={handleOpen}
                value={value || status}
                onChange={handleChange}
                autoWidth
                variant="outlined"
                size="small"
                renderValue={() => (
                    <StatusChip value={status} label={value} />
                )}
            >
                {statusChoices.map((statusChoice, i) => 
                    <MenuItem key={i} value={statusChoice.label}>
                        {statusChoice.label}
                    </MenuItem>
                )}
            </Select>
            {snackbarData.isSuccess ? (
                <SavingSnackbar 
                    open={snackbarData.isOpen}
                    isSuccess={snackbarData.isSuccess}
                    close={() => changeSnackbarIsOpen(false)} 
                    message="Статус сохранен"
                />
            ) : (
                <SavingSnackbar 
                    open={snackbarData.isOpen}
                    isSuccess={snackbarData.isSuccess}
                    close={() => changeSnackbarIsOpen(false)}
                    message="Произошла ошибка при сохранении"
                />
            )}
        </>
    )
}

export default OrderSelectStatus;