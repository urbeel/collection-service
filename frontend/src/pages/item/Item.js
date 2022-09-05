import React, {useEffect, useState} from 'react';
import {Button, Container, Grid, IconButton, Paper, TextField, Typography} from "@mui/material";
import {useParams} from "react-router-dom";
import DisplayField from "../../components/DisplayField";
import SockJsClient from 'react-stomp';
import Comment from "../../components/Comment";
import {useForm} from "react-hook-form";
import Divider from '@mui/material/Divider';
import {FavoriteBorderOutlined, FavoriteOutlined} from '@mui/icons-material';
import moment from "moment";
import api from "../../http";
import TagList from "../../components/TagList";
import useAuth from "../../hooks/useAuth";

const SOCKET_URL = `${process.env.REACT_APP_API_URL}/ws-message`;

const Item = () => {
    const [item, setItem] = useState({});
    const [likes, setLikes] = useState([]);
    const {id} = useParams();
    const {username} = useAuth();
    const [fields, setFields] = useState([]);
    const [comments, setComments] = useState([]);
    const {register, handleSubmit, reset} = useForm();

    useEffect(() => {
        api.get("/items/" + id)
            .then((response) => {
                setItem(response.data);
                setFields(response.data.fields);
                setComments(response.data.comments);
            }).catch((reason => {
            console.log(reason);
        }))
    }, [id]);

    useEffect(() => {
        downloadLikes();
    },[])

    const parseDate = (date) => {
        if (moment(date).isValid()) {
            return moment(date).format("DD.MM.YYYY HH:mm");
        }
    }

    const downloadLikes = () => {
        api.get("/likes", {
            params: {
                itemId: id,
            }
        }).then((response) => {
            setLikes(response.data);
        }).catch((reason) => {
            console.error(reason);
        })
    }

    const addComment = (comment) => {
        comment.itemId = id;
        api.post("/comments", comment)
            .then(() => {
                reset();
            })
            .catch((reason) => {
                console.error(reason)
            })
    }

    const onConnected = () => {
        console.log("Connected!!")
    }

    const onMessageReceived = (comment) => {
        console.log("Message!");
        if (comment === "updated") {
            downloadLikes();
        } else {
            setComments((comments) => [comment, ...comments]);
        }
    }

    const getLikeIcon = () => {
        return likes.includes(username) ?
            <IconButton onClick={unlike}>
                <FavoriteOutlined color="error"/>
            </IconButton>
            :
            <IconButton onClick={like}>
                <FavoriteBorderOutlined/>
            </IconButton>
    }

    const like = () => {
        api.post("/likes", null, {params: {itemId: id}}).catch(console.error);
    }

    const unlike = () => {
        api.delete("/likes", {params: {itemId: id}}).catch(console.error);
    }

    return (
        <Container maxWidth="sm" sx={{py: 3}}>
            <SockJsClient
                url={SOCKET_URL}
                topics={['/topic/comment', '/topic/like']}
                onConnect={onConnected}
                onDisconnect={console.log("Disconnected!")}
                onMessage={comment => onMessageReceived(comment)}
                debug={false}
            />
            <Grid container rowSpacing={2}>
                <Grid item xs={12}>
                    <Paper elevation={3} sx={{padding: 2, pb: 0}}>
                        <Grid container>
                            {item.createdDate &&
                                <Grid item md={12} xs={12} mb={2}>
                                    <Typography textAlign='end' variant='body2'
                                                color='gray'>Created: {parseDate(item.createdDate)}</Typography>
                                </Grid>
                            }
                            <Grid item md={12} xs={12} mb={4}>
                                <Typography textAlign='center' variant='h2'>{item.name}</Typography>
                            </Grid>
                            {fields.map((field, index) => {
                                return (
                                    <Grid container item key={index} xs={12} md={12} mb={1} px={3}>
                                        <DisplayField field={field}/>
                                        {index !== fields.length - 1 &&
                                            <Divider sx={{width: '100%'}}/>
                                        }
                                    </Grid>
                                )
                                    ;
                            })}
                            <Grid item md={12} xs={12} mt={2}>
                                <TagList tags={item.tags}/>
                            </Grid>
                            <Grid item md={12} xs={12} mt={3}>
                                <Typography textAlign='end'>
                                    {likes.length}
                                    {getLikeIcon()}
                                </Typography>
                            </Grid>
                        </Grid>
                    </Paper>
                </Grid>
                <Grid item xs={12}>
                    <Grid sx={{marginX: 'auto'}} item md={12} xs={12}>
                        <Paper sx={{padding: 2}}>
                            <Typography variant='body2' color={"gray"} mb={1}>
                                {username}
                            </Typography>
                            <form onSubmit={handleSubmit(addComment)}>
                                <TextField
                                    multiline
                                    variant='standard'
                                    placeholder='Enter text'
                                    fullWidth
                                    sx={{mb: 1}}
                                    {...register("content")}
                                />
                                <Grid textAlign='end'>
                                    <Button type='submit'>
                                        Comment
                                    </Button>
                                </Grid>
                            </form>
                        </Paper>
                    </Grid>
                </Grid>
                {
                    comments.map((comment) => {
                        return (
                            <Grid item xs={12} key={comment.id}>
                                <Comment
                                    content={comment.content}
                                    author={comment.author}
                                    postedDate={comment.createdDate}
                                />
                            </Grid>);
                    })
                }
            </Grid>
        </Container>
    )
        ;
};

export default Item;