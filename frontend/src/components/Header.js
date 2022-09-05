import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Button from "@mui/material/Button";
import {Link, useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {logout} from "../redux/slices/authSlice";
import SearchBar from "./SearchBar";
import useAuth from "../hooks/useAuth";
import api from "../http";

const Header = () => {
    const {isAuth, roles} = useAuth();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const logoutHandler = () => {
        if (window.confirm('Are you sure?')) {
            api.post("auth/logout").then(()=>{
                dispatch(logout());
                sessionStorage.removeItem('accessToken');
                sessionStorage.removeItem('username');
                sessionStorage.removeItem('roles');
                navigate("/");
            }).catch(console.error);
        }
    }

    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="static">
                <Toolbar>
                    <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
                        <Button component={Link} variant='contained' to='/'>
                            HOME
                        </Button>
                        {(roles && roles.includes("ROLE_ADMIN")) &&
                            <Button component={Link} variant='contained' to='/admin'>
                                ADMIN
                            </Button>
                        }
                    </Box>
                    {isAuth ?
                        (
                            <>
                                <Button component={Link} to="/profile" variant="contained"
                                        sx={{margin: 1}}>
                                    Profile
                                </Button>
                                <Button color="error" onClick={logoutHandler} variant="contained"
                                        sx={{margin: 1}}>
                                    LogOut
                                </Button>
                            </>
                        ) : (
                            <>
                                <Button color="primary" component={Link} to="/login" variant="contained">
                                    Login
                                </Button>
                                <Button color="primary" component={Link} to="/register" variant="contained"
                                        sx={{margin: 1}}>
                                    SignUp
                                </Button>
                            </>
                        )
                    }
                    <SearchBar/>
                </Toolbar>
            </AppBar>
        </Box>
    );
};

export default Header;