import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Test from "./pages/test";
import RegisterGarden from "./pages/registerGarden";
import RegisterPlant from "./pages/registerPlant";

function Main() {
  return <div>메인페이지</div>;
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main/>}/>
        <Route path="/test" element={<Test />} />
        <Route path="/register/garden" element={<RegisterGarden />} />
        <Route path="/register/plant" element={<RegisterPlant />} />
      </Routes>
    </Router>
  );
}

export default App;
