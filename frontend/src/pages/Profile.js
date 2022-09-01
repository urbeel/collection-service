import React, {useEffect, useState} from 'react';
import {Container, Grid} from "@mui/material";
import {Link} from "react-router-dom";
import Button from "@mui/material/Button";
import CollectionList from "../components/CollectionList";
import api from "../http";

const Profile = () => {
    const [collections, setCollections] = useState([]);
    useEffect(() => {
        api.get("/collections", {
            params: {
                "username": localStorage.getItem("username"),
            }
        }).then((response) => {
                setCollections(response.data);
            }
        ).catch((error) => {
            console.log(error);
        })
    }, [])

    return (
        <Container>
            <Grid container columnSpacing={5} marginTop={2} paddingX={5}>
                <Grid item xs={12} textAlign={"end"}>
                    <Button component={Link} to="/collections/new">Create collection</Button>
                </Grid>
                <Grid item xs={12}>
                 <CollectionList
                     collections={collections}
                 />
                </Grid>
            </Grid>
        </Container>
    );
};

export default Profile;