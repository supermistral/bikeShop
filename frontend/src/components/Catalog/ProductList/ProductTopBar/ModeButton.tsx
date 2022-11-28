import React, { useState } from "react";
import ViewListIcon from '@mui/icons-material/ViewList';
import ViewModuleIcon from '@mui/icons-material/ViewModule';
import { ToggleButton, ToggleButtonGroup } from "@mui/material";


const modes = {
    list: 'list',
    tile: 'tile'
}


const ModeButton = () => {
    const [mode, setMode] = useState<string>(modes.tile)
    
    const handleChange = (e: React.MouseEvent<HTMLElement>, nextMode: string) =>
        nextMode !== null && setMode(nextMode);

    return (
        <ToggleButtonGroup
            value={mode}
            onChange={handleChange}
            exclusive
            size="small"
        >
            <ToggleButton value={modes.tile} aria-label={modes.tile}>
                <ViewModuleIcon />
            </ToggleButton>
            <ToggleButton value={modes.list} aria-label={modes.list}>
                <ViewListIcon />
            </ToggleButton>
        </ToggleButtonGroup>
    )
}

export default ModeButton