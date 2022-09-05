import React from 'react';
import {Box, Grid, Typography} from "@mui/material";
import ItemCard from "./ItemCard";

const ItemList = (props) => {
    const {items} = props;
    if (!items || !Array.isArray(items)) {
        return (
            <Box p={2} textAlign='center'>
                <Typography variant='h5'>
                    Empty
                </Typography>
            </Box>
        );
    }
    return (
        <Grid container>
            {items.map((item) => {
                return <ItemCard key={item.id}
                                 itemName={item.name}
                                 itemId={item.id}
                                 collectionName={item.collectionName}
                                 authorName={item.author}
                />
            })}
        </Grid>
    );
};

export default ItemList;