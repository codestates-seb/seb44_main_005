import { Routes, Route } from "react-router-dom";
import Welcome from "../pages/Welcome";
import Home from "../pages/Home";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Partner from "../pages/Partner";

function MainRouter() {
  return (
    <Routes>
      <Route path="/" element={<Welcome />} />
      <Route path="/home" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/partner" element={<Partner />} />
    </Routes>
  );
}

export default MainRouter;