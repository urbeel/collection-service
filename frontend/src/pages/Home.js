import React, {useEffect, useState} from 'react';
import {Container, Grid, List} from "@mui/material";
import Typography from "@mui/material/Typography";
import CollectionList from "../components/CollectionList";
import api from "../http";
import ItemList from "../components/ItemList";
import TagList from "../components/TagList";

const Home = () => {
        const [topCollections, setTopCollections] = useState([]);
        const [lastAddedItems, setLastAddedItems] = useState([]);
        const [tags, setTags] = useState([]);

        useEffect(() => {
                api.get("/collections/top5")
                    .then((response) => {
                        setTopCollections(response.data);
                    })
                    .catch((reason) => {
                        console.log(reason);
                    });
                api.get("/items/last-added")
                    .then((response) => {
                        setLastAddedItems(response.data);
                    })
                    .catch((reason) => {
                        console.log(reason);
                    });
            api.get("/tags")
                .then((response) => {
                    setTags(response.data);
                })
                .catch((reason) => {
                    console.log(reason);
                });
            }, []
        )

        return (
            <Container sx={{pb: 5}}>
                <Grid container columnSpacing={5} marginTop={2}>
                    <Grid item md={6}>
                        <Typography textAlign="center" mb={2} variant='h4'>Last added items</Typography>
                        <ItemList items={lastAddedItems}/>
                    </Grid>
                    <Grid container item md={6}>
                        <Grid item md={12}>
                            <List direction="row" spacing={1}>
                              <TagList tags={tags}/>
                            </List>
                        </Grid>
                        <Grid item md={12}>
                            <Typography textAlign="center" mb={2} variant='h4'>Top 5 biggest collections</Typography>
                            <CollectionList
                                collections={topCollections}
                                columns={7}
                            />
                        </Grid>
                    </Grid>
                </Grid>
            </Container>
        );
    }
;

export default Home;