import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from "react-router-dom";
import {CardMedia, Container, Grid, Paper, Typography} from "@mui/material";
import Button from "@mui/material/Button";
import ItemCard from "../../components/ItemCard";
import useAuth from "../../hooks/useAuth";
import api from "../../http";

const Collection = () => {
    const [collection, setCollection] = useState({});
    const [items, setItems] = useState([]);
    const {id} = useParams();
    const {username, roles} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        api.get(`/collections/${id}`)
            .then((response) => {
                setCollection(response.data);
                console.log(response.data);
                setItems(response.data.items)
            })
            .catch((error) => {
                console.warn(error);
                alert("Error while getting collection")
            })
    }, [id]);

    const checkAuthorship = () => {
        return collection.author === username || roles.includes("ROLE_ADMIN");
    }

    const deleteButtonHandler = () => {
        if (checkAuthorship() && window.confirm('Are you sure?')) {
            api.delete(`collections/${id}`).then(()=>{
                navigate("/");
            }).catch(console.error);
        }
    }

    return (
        <Container sx={{height: '85%'}}>
            <Grid container columnSpacing={3} marginTop={2} height='100%'>
                <Grid item rowSpacing={2} md={9}>
                    <Paper sx={{height: '100%', width: '100%', p: 2}}>
                        <Grid container>
                            <Grid item container md={8} mb={2} px={3}>
                                <Grid item md={12} mb={3} textAlign='center'>
                                    <Typography variant='h4'>Collection: {collection.name}</Typography>
                                </Grid>
                                <Grid item md={12} mb={1}>
                                    Author: {collection.author}
                                </Grid>
                                <Grid item md={12} mb={2}>
                                    Topic: {collection.topic}
                                </Grid>
                            </Grid>
                            <Grid item md={4} mb={2}>
                                <CardMedia
                                    component="img"
                                    image={collection.imageUrl}
                                    sx={{width: 151, borderRadius: 2}}
                                />
                            </Grid>
                            {items.map((item, index) => {
                                return (
                                    <Grid item xs={12} key={index}>
                                        <ItemCard
                                            itemName={item.name}
                                            itemId={item.id}
                                            collectionName={collection.id}
                                            authorName={collection.author}
                                        />
                                    </Grid>
                                );
                            })}
                        </Grid>
                    </Paper>
                </Grid>
                {checkAuthorship() &&
                    <Grid container item md={3}>
                        <Paper sx={{height: '100%', width: '100%', p: 2}}>
                            <Grid item xs={12} textAlign='center'>
                                <Button component={Link}
                                        variant="contained"
                                        fullWidth
                                        sx={{mb: 2}}
                                        to='/items/new'
                                        state={{
                                            "fields": collection.defaultFieldTypes,
                                            "collectionId": id
                                        }}>
                                    Add item
                                </Button>
                                <Button
                                    onClick={deleteButtonHandler}
                                    variant="contained"
                                    fullWidth
                                    color="error"
                                >
                                    Delete collection
                                </Button>
                            </Grid>
                        </Paper>
                    </Grid>
                }
            </Grid>
        </Container>
    );
};

export default Collection;