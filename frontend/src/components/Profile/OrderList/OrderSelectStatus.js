import { Chip, MenuItem, Select } from "@mui/material";
import React, { useEffect, useState } from "react";
import TimelapseIcon from '@mui/icons-material/Timelapse';
import DoneOutlineIcon from '@mui/icons-material/DoneOutline';
import axiosInstance from "../../../constants/axios";
import SavingSnackbar from "../../DOM/SavingSnackbar";


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

export const getStatusLabel = status => 
    statusChoices.find(s => s.value === status)?.label;


export const StatusChip = ({ value, label }) => {
    const statusIcon = (label 
        ? statusChoices.find(s => s.label === label).icon
        : statusChoices.find(s => s.value === value).icon) || <TimelapseIcon />;

    return (
        <Chip
            avatar={statusIcon}
            label={label || statusChoices.find(s => s.value === value)?.label}
            variant="outlined"
        />
    )
}


const OrderSelectStatus = ({ id, status }) => {
    const [value, setValue] = useState();
    const [open, setOpen] = useState(false);
    const [success, setSuccess] = useState(false);

    const handleClose = () => setOpen(false);
    const handleOpen = () => setOpen(true);
    const handleChange = e => setValue(e.target.value);

    useEffect(() => {
        if (!value)
            return;

        const statusValue = statusChoices.find(s => s.label === value).value;

        axiosInstance
            .put(`orders/${id}/status?name=${statusValue}`)
            .then(res => setSuccess(true))
            .catch(e => setSuccess(false));

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
            {success.error ? (
                <SavingSnackbar 
                    open={success}
                    close={() => setSuccess(false)}
                    error={true}
                    message="Произошла ошибка при сохранении"
                />
            ) : success ? (
                <SavingSnackbar 
                    open={success}
                    close={() => setSuccess(false)} 
                    message="Статус сохранен"
                />
            ) : null}
        </>
    )
}

export default OrderSelectStatus;