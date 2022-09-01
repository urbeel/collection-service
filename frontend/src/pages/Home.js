import React, {useEffect, useState} from 'react';
import {Container, Grid, List} from "@mui/material";
import Item from "../components/Item";
import Tag from "../components/Tag";
import Typography from "@mui/material/Typography";
import CollectionList from "../components/CollectionList";
import api from "../http";

const Home = () => {
        const [topCollections, setTopCollections] = useState([]);
        useEffect(() => {
                api.get("/collections/top5")
                    .then((response) => {
                        setTopCollections(response.data);
                    })
                    .catch((reason) => {
                        console.log(reason);
                    })
            }, []
        )

        return (
            <Container sx={{pb: 5}}>
                <Grid container columnSpacing={5} marginTop={2}>
                    <Grid item md={6}>
                        <Typography textAlign="center" mb={2} variant='h4'>Last added items</Typography>
                        <Item itemName='Apple' authorName='Kate' collectionName='fruits'/>
                        <Item/>
                        <Item/>
                        <Item/>
                        <Item/>
                        <Item/>
                        <Item/>
                        <Item/>
                        <Item/>
                        <Item/>
                    </Grid>
                    <Grid container item md={6}>
                        <Grid item md={12}>
                            <List direction="row" spacing={1}>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                                <Tag/>
                            </List>
                        </Grid>
                        <Grid item md={12}>
                            <Typography textAlign="center" mb={2} variant='h4'>Top 5 biggest collections</Typography>
                            <CollectionList
                                collections={topCollections}
                                columns={7}
                            />
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                        </Grid>
                    </Grid>
                </Grid>
            </Container>
        );
    }
;

export default Home;