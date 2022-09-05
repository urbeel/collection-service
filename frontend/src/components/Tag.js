import React from 'react';
import {Chip} from "@mui/material";
import {useNavigate} from "react-router-dom";
import api from "../http";

const Tag = (props) => {
    const navigate = useNavigate();
    const tagName = props.value;

    const clickTagHandler = () => {
        api.get("/items/find-by-tag", {params: {"tag": tagName}})
            .then((response)=>{
                navigate("/search",{state: {"items": response.data, "tagName": tagName}});
            }).catch(console.error);
    }

    return (
        <Chip sx={{margin: 1}} label={tagName} onClick={clickTagHandler}/>
    );
};

export default Tag;