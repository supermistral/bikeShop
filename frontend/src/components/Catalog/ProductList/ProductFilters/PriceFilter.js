import { Box, Slider, Stack, TextField, Typography } from "@mui/material";
import React, { forwardRef, useCallback, useEffect, useState } from "react";
import NumberFormat from "react-number-format";
import { useSearchParams } from "react-router-dom";
import { debounce } from "lodash";


const PriceFormat = forwardRef((props, ref) => {
    const { onChange, validate, name, ...otherProps } = props;

    return (
        <NumberFormat
            {...otherProps}
            getInputRef={ref}
            isAllowed={validate}
            onValueChange={values => {
                onChange({
                    target: { name: name, value: values.value }
                });
            }}
            thousandSeparator=" "
            isNumericString
        />
    )
});


const PriceFilter = ({ price }) => {
    const minPrice = price.min;
    const maxPrice = price.max;

    const [values, setValues] = useState([minPrice, maxPrice]);

    const [searchParams, setSearchParams] = useSearchParams();

    const debouncedHandleChange = useCallback(
        debounce(e => {
            setValues(prev => {
                const value = +e.target.value;
                console.log(prev, value, minPrice, maxPrice);
                if (value >= minPrice && value <= maxPrice) {
                    if (e.target.name === "min-price")
                        return [value, prev[1]];
                    return [prev[0], value]
                }
                return prev;
            });
        }, 300),
        []
    )

    useEffect(() => {
        searchParams.set("price", values.join("-"));
        setSearchParams(searchParams);
    }, [values])

    const handleChange = e => {
        debouncedHandleChange(e);
    }

    const handleSliderChange = (e, value) => {
        setValues(value);
    }

    const handleValidate = ({ floatValue }) => {
        return floatValue <= maxPrice;
    }
    
    return (
        <>
            <Stack direction="row">
                <TextField
                    label="от"
                    value={values[0]}
                    onChange={handleChange}
                    name="min-price"
                    id="min-price-input"
                    variant="filled"
                    InputProps={{ 
                        inputComponent: PriceFormat, 
                        inputProps: { validate: handleValidate} 
                    }}
                    size="small"
                    sx={{ mr: 1 }}
                />
                <TextField
                    label="до"
                    value={values[1]}
                    onChange={handleChange}
                    name="max-price"
                    id="max-price-input"
                    variant="filled"
                    InputProps={{ 
                        inputComponent: PriceFormat, 
                        inputProps: { validate: handleValidate} 
                    }}
                    size="small"
                    sx={{ mr: 1 }}
                />
                <Box sx={{ alignSelf: 'flex-end' }}>
                    <Typography color="text.secondary" component="div">₽</Typography>
                </Box>
                
            </Stack>
            <Slider
                getAriaLabel={() => 'price'}
                value={values}
                defaultValue={[minPrice, maxPrice]}
                min={minPrice}
                max={maxPrice}
                onChange={handleSliderChange}
                size="small"
                disableSwap
            />
        </>
    )
}

export default PriceFilter;