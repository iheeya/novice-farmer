import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Main from "./pages/Home/Main";
import Test from "./pages/test";
import Footer from "./components/Footer";  // 푸터 컴포넌트 임포트

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/test" element={<Test />} />
        </Routes>

        {/* 푸터는 모든 페이지 하단에 공통적으로 보임 */}
        <Footer />
      </div>
    </Router>
  );
}

export default App;
