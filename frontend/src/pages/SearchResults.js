import React from 'react';
import {Container, Grid} from "@mui/material";
import Button from "@mui/material/Button";
import {Link, useLocation} from "react-router-dom";
import ItemTable from "../components/ItemTable";

const SearchResults = (state) => {
    const location = useLocation();
    console.log(state)
    return (
        <Container>
            <Grid container columnSpacing={5} marginTop={2} paddingX={5}>
                <Grid item xs={12} textAlign={"end"}>
                    <Button component={Link} to="/collections/new">Create collection</Button>
                </Grid>
                <Grid item xs={12}>
                    <ItemTable items={location.state.items}/>
                </Grid>
            </Grid>
        </Container>
    );
};

export default SearchResults;