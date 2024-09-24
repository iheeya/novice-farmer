import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Main from "./pages/Home/Main";
import Test from "./pages/test";
import RegisterGarden from "./pages/registerGarden";
import RegisterPlant from "./pages/registerPlant";
import WeekendFarmList from "./pages/WeekendFarm/WeekendFarmList";
import Footer from "./components/Footer";  // 푸터 컴포넌트 임포트


function App() {
  return (
    <Router>

      <Routes>
        <Route path='/' element={<Main/>}/>
        <Route path="/test" element={<Test />} />
        <Route path="/register/garden" element={<RegisterGarden />} />
        <Route path="/register/plant" element={<RegisterPlant />} />
        <Route path="/weekendFarm-recommend" element={<WeekendFarmList />} />
      </Routes>

        <Footer />
    </Router>
  );
}

export default App;
