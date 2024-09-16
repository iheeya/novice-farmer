import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Test from "./pages/test";

function Main() {
  return <div>메인페이지</div>;
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main/>}/>
        <Route path="/test" element={<Test />} />
      </Routes>
    </Router>
  );
}

export default App;
