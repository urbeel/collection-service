import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {logout, selectIsAuth} from "../redux/slices/authSlice";
import SearchBar from "./SearchBar";

const Header = () => {
    const isAuth = useSelector(selectIsAuth);
    const dispatch = useDispatch();

    const onClickLogout = () => {
        if (window.confirm('Are you sure?')) {
            dispatch(logout());
            window.localStorage.removeItem('accessToken');
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
                    </Box>
                    {isAuth ?
                        (
                            <>
                                <Button component={Link} to="/profile" variant="contained"
                                        sx={{margin: 1}}>
                                    Profile
                                </Button>
                                <Button color="error" onClick={onClickLogout} variant="contained"
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