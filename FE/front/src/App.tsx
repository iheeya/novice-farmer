import React, { useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, useLocation, useNavigate, Navigate } from "react-router-dom";
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
import CameraPage from "./pages/Detail/cameraPage"; 
import CameraDiagnosis from "./pages/Detail/cameraDiagnosis";
import FooterWithLocation from "./components/FooterWithLocation"; // Footer 로직 분리
import HomePage from "./pages/Home/Landing";
import InfoHome from "./pages/Information/InfoHome";
import InfoPlaceType from "./pages/Information/InfoPlaceType";
import InfoPlace from "./components/Information/InfoPlace";
import InfoPlant from "./components/Information/InfoPlant";
import InfoPlaceDetail from "./pages/Information/InfoPlaceDetail";
import TodoList from "./pages/Todo/TodoList";
import ArticleModify from "./pages/Community/ArticleModify";
import InfoPlantDetail from "./pages/Information/InfoPlantDetail";
import InfoFertilizer from "./pages/Information/InfoFertiilizer";
import InfoFertilizerDetail from "./pages/Information/InfoFertilizerDetail";
import InfoPest from "./pages/Information/InfoPest";
import InfoPestDetail from "./pages/Information/InfoPestDetail";


// AppWrapper 컴포넌트
function AppWrapper() {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const accessToken = sessionStorage.getItem("accessToken");

    // 인증이 필요 없는 경로 리스트
    const publicPaths = ["/user/login", "/user/signUp", "/introduce"];

    // 현재 경로가 인증이 필요 없는 경로가 아닌데, 토큰이 없으면 로그인 페이지로 이동
    if (!accessToken && !publicPaths.includes(location.pathname)) {
      navigate("/introduce");
    }
  }, [location, navigate]);

  return (
    <>
      <Routes>
        {/* 공개 페이지들 */}
        <Route path="/" element={<Main />} />
        <Route path="/user/signUp" element={<SignUp />} />
        <Route path="/user/login" element={<Login />} />
        <Route path="/user/survey" element={<Survey />} />
        <Route path="/introduce" element={<HomePage />} />

        {/* 인증이 필요한 페이지들 */}
        <Route path="/register/garden" element={<RegisterGarden />} />
        <Route path="/register/plant" element={<RegisterPlant />} />
        <Route path="/weekendFarm-recommend" element={<WeekendFarmList />} />
        <Route path="/community" element={<Community />} />
        <Route path="/community/:id/detail" element={<CommunityDetail />} />
        <Route path="/community/article/write" element={<WriteCommunityArticle />} />
        <Route path="/myGarden/:myPlaceId" element={<MyGarden />} />
        <Route path="/myGarden/:myPlaceId/:myPlantId" element={<MyPlant />} />
        <Route path="/myPage" element={<MyPage />} />
        <Route path="/myPage/profile" element={<ProfileUpdate />} />
        <Route path="/introduce" element={<LandingPage />} />
        <Route path="/community/search/:search" element={<CommunitySearchResult/>} />
        <Route path="/community/search" element={<CommunitySearch/>} />
        <Route path="/community/:id/modify" element={<ArticleModify/>} />
        <Route path="/myGarden/:myPlaceId/:myPlantId/camera" element={<CameraPage />} />
        <Route path="/info" element={<InfoHome />}>
          {/* 텃밭 */}
          <Route path="place" element={<InfoPlace />} />
          <Route path="place/type" element={<InfoPlaceType />} />
          <Route path="place/type/:title" element={<InfoPlaceDetail />} />
          {/* 작물 */}
          <Route path="plant" element={<InfoPlant />} />
          <Route path="plant/:title" element={<InfoPlantDetail />} />
          <Route path="plant/fertilizer" element={<InfoFertilizer />} />
          <Route path="plant/fertilizer/:title" element={<InfoFertilizerDetail />} />
          <Route path="plant/pest" element={<InfoPest />} />
          <Route path="plant/pest/:title" element={<InfoPestDetail />} />
        </Route>
        <Route path="/introduce" element={<HomePage />} />
        <Route path="/myGarden/:myPlaceId/:myPlantId/diagnosis" element={<CameraDiagnosis />} />
        <Route path="/todo" element={<TodoList />} />
      </Routes>
      <FooterWithLocation />
    </>
  );
}

// 최상위 App 컴포넌트
function App() {
  return (
    <Router>
      <AppWrapper />
    </Router>
  );
}

export default App;
