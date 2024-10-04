import React, { useState, useEffect } from 'react';
import AWS from 'aws-sdk';

const ImageComponent: React.FC = () => {
  const [imageUrl, setImageUrl] = useState<string>('');

  useEffect(() => {
    // AWS SDK 구성
    AWS.config.update({
      accessKeyId: process.env.REACT_APP_AWS_ACCESS_KEY_ID || '',  // 환경변수에서 Access Key 가져오기
      secretAccessKey: process.env.REACT_APP_AWS_SECRET_ACCESS_KEY || '', // 환경변수에서 Secret Key 가져오기
      region: process.env.REACT_APP_AWS_REGION || ''  // 환경변수에서 리전 정보 가져오기
    });

    // S3 클라이언트 생성
    const s3 = new AWS.S3();
    const bucketName: string = process.env.REACT_APP_AWS_BUCKET_NAME || '';
    const imageName: string = 'user/농부여신.png';  // 실제로 가져올 이미지 파일 이름

    // S3에서 이미지 URL 가져오기
    const params: AWS.S3.GetObjectRequest = {
      Bucket: bucketName,
      Key: imageName,
    };
    // S3 URL을 받아오는 함수
    s3.getSignedUrl('getObject', params, (err, url) => {
      if (err) {
        console.error('Error fetching image:', err);
      } else if (url) {
        console.log('Image URL:', url);
        setImageUrl(url);
      }
    });
  }, []);

  return (
    <div>
      <h1>S3 Image Display</h1>
      {imageUrl ? (
        <img src={imageUrl} alt="S3 Image" style={{ width: '300px', height: 'auto' }} />
      ) : (
        <p>Loading image...</p>
      )}
    </div>
  );
};

export default ImageComponent;
