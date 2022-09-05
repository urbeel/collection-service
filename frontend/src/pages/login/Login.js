import React from 'react';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import {Container, Grid} from "@mui/material";
import api from "../../http";
import {useDispatch} from "react-redux";
import {login} from "../../redux/slices/authSlice";

const Login = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {register, handleSubmit, formState: {errors}} = useForm({
        defaultValues: {
            username: '',
            password: '',
        },
    });

    const handleLogin = async (values) => {
        api.post('/auth/login', values)
            .then((response) => {
                dispatch(login({
                    username: response.data.user.username,
                    roles: response.data.user.roles,
                    accessToken: response.data.accessToken,
                }));
                sessionStorage.setItem("accessToken", response.data.accessToken);
                sessionStorage.setItem("username", response.data.user.username);
                sessionStorage.setItem("roles", response.data.user.roles);
                navigate("/");
            }).catch((reason) => {
            console.error(reason);
            alert('Error while login!');
        });
    }

    return (
        <Container sx={{height: '85%'}}>
            <Grid container justifyContent='center' alignContent='center' height='100%'>
                <Grid item md={5} xs={12}>
                    <Paper elevation={3} sx={{p: 3}}>
                        <Typography textAlign='center' mb={3} variant="h5">
                            LOGIN FORM
                        </Typography>
                        <form onSubmit={handleSubmit(handleLogin)}>
                            <TextField
                                label="Username"
                                sx={{mb: 2}}
                                error={Boolean(errors.username?.message)}
                                helperText={errors.username?.message}
                                {...register('username', {required: 'Username cannot be empty.'})}
                                fullWidth
                            />
                            <TextField
                                type={"password"}
                                sx={{mb: 2}}
                                label="Password"
                                error={Boolean(errors.password?.message)}
                                helperText={errors.password?.message}
                                {...register('password', {required: 'Password cannot be empty.'})}
                                fullWidth
                            />
                            <Button type="submit" size="large" variant="contained" fullWidth>
                                Login
                            </Button>
                        </form>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Login;