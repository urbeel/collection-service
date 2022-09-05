import React from 'react';
import {Container, Typography} from "@mui/material";
import useAuth from "../hooks/useAuth";
import {useNavigate} from "react-router-dom";

const AdminPage = () => {
    const {roles} = useAuth();
    const navigate = useNavigate();

    return roles.includes("ROLE_ADMIN") ? (
            <Container>
                <Typography variant="h1">
                    ADMIN PAGE!
                </Typography>
            </Container>
        ) :
        navigate("/");
};

export default AdminPage;