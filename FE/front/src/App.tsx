import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Test from "./pages/test";
import SignUp from "./pages/user/signUp";

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
        <Route path="/user/signUp" element={<SignUp />} />
      </Routes>
    </Router>
  );
}

export default App;
