import AWS from "aws-sdk";

// AWS SDK 설정
AWS.config.update({
  accessKeyId: process.env.REACT_APP_AWS_ACCESS_KEY_ID || "", // 환경변수에서 Access Key 가져오기
  secretAccessKey: process.env.REACT_APP_AWS_SECRET_ACCESS_KEY || "", // 환경변수에서 Secret Key 가져오기
  region: process.env.REACT_APP_AWS_REGION || "", // 환경변수에서 리전 정보 가져오기
});

/**
 * S3에서 이미지 URL을 가져오는 함수
 * @param imageName - S3 버킷 내 이미지 경로 (예: "user/농부여신.png")
 * @returns S3에서 생성된 presigned URL을 반환 (비동기 함수)
 */
export async function GetImage(imageName: string): Promise<string> {
  const s3 = new AWS.S3();
  const bucketName: string = process.env.REACT_APP_AWS_BUCKET_NAME || "";
  
  // 파라미터 설정
  const params: AWS.S3.GetObjectRequest = {
    Bucket: bucketName,
    Key: imageName,
  };

  // `s3.getSignedUrl`을 Promise로 감싸서 비동기 처리를 수행
  return new Promise((resolve, reject) => {
    s3.getSignedUrl("getObject", params, (err, url) => {
      if (err) {
        console.error("Error fetching image:", err);
        reject(err); // 오류가 발생하면 reject 호출
      } else if (url) {
        resolve(url); // URL을 정상적으로 가져오면 resolve 호출
      }
    });
  });
}
