import { Box, InputBaseComponentProps, Slider, Stack, TextField, Typography } from "@mui/material";
import React, { forwardRef, useCallback, useEffect, useState } from "react";
import NumberFormat, { NumberFormatProps, NumberFormatValues } from "react-number-format";
import { useSearchParams } from "react-router-dom";
import { debounce } from "lodash";


export interface PriceFilterProps {
    price: {
        min: number;
        max: number;
    }
}

interface ValidationNumberFormatProps extends NumberFormatProps {
    validate: (values: NumberFormatValues) => boolean;
    onChange: (event: { target: { name: string; value: string } }) => void;
}

type SettingsSearchParamsData = {
    key: string;
    values: number[];
}


const PriceFormat = forwardRef<HTMLInputElement, ValidationNumberFormatProps>((props, ref) => {
    const { onChange, validate, name, ...otherProps } = props;

    return (
        <NumberFormat
            getInputRef={ref}
            isAllowed={validate}
            onValueChange={values => {
                onChange!({
                    target: ({ name: name!, value: values.value })
                })
            }}
            thousandSeparator=" "
            isNumericString
            {...otherProps}
        />
    )
});


const PriceFilter = ({ price }: PriceFilterProps) => {
    const minPrice = price.min;
    const maxPrice = price.max;

    const [values, setValues] = useState<number[]>([minPrice, maxPrice]);

    const [searchParams, setSearchParams] = useSearchParams();

    const debouncedHandleChange = useCallback(
        debounce((e: React.ChangeEvent<HTMLInputElement>) => {
            setValues(prev => {
                const value = +e.target.value;

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

    const debouncedSettingSearchParams = useCallback(
        debounce(({ key, values }: SettingsSearchParamsData) => {
            if (values[0] === minPrice && values[1] === maxPrice)
                searchParams.delete(key);
            else
                searchParams.set(key, values.join("-"));
            setSearchParams(searchParams);
        }, 300),
        []
    )

    useEffect(() => {
        debouncedSettingSearchParams({ key: "price", values: values });
    }, [values])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        debouncedHandleChange(e);
    }

    const handleSliderChange = (e: Event, value: number | number[]) => {
        setValues(value as number[]);
    }

    const handleValidate = ({ floatValue }: { floatValue: number }) => {
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
                        inputComponent: typeof PriceFormat as React.ElementType<InputBaseComponentProps>, 
                        inputProps: { validate: handleValidate } 
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
                        inputComponent: typeof PriceFormat as React.ElementType<InputBaseComponentProps>,
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