import React, {useState} from 'react';
import {Checkbox, FormControlLabel, TextField} from "@mui/material";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterMoment} from "@mui/x-date-pickers/AdapterMoment";


const GenerateField = (props) => {
    const [value, setValue] = useState(null);
    const register = props.registerFunction;
    const setValueV = props.setValueFunction;
    const index = props.index;
    const field = props.field;

    const stringField = () => {
        return (
            <TextField
                type="text"
                sx={{marginBottom: 2}}
                label={field.name}
                fullWidth
                {...register("fields." + index + ".stringValue")}
            />
        );
    }

    const numberField = () => {
        return (
            <TextField
                type="number"
                sx={{marginBottom: 2}}
                label={field.name}
                fullWidth
                {...register("fields." + index + ".numberValue")}
            />
        );
    }

    const dateField = () => {
        return (
            <LocalizationProvider dateAdapter={AdapterMoment}>
                <DatePicker
                    label={field.name}
                    value={value}
                    inputFormat="DD/MM/YYYY"
                    onChange={(newValue) => {
                        setValue(newValue);
                        setValueV("fields." + index + ".dateValue", newValue);
                    }}
                    renderInput={(params) =>
                        <TextField
                            sx={{marginBottom: 2}}
                            fullWidth
                            {...params} />}
                />
            </LocalizationProvider>
        );
    }

    const booleanField = () => {
        return (
            <FormControlLabel
                sx={{marginBottom: 2}}
                label={field.name}
                control={
                <Checkbox
                    {...register("fields." + index + ".booleanValue")}
                />
            }/>
        );
    }

    const multiLineStringField = () => {
        return (
            <TextField
                type="text"
                sx={{marginBottom: 2}}
                label={field.name}
                fullWidth
                multiline
                {...register("fields." + index + ".stringValue")}
            />
        );
    }

    setValueV("fields." + index + ".type",field);

    switch (field.type) {
        case "STRING":
            return stringField();
        case "NUMBER":
            return numberField();
        case "DATE":
            return dateField();
        case "BOOLEAN":
            return booleanField();
        case "MULTI_LINE_STRING":
            return multiLineStringField();
        default:
            return (<></>);
    }
};

export default GenerateField;