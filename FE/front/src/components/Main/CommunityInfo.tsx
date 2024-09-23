import React from 'react';
import styles from '../../styles/Main/CommunityInfo.module.css';

interface CommunityInfoProps {
  data: {
    communitySortedByPopularities: {
      communityId: number;
      title: string;
      content: string;
      imagePath: string;
      heartCount: number;
      commentCount: number;
      writer: string;
      writerImagePath: string;
      registerDate: string;
    }[];
  };
}

const CommunityInfo: React.FC<CommunityInfoProps> = ({ data }) => {
  return (
    <div className={styles.communityInfoContainer}>
      <h2>인기 커뮤니티 게시물</h2>
      <ul className={styles.postList}>
        {data.communitySortedByPopularities.map((post) => (
          <li key={post.communityId} className={styles.postItem}>
            <img src={post.imagePath} alt={post.title} className={styles.postImage} />
            <div className={styles.postContent}>
              <h3>{post.title}</h3>
              <p>{post.content}</p>
              <div className={styles.authorInfo}>
                <img src={post.writerImagePath} alt={post.writer} className={styles.authorImage} />
                <span>{post.writer} - {new Date(post.registerDate).toLocaleDateString()}</span>
              </div>
              <div className={styles.postStats}>
                ❤️ {post.heartCount} | 💬 {post.commentCount}
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CommunityInfo;
