import React from 'react';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import {Container, Grid} from "@mui/material";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import api from "../../http";

const Registration = () => {
    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();

    const submitHandler = (data) => {
        api.post("/auth/signup",data)
            .then(()=>{
                navigate("/login");
            })
            .catch(reason => {
                alert(reason);
            })
    }
    return (
        <Container sx={{height: '85%'}}>
            <Grid container alignContent='center' justifyContent='center' height='100%'>
                <Grid item md={5} xs={12}>
                    <Paper elevation={3} sx={{p: 3}}>
                        <Typography marginBottom={3} textAlign={"center"} variant="h5">
                            REGISTRATION FORM
                        </Typography>
                        <form onSubmit={handleSubmit(submitHandler)}>
                            <TextField
                                label="Username"
                                fullWidth
                                sx={{mb: 2}}
                                {...register("username")}
                            />
                            <TextField
                                label="Email"
                                fullWidth
                                sx={{mb: 2}}
                                {...register("email")}
                            />
                            <TextField
                                type="password"
                                label="Password"
                                fullWidth
                                sx={{mb: 2}}
                                {...register("password")}
                            />
                            <TextField type="password"
                                       label="Confirm password"
                                       fullWidth
                                       sx={{mb: 2}}/>
                            <Button
                                type="submit"
                                size="large"
                                variant="contained"
                                fullWidth
                            >
                                Login
                            </Button>
                        </form>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Registration;