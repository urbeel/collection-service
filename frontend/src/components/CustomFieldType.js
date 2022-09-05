import React from 'react';
import {FormControl, Grid, InputLabel, MenuItem, Select} from "@mui/material";
import TextField from "@mui/material/TextField";

const CustomFieldType = (props) => {
    const register = props.registerFunction;
    const index = props.index;
    return (
        <Grid container columnSpacing={1} mb={2} justifyContent={"space-between"}>
            <Grid item xs={4}>
                <FormControl fullWidth>
                    <InputLabel id="select-field-type-label">Field type</InputLabel>
                    <Select
                        labelId="select-field-type-label"
                        {...register("defaultFieldTypes." + index + ".type")}
                        label="Field type"
                    >
                        <MenuItem value="NUMBER">Number</MenuItem>
                        <MenuItem value="STRING">String</MenuItem>
                        <MenuItem value="BOOLEAN">Boolean</MenuItem>
                        <MenuItem value="DATE">Date</MenuItem>
                    </Select>
                </FormControl>
            </Grid>
            <Grid item xs={8}>
                <TextField
                    {...register("defaultFieldTypes." + index + ".name")}
                    label="Field name"
                    fullWidth
                />
            </Grid>
        </Grid>
    );
};

export default CustomFieldType;