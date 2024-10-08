import React from 'react';
import { Link } from 'react-router-dom';  // 각 탭을 링크로 처리
import styles from '../styles/Footer.module.css';  // Footer 스타일 임포트

import HomeIcon from '../assets/icons/Home.png';
import InfoIcon from '../assets/icons/Info.png';
import TodoIcon from '../assets/icons/Todo.png';
import CommunityIcon from '../assets/icons/Community.png';
import MypageIcon from '../assets/icons/Mypage.png';

const Footer = () => {
  return (
    <footer className={styles.footer}>
      <nav className={styles.nav}>
        <Link to="/" className={styles.tab}>
          <img src={HomeIcon} alt="Home" className={styles.icon} />
          <span>홈</span>
        </Link>
        <Link to="/info" className={styles.tab}>
          <img src={InfoIcon} alt="정보" className={styles.icon} />
          <span>정보</span>
        </Link>
        <Link to="/todo" className={styles.tab}>
          <img src={TodoIcon} alt="ToDo" className={styles.icon} />
          <span>ToDo</span>
        </Link>
        <Link to="/community" className={styles.tab}>
          <img src={CommunityIcon} alt="커뮤니티" className={styles.icon} />
          <span>커뮤니티</span>
        </Link>
        <Link to="/myPage" className={styles.tab}>
          <img src={MypageIcon} alt="마이페이지" className={styles.icon} />
          <span>마이페이지</span>
        </Link>
      </nav>
    </footer>
  );
};


export default Footer;
