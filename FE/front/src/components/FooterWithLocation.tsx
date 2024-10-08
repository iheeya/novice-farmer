import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Footer from './Footer'; // 실제 푸터 컴포넌트

const FooterWithLocation: React.FC = () => {
  const location = useLocation(); // 현재 경로를 가져옴

  // 푸터를 숨길 경로들 (정규식 패턴 사용)
  const hideFooterPaths = [
    /^\/myGarden\/\d+\/\d+\/camera$/,  // /myGarden/:myPlaceId/:myPlantId/camera 경로 숨김
    /^\/user\/login$/,                  // /user/login 경로 숨김
    /^\/user\/signUp$/,                  // /user/signup 경로 숨김
    /^\/introduce$/
  ];

  // 경로 중 하나에 해당하는지 확인
  const shouldHideFooter = hideFooterPaths.some(regex => regex.test(location.pathname));

  useEffect(() => {
    // 푸터를 숨길 페이지일 경우, body에 padding을 없앰
    if (shouldHideFooter) {
      document.body.style.paddingBottom = '0';
    } else {
      // 푸터가 있을 경우 적당한 padding을 설정 (예: 60px)
      document.body.style.paddingBottom = '10vw';
    }

    // 컴포넌트 언마운트 시 padding 원상복구
    return () => {
      document.body.style.paddingBottom = '';
    };
  }, [shouldHideFooter]);

  return !shouldHideFooter ? <Footer /> : null;
};

export default FooterWithLocation;
