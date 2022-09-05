import React from 'react';
import {Box, Typography} from "@mui/material";
import moment from "moment";
import {CheckBoxOutlineBlankOutlined, CheckBoxOutlined} from "@mui/icons-material";

const DisplayField = (props) => {
    const field = props.field;
    const value = () => {
        switch (field.type.type) {
            case "STRING":
            case "MULTI_LINE_STRING":
                return field.stringValue;
            case "BOOLEAN":
                if (field.booleanValue === true) {
                    return <CheckBoxOutlined sx={{verticalAlign: 'middle', ml: 2}}/>;

                } else {
                    return <CheckBoxOutlineBlankOutlined/>;
                }
            case "NUMBER":
                return field.numberValue;
            case "DATE":
                if (moment(field.dateValue).isValid()) {
                    return moment(field.dateValue).format("DD.MM.YYYY");
                }
                break;
            default:
                return "";
        }
    }

    return (
        <Typography>
            <Box component='span' marginY='auto'>
                {field.type.name}:
            </Box>
            <Box component='span'>
                {value()}
            </Box>
        </Typography>
    );
};

export default DisplayField;