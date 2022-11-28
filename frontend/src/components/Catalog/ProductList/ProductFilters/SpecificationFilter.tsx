import React from "react";
import { ProductSpecificationWithValuesData } from "../../../../constants/types";
import ListFilter from "./ListFilter";


export interface SpecificationFilterProps {
    specifications: ProductSpecificationWithValuesData[];
}

export interface FilterOnlyData {
    filter: JSX.Element;
}


const SpecificationFilter = ({ specifications }: SpecificationFilterProps): FilterOnlyData[] => {
    return specifications.map((specification, i) => ({
        filter: <ListFilter
            name={`specification${i}`}
            items={specification.values}
            label={specification.key}
            searchParamKey={specification.id.toString()}
            collapsed={true}
        />
    }));
}

export default SpecificationFilter;