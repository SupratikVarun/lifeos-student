import { BrowserRouter, Routes, Route } from "react-router-dom";

import MainLayout from "../components/layout/MainLayout";
import Dashboard from "../pages/dashboard/Dashboard";
import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";

export default function AppRoutes() {

    return (

        <BrowserRouter>

            <Routes>

                <Route path="/login" element={<Login />} />

                <Route path="/register" element={<Register />} />

                <Route element={<MainLayout />}>

                    <Route path="/" element={<Dashboard />} />

                </Route>

            </Routes>

        </BrowserRouter>

    );

}