import { Box, Button, Chip, FormControl, FormControlLabel, FormLabel, Radio, RadioGroup, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import FavoriteIcon from "../../Icons/FavoriteIcon";
import { ProductItemData, ProductItemInstanceReducedData } from "../../../constants/types";


export interface RadioSpecificationItemProps {
    specification: SpecificationWithInstanceIdsData;
    valueChoices: SpecificationValue[];
    updateValueChoices: (value: string) => void;
    index: number;
    disabled: boolean;
}

export interface RadioSpecificationsProps {
    setInstanceId: (id: number) => void;
    item: ProductItemData;
}

type SpecificationValue = {
    instanceIds: number[];
    value?: string;
    image?: string;
}

type SpecificationWithInstanceIdsData = {
    id: number;
    key: string;
    values: SpecificationValue[];
    searchParam?: string;
}

type RadioProps = Record<string, JSX.Element | string | boolean>


const calculateInstanceIds = (
    valueChoices: SpecificationValue[],
    startIndex: number = 0,
    endIndex: number = 0
) => {
    if (startIndex > endIndex)
        return [];
    else if (startIndex === endIndex)
        return valueChoices[0].instanceIds;

    const tempValueChoices = valueChoices.slice(startIndex, endIndex);
    let instanceIds = tempValueChoices[0].instanceIds;
    
    tempValueChoices.slice(1).forEach(value => {
        instanceIds = instanceIds.filter(id => value.instanceIds.includes(id));
    })
    
    return instanceIds;
}


const RadioSpecificationItem = ({ 
    specification,
    valueChoices,
    updateValueChoices,
    index,
    disabled,
}: RadioSpecificationItemProps) => {
    const [value, setValue] = useState<string | undefined>(valueChoices[index]?.value);
    // const [searchParams, setSearchParams] = useSearchParams();

    // useEffect(() => {
    //     if (specification.searchParam) {
    //         searchParams.set(
    //             specification.searchParam, 
    //             specification.values.findIndex(v => v.value === value)
    //         );
    //         setSearchParams(searchParams);
    //     }
    // }, [value]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => setValue(e.target.value);

    useEffect(() => {
        if (typeof value !== "undefined") {
            updateValueChoices(value);
        }
    }, [value]);

    useEffect(() => {
        setValue(valueChoices[index].value);
    }, [valueChoices[index].value])

    const formId = `instance-specification-group-${specification.id}`;
    const instanceIds = calculateInstanceIds(valueChoices, 0, index);

    return (
        <Box sx={{ py: 0.5 }}>
            <FormControl>
                <FormLabel id={formId} sx={{ py: 0.5 }}>{specification.key}</FormLabel>
                <RadioGroup
                    aria-labelledby={formId}
                    name={`instance-specification-radio-group-${specification.id}`}
                    value={value}
                    onChange={handleChange}
                    className="specification-radio-group"
                >
                    {specification.values.map((specValue, valueIndex) => {
                        const getButtonComponent = () => (
                            <Button
                                color="inherit"
                                variant="text"
                                size="small"
                            >
                                {specValue.value}
                            </Button>
                        );
                        const getImageComponent = () => (
                            <img src={specValue.image} alt="" />
                        );

                        const matchedId = specValue.instanceIds.findIndex(id => instanceIds.includes(id)) !== -1;

                        const radioProps: RadioProps = specValue.image ? {
                            icon: getImageComponent(),
                            checkedIcon: getImageComponent()
                        } : {
                            icon: getButtonComponent(),
                            checkedIcon: getButtonComponent()
                        };
                        radioProps.className = "specification-radio-button";
                        radioProps.checked = matchedId;

                        if (disabled && !matchedId)
                            radioProps.disabled = true;

                        return (
                            <FormControlLabel
                                key={valueIndex}
                                value={specValue.value}
                                control={ <Radio {...radioProps} /> }
                                className="form-specification-radio-button"
                                label={specValue.image && specValue.value}
                                componentsProps={{
                                    typography: { variant: "caption" }
                                }}
                            />
                        )
                    })}
                </RadioGroup>
            </FormControl>
        </Box>
    )
}


const createInstancesSpecifications = (instances: ProductItemInstanceReducedData[]) => {
    let specifications: SpecificationWithInstanceIdsData[] = [];
    let currentSpecification: SpecificationWithInstanceIdsData,
        currentSpecificationValueIndex: number,
        currentSpecificationValue: SpecificationValue;

    instances.forEach(instance => {
        instance.specifications.forEach(spec => {
            if (specifications.findIndex(s => s.id === spec.id) === -1) {
                currentSpecification = { 
                    id: spec.id,
                    key: spec.key,
                    values: [],
                };
                specifications.push(currentSpecification);
            } else {
                currentSpecification = specifications.find(s => s.id === spec.id)!;
            }

            currentSpecificationValueIndex = currentSpecification.values.findIndex(v => v.value === spec.value);

            if (currentSpecificationValueIndex === -1) {
                // Value of the current spec not found - push new value with new instance id list
                currentSpecification.values.push({
                    instanceIds: [instance.id],
                    value: spec.value
                });
            } else {
                // Value found - add current instance id to list
                currentSpecificationValue = currentSpecification.values[currentSpecificationValueIndex]
                currentSpecificationValue.instanceIds.push(instance.id);
            }
        });
    });

    const specificationColor = specifications.filter(s => s.key.toLowerCase() === "цвет");
    if (specificationColor.length === 1) {
        const color = specificationColor[0];
        const colorImages = instances.map(instance => instance.images[0]);
        
        color.searchParam = "color";
        color.values = color.values.map((value, i) => ({
            ...value,
            image: colorImages[i]
        }));
    }

    specifications.sort((s1, s2) => s1.id - s2.id);
    return specifications;
}


const calculateValuesChoices =(
    specifications: SpecificationWithInstanceIdsData[],
    index: number = 0,
    newValue: SpecificationValue | null = null,
    values: SpecificationValue[] | null = null
) => {
    let resultValues: SpecificationValue[], 
        instanceIds: number[];

    if (specifications.length === 0) {
        return [];
    }
    
    if (values) {
        resultValues = [...values];
        instanceIds = resultValues[0].instanceIds;
        
        resultValues.slice(1, index).forEach(value => {
            instanceIds = instanceIds.filter(id => value.instanceIds.includes(id));
        });

        resultValues[index] = newValue!;
    } else {
        resultValues = new Array(specifications.length);
        resultValues[0] = specifications[0].values[0];

        instanceIds = resultValues[0].instanceIds;
    }

    specifications.slice(index + 1).forEach((spec, i) => {
        const value = spec.values.find(value => instanceIds.find(id => value.instanceIds.includes(id)))!;
        resultValues[index + 1 + i] = value;

        instanceIds = instanceIds.filter(id => value.instanceIds.includes(id));
    });

    return resultValues;
}


const RadioSpecifications = ({ setInstanceId, item }: RadioSpecificationsProps) => {
    const instances = item.instances;
    const specifications = createInstancesSpecifications(instances);

    const [valueChoices, setValueChoices] = useState<SpecificationValue[]>(
        calculateValuesChoices(specifications) || [{
            instanceIds: [instances[0].id],
        }]
    );

    const updateValueChoices = (index: number) => (value: string) => {
        const specValue = specifications[index].values.find(v => v.value === value) || null;
        setValueChoices(prev => calculateValuesChoices(specifications, index, specValue, prev));
    }

    useEffect(() => {
        const instanceIds = calculateInstanceIds(valueChoices);
        if (instanceIds.length === 1) {
            setInstanceId(instanceIds[0])
        }
    }, [valueChoices]);

    const instanceIds = calculateInstanceIds(valueChoices);
    let stock = true;

    // console.log(valueChoices);
    
    if (instanceIds.length === 1) {
        const instanceId = instanceIds[0];
        stock = instances.find(instance => instance.id === instanceId)!.stock > 0;
    }

    return (
        <Box sx={{ p: 2 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <Chip
                    variant="outlined"
                    size="small"
                    {...(stock ? {
                        icon: <CheckCircleOutlineIcon />,
                        color: "success",
                        label: "В наличии",
                    } : {
                        icon: <RemoveCircleOutlineIcon />,
                        color: "default",
                        label: "Нет в наличии",
                    })}
                />
                <FavoriteIcon
                    item={item}
                    component={Button}
                    componentProps={{
                        variant: "outlined",
                        size: "small",
                        sx: { textTransform: 'none' },
                        color: "error",
                        children: "В избранное"
                    }}
                    color="error" 
                    sx={{ ml: 0.5 }}
                />
            </Box>
            <Box>
                {specifications.map((specification, i) =>
                    <RadioSpecificationItem
                        key={i}
                        specification={specification}
                        valueChoices={valueChoices}
                        updateValueChoices={updateValueChoices(i)}
                        disabled={i === specifications.length - 1}
                        index={i}
                    />
                )}
            </Box>
        </Box>
    )
}

export default RadioSpecifications;