import React, { useState } from 'react';
import styles from '../../styles/Detail/memo.module.css';
import writeIcon from '../../assets/icons/Write.png';
import checkIcon from '../../assets/icons/Check.png';
import { updateMemo } from '../../services/PlantDetail/PlantDetailPageApi';

interface MemoProps {
  memo: string;
  farmId: number;
  onMemoUpdate: (newMemo: string) => void;
}

const Memo: React.FC<MemoProps> = ({ memo, farmId, onMemoUpdate }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [tempMemo, setTempMemo] = useState(memo);
  const [errorMessage, setErrorMessage] = useState('');

  const handleSaveMemo = async () => {
    if (tempMemo.length > 100) {
      setErrorMessage('메모는 100자 이내로 입력해주세요.');
      return;
    }

    try {
      await updateMemo(farmId, tempMemo);  // 메모 수정 요청
      onMemoUpdate(tempMemo);  // 부모 컴포넌트에 업데이트된 메모 전달
      setIsEditing(false);
      setErrorMessage('');
    } catch (error) {
      console.error('메모 수정 실패:', error);
    }
  };

  // 메모 입력 시 실시간으로 100자 제한 체크
  const handleMemoChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const inputMemo = e.target.value;
    if (inputMemo.length > 100) {
      setErrorMessage('메모는 100자 이내로 입력해주세요.');
    } else {
      setErrorMessage('');  // 에러 메시지 초기화
    }
    setTempMemo(inputMemo);
  };

  return (
    <div className={styles.memoContainer}>
      <h3 className={styles.memoTitle}>작물 메모</h3>
      {isEditing ? (
        <div className={styles.memoInputWrapper}>
          <textarea
            value={tempMemo}
            onChange={handleMemoChange}
            className={styles.memoInput}
          />
          <img
            src={checkIcon}
            alt="Save"
            className={styles.icon}
            onClick={handleSaveMemo}
          />
        </div>
      ) : (
        <div className={styles.memoDisplay}>
          <p>{memo}</p>
          <img
            src={writeIcon}
            alt="Edit"
            className={styles.icon}
            onClick={() => setIsEditing(true)}
          />
        </div>
      )}
      {errorMessage && <p className={styles.errorMessage}>{errorMessage}</p>}
    </div>
  );
};

export default Memo;
