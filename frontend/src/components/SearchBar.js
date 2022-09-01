import React from 'react';
import {Box, IconButton, TextField} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import api from "../http";

const SearchBar = () => {

    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();

    const submitHandler = (data) => {
        api.get("/items/search", {params: {"query": data.query}})
            .then((response) => {
                navigate("/search", {state: {"items": response.data}});
            })
    }

    return (
        <form onSubmit={handleSubmit(submitHandler)}>
            <Box sx={{flexGrow: 0}}>
                <TextField
                    id="search-bar"
                    label="Enter a city name"
                    variant="outlined"
                    placeholder="Search..."
                    size="small"
                    {...register("query")}
                />
                <IconButton type="submit" aria-label="search">
                    <SearchIcon style={{fill: "blue"}}/>
                </IconButton>
            </Box>
        </form>
    );
};

export default SearchBar;