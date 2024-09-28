import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Main from "./pages/Home/Main";
import RegisterGarden from "./pages/AddGarden/registerGarden";
import RegisterPlant from "./pages/AddGarden/registerPlant";
import WeekendFarmList from "./pages/WeekendFarm/WeekendFarmList";
import SignUp from "./pages/user/signUp";
import Login from "./pages/user/login";
import Survey from "./pages/user/survey";
import MyGarden from "./pages/Detail/myGarden";  
import Footer from "./components/Footer";  // 푸터 컴포넌트 임포트


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/register/garden" element={<RegisterGarden />} />
        <Route path="/register/plant" element={<RegisterPlant />} />
        <Route path="/weekendFarm-recommend" element={<WeekendFarmList />} />
        <Route path="/user/signup" element={<SignUp />} />
        <Route path="/user/login" element={<Login />} />
        <Route path="/user/survey" element={<Survey />} />
        <Route path="/myGarden/:myPlaceId" element={<MyGarden />} />
      </Routes>
      <Footer />
    </Router>
  );
}

export default App;
