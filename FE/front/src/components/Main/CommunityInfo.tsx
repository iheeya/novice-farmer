import React from 'react';
import styles from '../../styles/Main/CommunityInfo.module.css'; // CSS 경로

interface CommunityInfoProps {
  data: {
    popularPosts: {
      image: string;
      title: string;
      content: string;
      author: string;
      authorImage: string;
      date: string;
    }[];
  };
}

const CommunityInfo: React.FC<CommunityInfoProps> = ({ data }) => {
  return (
    <div className={styles.communityInfoContainer}>
      <h2>인기 커뮤니티 게시물</h2>
      <ul className={styles.postList}>
        {data.popularPosts.map((post, index) => (
          <li key={index} className={styles.postItem}>
            <img src={post.image} alt={post.title} className={styles.postImage} />
            <div className={styles.postContent}>
              <h3>{post.title}</h3>
              <p>{post.content}</p>
              <div className={styles.authorInfo}>
                <img src={post.authorImage} alt={post.author} className={styles.authorImage} />
                <span>{post.author} - {post.date}</span>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CommunityInfo;
