import React from 'react';
import {Container, Grid, Typography} from "@mui/material";
import {useLocation} from "react-router-dom";
import ItemList from "../components/ItemList";

const SearchResults = () => {
    const {items, query, tagName} = useLocation().state;

    const isValidItems = () => {
        console.log(items.length !== 0 + " = length")
        console.log(Array.isArray(items) + " = is Array")
        return  Array.isArray(items) && items.length !== 0;
    }

    return (
        <Container sx={{height:'85%'}}>
            <Grid container sx={{height:'100%'}} columnSpacing={5} marginTop={2} paddingX={5}>
                <Grid item xs={12}>
                    {isValidItems() ?
                        <>
                        <Typography variant="body1" color="gray" mb={2}>Results for "{query || tagName}":
                        </Typography>
                        <ItemList items={items}/>
                        </>
                        :
                        <Typography textAlign="center" mt={5} variant="h5">No results for "{query || tagName}".
                            Try checking your spelling or use more general terms</Typography>}
                </Grid>
            </Grid>
        </Container>
    );
};

export default SearchResults;