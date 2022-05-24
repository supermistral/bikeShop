import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import axiosInstance, { removeAuthorizationData } from "../../../constants/axios";
import UserAuthContext from "../../DOM/UserAuthContext";


const Logout = () => {
    const { isAuthorized } = useContext(UserAuthContext);

    if (!isAuthorized) {
        return <Navigate to="/" />
    }

    axiosInstance
        .delete('/auth/token')
        .then(res => {
            removeAuthorizationData();
        })
        .catch(err => alert("Произошла ошибка: " + err));
    
    return <Navigate to="/" />
}

export default Logout;