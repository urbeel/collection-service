import React from 'react';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import {useForm} from "react-hook-form";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {fetchLogin, selectIsAuth} from "../../redux/slices/authSlice";
import {Container, Grid} from "@mui/material";

const Login = () => {
    const isAuth = useSelector(selectIsAuth);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {register, handleSubmit, formState: {errors}} = useForm({
        defaultValues: {
            username: '',
            password: '',
        },
    });

    const onSubmit = async (values) => {
        const data = await dispatch(fetchLogin(values));
        console.log(data);
        if (!data.payload) {
            return alert('Error while login!');
        }
        if ('accessToken' in data.payload) {
            localStorage.setItem("accessToken", data.payload.accessToken);
            localStorage.setItem("username", data.payload.username);
        } else {
            alert('Error while login!');
        }
    }

    if (isAuth) {
        navigate("/");
    }

    return (
        <Container sx={{height: '85%'}}>
            <Grid container justifyContent='center' alignContent='center' height='100%'>
                <Grid item md={5} xs={12}>
                    <Paper elevation={3} sx={{p: 3}}>
                        <Typography textAlign='center' mb={3} variant="h5">
                            LOGIN FORM
                        </Typography>
                        <form onSubmit={handleSubmit(onSubmit)}>
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