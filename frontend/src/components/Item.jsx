import React from 'react';
import {Card, CardActionArea, CardContent, Grid} from "@mui/material";
import {useNavigate} from "react-router-dom";
import Typography from "@mui/material/Typography";

const Item = (props) => {
    const {itemName, itemId, collectionName, authorName} = props;
    const navigate = useNavigate();

    const clickItemHandler = () => {
        navigate("/items/" + itemId);
    }

    return (
        <Card sx={{margin: 1}} elevation={3}>
            <CardActionArea onClick={clickItemHandler}>
                <CardContent>
                    <Grid container padding={1}>
                        <Grid item xs={12}>
                            <Typography>Name: {itemName}</Typography>
                        </Grid>
                        <Grid item xs={8}>
                            Collection: {collectionName}
                        </Grid>
                        <Grid item xs={4}>
                            Author: {authorName}
                        </Grid>
                    </Grid>
                </CardContent>
            </CardActionArea>
        </Card>
    );
};

export default Item;