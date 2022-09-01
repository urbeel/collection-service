import {createSlice, createAsyncThunk} from "@reduxjs/toolkit";
import api from "../../http";

export const fetchLogin = createAsyncThunk('auth/fetchLogin', async (params) => {
    const {data} = await api.post('/auth/login', params).then()
    return data;
})

const initialState = {
    data: null,
    status: 'loading',
}

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        logout: (state) => {
            state.data = null;
        },
        authFromStorage: (state) => {
            state.data = window.localStorage.getItem('accessToken');
        },
    },
    extraReducers: {
        [fetchLogin.pending]: (state) => {
            state.status = 'loading';
            state.data = null;
        },
        [fetchLogin.fulfilled]: (state, action) => {
            state.status = 'loaded';
            state.data = action.payload;
        },
        [fetchLogin.rejected]: (state) => {
            state.status = 'error';
            state.data = null;
        }
    }
})

export const selectIsAuth = (state) => Boolean(state.auth.data)

export const {logout} = authSlice.actions;
export const {authFromStorage} = authSlice.actions;

export const authReducer = authSlice.reducer;