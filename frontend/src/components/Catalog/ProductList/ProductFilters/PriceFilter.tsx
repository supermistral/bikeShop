import { Box, Slider, Stack, TextField, Typography } from "@mui/material";
import { useCallback, useEffect, useState } from "react";
import NumberFormat, { NumberFormatValues, SourceInfo } from "react-number-format";
import { useSearchParams } from "react-router-dom";
import { debounce } from "lodash";


export interface PriceFilterProps {
    price: {
        min: number;
        max: number;
    }
}

type SettingsSearchParamsData = {
    key: string;
    values: number[];
}


const PriceFilter = ({ price }: PriceFilterProps) => {
    const minPrice = price.min;
    const maxPrice = price.max;

    const [values, setValues] = useState<number[]>([minPrice, maxPrice]);

    const [searchParams, setSearchParams] = useSearchParams();

    const debouncedHandleChange = useCallback(
        debounce((values: NumberFormatValues, sourceInfo: SourceInfo) => {
            const value = values.floatValue;
            const event = sourceInfo.event;

            if (event && value) {
                setValues(prev => {
                    if (value < minPrice) return [minPrice, prev[1]];
                    if (value > maxPrice) return [prev[0], maxPrice];
                    if (event.target.name === "min-price") return [value, prev[1]];
                    return [prev[0], value];
                });
            }
        }, 300),
        [minPrice, maxPrice]
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

    const handleSliderChange = useCallback((e: Event, values: number | number[]) => {
        setValues(values as number[]);
    }, [])

    const isAllowed = useCallback(({ floatValue }: { floatValue: number | undefined }) => (
        (floatValue && maxPrice) ? floatValue <= maxPrice : false
    ), [minPrice, maxPrice])

    return (
        <>
            <Stack direction="row">
                <NumberFormat
                    customInput={TextField}
                    isAllowed={isAllowed}
                    onValueChange={debouncedHandleChange}
                    thousandSeparator=" "
                    label="от"
                    value={values[0]}
                    name="min-price"
                    id="min-price-input"
                    variant="filled"
                    size="small"
                    sx={{ mr: 1 }}
                />
                <NumberFormat
                    customInput={TextField}
                    isAllowed={isAllowed}
                    onValueChange={debouncedHandleChange}
                    thousandSeparator=" "
                    label="до"
                    value={values[1]}
                    name="max-price"
                    id="max-price-input"
                    variant="filled"
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