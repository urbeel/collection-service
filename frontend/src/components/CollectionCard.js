import React from 'react';
import {Card, CardActionArea, CardContent, CardMedia, Grid} from "@mui/material";
import Typography from "@mui/material/Typography";
import {useNavigate} from "react-router-dom";

const CollectionCard = (props) => {
    let navigate = useNavigate();
    const emptyImage = "http://res.cloudinary.com/urbel-cloud/image/upload/v1661354381/m9ulqcpqpuehwywydz98.png";
    const imageUrl = props.imageUrl ? props.imageUrl : emptyImage;
    const collectionClickHandler = () => {
        navigate("/collections/" + props.collectionId)
    }
    return (
        <Grid item xs={12} md={3}>
            <Card sx={{margin: 'auto', height: '100%'}}>
                <CardActionArea onClick={collectionClickHandler}>
                    <CardMedia
                        sx={{mt:0}}
                        component="img"
                        height="150"
                        image={imageUrl}
                        alt="green iguana"
                    />
                    <CardContent>
                        <Typography gutterBottom variant="h5" component="div">
                            Name: {props.collectionName}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                                Author: {props.author}<br/>
                                Topic: {props.topic}
                        </Typography>
                    </CardContent>
                </CardActionArea>
            </Card>
        </Grid>
    );
};

export default CollectionCard;