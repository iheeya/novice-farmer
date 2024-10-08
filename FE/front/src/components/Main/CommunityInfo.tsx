import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // useNavigate 임포트
import { useSwipeable } from 'react-swipeable';
import styles from '../../styles/Main/CommunityInfo.module.css';
import { GetImage } from '../../services/getImage'; // GetImage 함수 가져오기

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
  const navigate = useNavigate(); // useNavigate 사용
  const [activeTab, setActiveTab] = useState<'popular' | 'recent'>('popular');
  const [currentPostIndex, setCurrentPostIndex] = useState(0);
  const [postImages, setPostImages] = useState<string[]>([]);
  const [writerImages, setWriterImages] = useState<string[]>([]);

  const posts = activeTab === 'popular' ? data.communitySortedByPopularities : data.communitySortedByRecents;
  const currentPost = posts[currentPostIndex];

  // 이미지 가져오기
  useEffect(() => {
    const fetchImages = async () => {
      const postImagePromises = posts.map(async (post) => {
        if (post.imagePath) {
          return await GetImage(post.imagePath);
        }
        return 'https://via.placeholder.com/150'; // 기본 이미지
      });

      const writerImagePromises = posts.map(async (post) => {
        if (post.writerImagePath) {
          return await GetImage(post.writerImagePath);
        }
        return 'https://via.placeholder.com/50'; // 기본 작성자 이미지
      });

      const postImages = await Promise.all(postImagePromises);
      const writerImages = await Promise.all(writerImagePromises);

      setPostImages(postImages);
      setWriterImages(writerImages);
    };

    fetchImages();
  }, [posts]);

  const handleNextPost = () => {
    setCurrentPostIndex((prevIndex) => (prevIndex + 1) % posts.length);
  };

  const handlePrevPost = () => {
    setCurrentPostIndex((prevIndex) => (prevIndex - 1 + posts.length) % posts.length);
  };

  const handlePostClick = (communityId: number) => {
    navigate(`/community/${communityId}/detail`); // 게시글 클릭 시 해당 상세 페이지로 이동
  };

  // 스와이프 핸들러 설정
  const swipeHandlers = useSwipeable({
    onSwipedLeft: handleNextPost,
    onSwipedRight: handlePrevPost,
    preventScrollOnSwipe: true,
    delta: { left: 10, right: 10, up: 100, down: 100 },
    trackMouse: true,
  });

  // 5초마다 자동으로 다음 게시글로 이동
  useEffect(() => {
    const interval = setInterval(() => {
      handleNextPost();
    }, 5000);

    return () => clearInterval(interval);
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
      <div {...swipeHandlers} className={styles.postWrapper} onClick={() => handlePostClick(currentPost.communityId)}>
        <img 
          src={postImages[currentPostIndex] || 'https://via.placeholder.com/150'} 
          alt={currentPost.title} 
          className={styles.postImage} 
        />
        <div className={styles.postContent}>
          <h3>{currentPost.title}</h3>
          <p>{currentPost.content}</p>
          <div className={styles.authorPaginationWrapper}>
            <div className={styles.authorInfo}>
              <img 
                src={writerImages[currentPostIndex] || 'https://via.placeholder.com/50'} 
                alt={currentPost.writer} 
                className={styles.authorImage} 
              />
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
