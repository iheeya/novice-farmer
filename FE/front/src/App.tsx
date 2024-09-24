import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Test from "./pages/test";
import SignUp from "./pages/user/signUp";
import Login from "./pages/user/login";

function Main() {
  return (
    <div>
      <h1>main page</h1>
    </div>
  );
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/test" element={<Test />} />
        <Route path="/user/signup" element={<SignUp />} />
        <Route path="/user/login" element={<Login />} />
      </Routes>
    </Router>
  );
}

export default App;
