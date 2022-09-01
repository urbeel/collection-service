import React from 'react';
import CollectionCard from "./CollectionCard";
import {Box, Grid, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography} from "@mui/material";
import Item from "./Item";

const ItemTable = (props) => {
    const {items} = props;

    if (!items) {
        return (
            <Box p={2} textAlign='center'>
                <Typography variant='h5'>
                    Empty
                </Typography>
            </Box>
        );
    }
    return (
        // <TableContainer component={Paper}>
        //     <Table sx={{minWidth: 650}} aria-label="simple table">
        //         <TableHead>
        //             <TableRow>
        //                 <TableCell>Dessert (100g serving)</TableCell>
        //                 <TableCell align="right">Calories</TableCell>
        //                 <TableCell align="right">Fat&nbsp;(g)</TableCell>
        //                 <TableCell align="right">Carbs&nbsp;(g)</TableCell>
        //                 <TableCell align="right">Protein&nbsp;(g)</TableCell>
        //             </TableRow>
        //         </TableHead>
        //         <TableBody>
        //             {rows.map((row) => (
        //                 <TableRow
        //                     key={row.name}
        //                     sx={{'&:last-child td, &:last-child th': {border: 0}}}
        //                 >
        //                     <TableCell component="th" scope="row">
        //                         {row.name}
        //                     </TableCell>
        //                     <TableCell align="right">{row.calories}</TableCell>
        //                     <TableCell align="right">{row.fat}</TableCell>
        //                     <TableCell align="right">{row.carbs}</TableCell>
        //                     <TableCell align="right">{row.protein}</TableCell>
        //                 </TableRow>
        //             ))}
        //         </TableBody>
        //     </Table>
        // </TableContainer>
        <>
            {
                items.map((item, index) => {
                    return <Item key={index}
                        // collectionName={collection.name}
                        // topic={collection.topic}
                        // author={collection.author}
                        // imageUrl={collection.imageUrl}
                        // collectionId={collection.id}
                    />
                })
            }
        </>
    )
        ;
};

export default ItemTable;