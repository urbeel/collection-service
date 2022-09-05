import React, {useEffect, useState} from 'react';
import {Grid} from "@mui/material";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {useLocation, useNavigate} from "react-router-dom";
import GenerateField from "../../service/GenerateField";
import {useForm} from "react-hook-form";
import CreatableSelect from "react-select/creatable";
import api from "../../http";

const NewItem = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [tagsFromDB, setTagsFromDB] = useState([]);
    const [fields, setFields] = useState([]);
    const [tags, setTags] = useState([]);
    const {register, handleSubmit, setValue} = useForm();

    const onSubmit = (item) => {
        item.tags = tags;
        item.collectionId = location.state.collectionId;
        api.post("/items", item).then(() => {
                navigate("/collections/" + item.collectionId);
            }
        ).catch((reason) => {
            console.error(reason);
        })
    }

    const handleChange = (newValue) => {
        setTags(newValue.map(element => element.value));
    };

    useEffect(() => {
        if (location.state) {
            setFields(location.state.fields);
        } else {
            navigate("/");
        }
    }, []);

    useEffect(() => {
        if (location.state) {
            api.get("/tags")
                .then((response) => {
                    const arr = response.data.map(el => ({
                        label: el,
                        value: el,
                    }))
                    setTagsFromDB(arr);
                })
        }
    }, []);

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <Grid container justifyContent="center" paddingX={5} marginTop={2}>
                <Grid p={4} m={4} component={Paper} item container xs={5} justifyContent={"space-between"}>
                    <Grid item xs={12}>
                        <Typography textAlign="center" mb={3} variant="h5">
                            Create new item
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            sx={{mb: 2}}
                            label="ItemCard name"
                            fullWidth
                            {...register('name')}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <CreatableSelect
                            isClearable
                            isMulti
                            onChange={handleChange}
                            options={tagsFromDB}
                        />
                    </Grid>
                    {fields.map((field, index) => {
                        return (
                            <Grid item xs={12} key={index}>
                                <GenerateField
                                    index={index}
                                    field={field}
                                    registerFunction={register}
                                    setValueFunction={setValue}
                                />
                            </Grid>
                        );
                    })}
                    <Grid textAlign="center" mt={2} item xs={12}>
                        <Button type="submit">Create</Button>
                    </Grid>
                </Grid>
            </Grid>
        </form>
    );
};

export default NewItem;