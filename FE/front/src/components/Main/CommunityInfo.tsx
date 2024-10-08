import React, { useState, useEffect } from 'react';
import { useSwipeable } from 'react-swipeable';
import styles from '../../styles/Main/CommunityInfo.module.css';

interface CommunityInfoProps {
  data: {
    tagName: string;
    communitySortedByPopularities: {
      communityId: number;
      title: string;
      content: string;
      imagePath: string | null; 
      heartCount: number;
      commentCount: number;
      writer: string;
      writerImagePath: string;
      registerDate: string;
    }[];
    communitySortedByRecents: {
      communityId: number;
      title: string;
      content: string;
      imagePath: string | null; 
      heartCount: number;
      commentCount: number;
      writer: string;
      writerImagePath: string;
      registerDate: string;
    }[];
  };
}

const CommunityInfo: React.FC<CommunityInfoProps> = ({ data }) => {
  const [activeTab, setActiveTab] = useState<'popular' | 'recent'>('popular');
  const [currentPostIndex, setCurrentPostIndex] = useState(0);
  const posts = activeTab === 'popular' ? data.communitySortedByPopularities : data.communitySortedByRecents;
  const currentPost = posts[currentPostIndex];

  const handleNextPost = () => {
    setCurrentPostIndex((prevIndex) => (prevIndex + 1) % posts.length);
  };

  const handlePrevPost = () => {
    setCurrentPostIndex((prevIndex) => (prevIndex - 1 + posts.length) % posts.length);
  };

  // 스와이프 핸들러 설정 (좌우 스와이프만 감지)
  const swipeHandlers = useSwipeable({
    onSwipedLeft: handleNextPost,
    onSwipedRight: handlePrevPost,
    preventScrollOnSwipe: true, // 터치 시 스크롤 방지
    delta: { left: 10, right: 10, up: 100, down: 100 }, // 상하 방향 스와이프 민감도는 크게 설정
    trackMouse: true, // 마우스 스와이프 감지
  });

  // 5초마다 자동으로 다음 게시글로 이동
  useEffect(() => {
    const interval = setInterval(() => {
      handleNextPost();
    }, 5000); // 5초마다 자동으로 다음 게시글로 이동

    return () => clearInterval(interval); // 컴포넌트 언마운트 시 타이머 제거
  }, [currentPostIndex]);

  return (
    <div className={styles.communityInfoContainer}>
      {/* 상단 # 태그 및 탭 */}
      <div className={styles.header}>
        <h2># {data.tagName}</h2>
        <div className={styles.tabs}>
          <button 
            className={activeTab === 'popular' ? styles.activeTab : ''} 
            onClick={() => { setActiveTab('popular'); setCurrentPostIndex(0); }}
          >
            인기순
          </button>
          | 
          <button 
            className={activeTab === 'recent' ? styles.activeTab : ''} 
            onClick={() => { setActiveTab('recent'); setCurrentPostIndex(0); }}
          >
            최신순
          </button>
        </div>
      </div>

      {/* 게시글 */}
      <div {...swipeHandlers} className={styles.postWrapper}>
        <img 
          src={currentPost.imagePath || 'https://via.placeholder.com/150'} // 이미지 경로가 null이면 기본 이미지 사용
          alt={currentPost.title} 
          className={styles.postImage} 
        />
        <div className={styles.postContent}>
          <h3>{currentPost.title}</h3>
          <p>{currentPost.content}</p>
          <div className={styles.authorPaginationWrapper}>
            <div className={styles.authorInfo}>
              <img src={currentPost.writerImagePath} alt={currentPost.writer} className={styles.authorImage} />
              <span>{currentPost.writer} - {new Date(currentPost.registerDate).toLocaleDateString()}</span>
            </div>
            <div className={styles.pagination}>
              {currentPostIndex + 1} / {posts.length}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CommunityInfo;
