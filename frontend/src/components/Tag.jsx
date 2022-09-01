import React from 'react';
import {Chip} from "@mui/material";

const Tag = () => {
    const clickTagHandler = () =>{
        console.log('Tag was clicked');
    }

    return (
            <Chip sx={{margin:1}} label="TagValue" onClick={clickTagHandler}/>
    );
};

export default Tag;