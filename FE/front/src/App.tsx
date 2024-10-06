import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Main from "./pages/Home/Main";
import RegisterGarden from "./pages/AddGarden/registerGarden";
import RegisterPlant from "./pages/AddGarden/registerPlant";
import WeekendFarmList from "./pages/WeekendFarm/WeekendFarmList";
import Community from "./pages/Community/Community";
import CommunityDetail from "./pages/Community/CommunityDetail";
import SignUp from "./pages/user/signUp";
import Login from "./pages/user/login";
import Survey from "./pages/user/survey";
import MyGarden from "./pages/Detail/myGarden";  
import MyPlant from "./pages/Detail/myPlant";
import MyPage from "./pages/user/myPage";
import ProfileUpdate from "./pages/user/profileUpdate";
import WriteCommunityArticle from "./pages/Community/WriteCommunityArticle";
import CommunitySearch from "./pages/Community/CommunitySearch";
import LandingPage from "./pages/Home/Landing";
import CommunitySearchResult from "./pages/Community/CommunitySearchResult";
import CameraPage from "./pages/Detail/cameraPage"; // 새 페이지 컴포넌트
import FooterWithLocation from "./components/FooterWithLocation"; // Footer 로직 분리
import HomePage from "./pages/Home/Landing";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/register/garden" element={<RegisterGarden />} />
        <Route path="/register/plant" element={<RegisterPlant />} />
        <Route path="/weekendFarm-recommend" element={<WeekendFarmList />} />
        <Route path="/community" element={<Community />} />
        <Route path="/community/:id/detail" element={<CommunityDetail />} />
        <Route path="/community/article/write" element={<WriteCommunityArticle />} />
        <Route path="/user/signup" element={<SignUp />} />
        <Route path="/user/login" element={<Login />} />
        <Route path="/user/survey" element={<Survey />} />
        <Route path="/myGarden/:myPlaceId" element={<MyGarden />} />
        <Route path="/myGarden/:myPlaceId/:myPlantId" element={<MyPlant />} />
        <Route path="/myPage" element={<MyPage />} />
        <Route path="/myPage/profile" element={<ProfileUpdate />} />
        <Route path="/introduce" element={<LandingPage />} />
        <Route path="/community/search/:search" element={<CommunitySearchResult/>} />
        <Route path="/community/search" element={<CommunitySearch/>} />
        <Route path="/myGarden/:myPlaceId/:myPlantId/camera" element={<CameraPage />} />
        <Route path="/introduce" element={<HomePage />} />
      </Routes>
      <FooterWithLocation />
    </Router>
  );
}

export default App;
