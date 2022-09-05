import './App.css';
import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {authFromSession} from "./redux/slices/authSlice";
import {Route, Routes} from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/Home";
import Login from "./pages/login/Login";
import Registration from "./pages/register/Registration";
import NewCollection from "./pages/collection/NewCollection";
import NewItem from "./pages/item/NewItem";
import Profile from "./pages/Profile";
import SearchResults from "./pages/SearchResults";
import AdminPage from "./pages/AdminPage";
import Collection from "./pages/collection/Collection";
import Item from "./pages/item/Item";

function App() {

    const dispatch = useDispatch();

    useEffect(() => {
        if (sessionStorage.getItem('accessToken')) {
            dispatch(authFromSession());
        }
    }, [dispatch])

    return (
        <div className="App">
            <Header/>
            <Routes>
                <Route path={"/"} exact element={<Home/>}/>
                <Route path={"/login"} element={<Login/>}/>
                <Route path={"/register"} element={<Registration/>}/>
                <Route path={"/collections/new"} element={<NewCollection/>}/>
                <Route path={"/collections/:id"} element={<Collection/>}/>
                <Route path={"/items/new"} element={<NewItem/>}/>
                <Route path={"/items/:id"} element={<Item/>}/>
                <Route path={"/profile"} element={<Profile/>}/>
                <Route path={"/search"} element={<SearchResults/>}/>
                <Route path={"/admin"} element={<AdminPage/>}/>
            </Routes>
        </div>
    );
}

export default App;
