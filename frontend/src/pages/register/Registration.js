import React, {useState} from 'react';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import {Container, Grid} from "@mui/material";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import api from "../../http";

const Registration = () => {
    const [confirmPassword, setConfirmPassword] = useState("");
    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();

    const submitHandler = (data) => {
        if (data.password === confirmPassword){
            api.post("/auth/registration", data)
                .then(() => {
                    navigate("/login");
                })
                .catch(reason => {
                    if (reason.response.status===400){
                        console.log(reason)
                        alert(reason.response.data.message || reason.response.data);
                    }else {
                        console.error(reason);
                    }
                })
        }else {
            alert("Passwords don't match!")
        }
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
                                type="password"
                                label="Password"
                                fullWidth
                                sx={{mb: 2}}
                                {...register("password")}
                            />
                            <TextField type="password"
                                       label="Confirm password"
                                       onChange={(event)=>setConfirmPassword(event.target.value)}
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