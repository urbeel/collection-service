import React from 'react';
import {Box, Grid, Typography} from "@mui/material";
import Tag from "./Tag";

const TagList = (props) => {
    const {tags} = props;

    if (!tags || !Array.isArray(tags)) {
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
            {tags.map((tag) => {
                return <Tag key={tag} value={tag}/>
            })}
        </Grid>
    );
};

export default TagList;