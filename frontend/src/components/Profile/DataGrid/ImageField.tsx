import React from "react";
import { Box, Button, FormControl, IconButton } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';


export interface ImageFieldProps {
    id?: number;
    disabled?: boolean;
    currentImage?: string;
    imageUrl: string;
    handleChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
    handleClose?: () => void;
}


const ImageField = ({
    id,
    disabled,
    currentImage,
    imageUrl,
    handleChange,
    handleClose
}: ImageFieldProps) => {

    return (
        <FormControl sx={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between', height: '100%' }}>
            {imageUrl ? (
                <img src={imageUrl} style={{ maxWidth: '50%', height: '100%' }} />
            ) : ( 
                <img src={currentImage} style={{ maxWidth: '50%' }} />
            )}
            {!disabled &&
                <>
                    <input
                        accept="image/*"
                        style={{ display: 'none' }}
                        id={`image-upload-${id}`}
                        type="file"
                        onChange={handleChange}
                    />
                    <label htmlFor={`image-upload-${id}`} style={{ display: 'flex' }}>
                        <Button variant="contained" component="span">Upload</Button>
                        <Box sx={{ display: 'flex', alignItems: 'center' }}>
                            <IconButton onClick={handleClose} size="small">
                                <CloseIcon />
                            </IconButton>
                        </Box>
                </label>
                </>
            }
        </FormControl>
    )
}

export default ImageField;