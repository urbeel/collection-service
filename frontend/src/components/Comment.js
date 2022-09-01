import React from 'react';
import {Grid, Paper, Typography} from "@mui/material";
import moment from "moment/moment";

const Comment = (props) => {
    const {author, content, postedDate} = props;

    const parseDate = () => {
        if (moment(postedDate).isValid()) {
            return moment(postedDate).format("DD.MM.YYYY HH:mm");
        }
    }

    return (
        <Paper sx={{mt:1}}>
            <Grid container wrap="nowrap" padding={1} rowSpacing={1}>
                <Grid justifyContent="left" item xs zeroMinWidth>
                    <Typography variant='h6'>{author}</Typography>
                    <Typography variant='body2' textAlign='left'>
                        {content}
                    </Typography>
                    <Typography color='gray' textAlign='end'>
                        {parseDate()}
                    </Typography>
                </Grid>
            </Grid>
        </Paper>
    );
};

export default Comment;