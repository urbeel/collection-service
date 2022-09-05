import {createSlice} from "@reduxjs/toolkit";

const initialState = {
    username: null,
    accessToken: null,
    roles: [],
}

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        login: (state, action) => {
            state.username = action.payload.username;
            state.accessToken = action.payload.accessToken;
            state.roles = action.payload.roles;
        },
        logout: (state) => {
            state.username = null;
            state.accessToken = null;
            state.roles = null;
        },
        authFromSession: (state) => {
            state.username = sessionStorage.getItem('username');
            state.accessToken = sessionStorage.getItem('accessToken');
            state.roles = sessionStorage.getItem('roles');
        },
    }
})

export const selectAuth = (state) => state.auth;

export const {login, logout, authFromSession} = authSlice.actions;

export const authReducer = authSlice.reducer;