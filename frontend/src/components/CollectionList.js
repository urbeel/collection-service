import React from 'react';
import {Box, Grid, Typography} from "@mui/material";
import CollectionCard from "./CollectionCard";

const CollectionList = (props) => {
    const {collections, columns} = props;
    if (!collections) {
        return (
            <Box p={2} textAlign='center'>
                <Typography variant='h5'>
                    Empty
                </Typography>
            </Box>
        );
    }
    return (
        <Grid container columns={columns} spacing={2}>
            {collections.map((collection, index) => {
                return <CollectionCard key={index}
                                       collectionName={collection.name}
                                       topic={collection.topic}
                                       author={collection.author}
                                       imageUrl={collection.imageUrl}
                                       collectionId={collection.id}
                />
            })}
        </Grid>
    );
};

export default CollectionList;