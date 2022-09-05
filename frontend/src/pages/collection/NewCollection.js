import React, {useState} from 'react';
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import {FormControl, Grid, InputLabel, MenuItem, Select} from "@mui/material";
import CustomFieldType from "../../components/CustomFieldType";
import api from "../../http";

const NewCollection = () => {
    const [count, setCount] = useState(0);
    const [fileName, setFileName] = useState(null);
    let navigate = useNavigate();
    const {register, unregister, handleSubmit} = useForm();

    const onSubmit = (data) => {
        const file = data.imageFiles[0];
        if (file) {
            api.post("/upload-file",
                {
                    "imageFile": file,
                },
                {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                }
            )
                .then(
                    (response) => {
                        data.imageUrl = response.data;
                        delete data.imageFiles;
                        api.post("/collections", data)
                            .then((response) => {
                                navigate("/");
                            })
                            .catch((error) => {
                                console.log(error);
                            })
                    }
                ).catch((error) => {
                console.log(error)
            })
        } else {
            api.post("/collections", data)
                .then((response) => {
                    navigate("/");
                })
                .catch((error) => {
                    console.log(error);
                })
        }
    }

    const addCustomField = () => {
        setCount(count + 1);
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <Grid container justifyContent={"center"}>
                <Grid p={4} m={4} component={Paper} item container xs={5} justifyContent={"space-between"}>
                    <Grid xs={12}>
                        <Typography variant="h5" textAlign="center" mb={3}>
                            Create new collection
                        </Typography>
                    </Grid>
                    <Grid xs={12} mb={2}>
                        <TextField
                            {...register("name")}
                            label="Collection name"
                            fullWidth
                        />
                    </Grid>
                    <Grid xs={12} mb={2}>
                        <FormControl fullWidth>
                            <InputLabel id="select-topic-label">Topic</InputLabel>
                            <Select
                                labelId="select-topic-label"
                                {...register("topic")}
                                label="Topic"
                            >
                                <MenuItem value="Books">Books</MenuItem>
                                <MenuItem value="Phones">Phones</MenuItem>
                                <MenuItem value="Games">Games</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid xs={12} mb={2}>
                        <TextField
                            multiline
                            rows={10}
                            label="Description"
                            fullWidth
                            {...register("description")}
                        />
                    </Grid>
                    <Grid xs={12} mb={2}>
                        <Button
                            variant="outlined"
                            component="label"
                        >
                            Choose image
                            <input type="file"
                                   accept="image/*"
                                   multiple={false}
                                   hidden
                                   onInput={(e) => setFileName(e.target.files[0].name)}
                                   {...register("imageFiles")}/>
                        </Button>
                        <Typography variant="body2" color="gray">{fileName}</Typography>
                    </Grid>
                    <Grid xs={12} mb={3}>
                        <Typography textAlign="center" variant={"h6"}>Add custom fields</Typography>
                    </Grid>
                    <Grid xs={12}>
                        {[...Array(count)].map((_, i) =>
                            <CustomFieldType
                                registerFunction={register}
                                index={i}
                                unregisterFunction={unregister}
                                key={i}/>
                        )}
                    </Grid>
                    <Grid xs={12}>
                        <Button variant="outlined" onClick={addCustomField}>Add field</Button>
                    </Grid>
                    <Grid xs={12} textAlign="center" mt={3}>
                        <Button variant="contained" type="submit">Create</Button>
                    </Grid>
                </Grid>
            </Grid>
        </form>
    )
        ;
};

export default NewCollection;