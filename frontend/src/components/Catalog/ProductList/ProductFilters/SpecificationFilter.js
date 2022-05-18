import React from "react";
import ListFilter from "./ListFilter";


const SpecificationFilter = ({ specifications }) => {
    return specifications.map((specification, i) => ({
        filter: <ListFilter
            name={`specification${i}`}
            items={specification.values}
            label={specification.key}
            searchParamKey={specification.id}
            collapsed={true}
        />
    }));
}

export default SpecificationFilter;