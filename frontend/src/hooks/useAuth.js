import {useSelector} from "react-redux";
import {selectAuth} from "../redux/slices/authSlice";

const UseAuth = () => {
    const {username, accessToken, roles} = useSelector(selectAuth);

    return {
        isAuth: Boolean(accessToken),
        username: username,
        roles: roles,
    };
};

export default UseAuth;