import Swal, { SweetAlertResult } from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

// SweetAlert2 인스턴스 생성
const MySwal = withReactContent(Swal);

// 삭제 모달
export const showDeleteModal = (placeName: string, plantName: string, onDelete: () => void) => {
  MySwal.fire({
    title: '정말 이 작물을 삭제하시겠습니까?',
    text: `${placeName} - ${plantName}`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#e74c3c',
    cancelButtonColor: '#9AF531',
    confirmButtonText: '삭제',
    cancelButtonText: '취소',
  }).then((result: SweetAlertResult<any>) => {
    if (result.isConfirmed) {
      onDelete();
      MySwal.fire('삭제 완료', `${plantName} 작물이 삭제되었습니다.`, 'success');
    }
  });
};

// 첫수확 모달
export const showHarvestModal = (placeName: string, plantName: string, onHarvest: () => void) => {
  MySwal.fire({
    title: '첫 수확을 완료하시겠습니까?',
    text: `${placeName} - ${plantName}`,
    icon: 'question',
    showCancelButton: true,
    confirmButtonColor: '#4caf50',
    cancelButtonColor: '#9AF531',
    confirmButtonText: '첫 수확하기',
    cancelButtonText: '취소',
  }).then((result: SweetAlertResult<any>) => {
    if (result.isConfirmed) {
      onHarvest();
      MySwal.fire('수확 완료', `${plantName} 첫 수확을 완료했습니다.`, 'success');
    }
  });
};

// 재배 종료 모달
export const showEndGrowModal = (placeName: string, plantName: string, onEndGrow: () => void) => {
  MySwal.fire({
    title: '정말 재배를 종료하시겠습니까?',
    text: `${placeName} - ${plantName}`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#e74c3c',
    cancelButtonColor: '#9AF531',
    confirmButtonText: '재배종료하기',
    cancelButtonText: '취소',
  }).then((result: SweetAlertResult<any>) => {
    if (result.isConfirmed) {
      onEndGrow();
      MySwal.fire('재배 종료', `${plantName} 재배가 종료되었습니다.`, 'success');
    }
  });
};

// 물주기 모달
export const showWaterModal = (placeName: string, plantName: string, onWater: () => void) => {
  MySwal.fire({
    title: '물을 주시겠습니까?',
    text: `${placeName} - ${plantName}`,
    icon: 'info',
    showCancelButton: true,
    confirmButtonColor: '#4caf50',
    cancelButtonColor: '#9AF531',
    confirmButtonText: '물주기',
    cancelButtonText: '취소',
  }).then((result: SweetAlertResult<any>) => {
    if (result.isConfirmed) {
      onWater();
      MySwal.fire('물주기', `${plantName}에 물을 주었습니다.`, 'success');
    }
  });
};

// 비료주기 모달
export const showFertilizeModal = (placeName: string, plantName: string, onFertilize: () => void) => {
  MySwal.fire({
    title: '비료를 주시겠습니까?',
    text: `${placeName} - ${plantName}`,
    icon: 'info',
    showCancelButton: true,
    confirmButtonColor: '#4caf50',
    cancelButtonColor: '#9AF531',
    confirmButtonText: '비료주기',
    cancelButtonText: '취소',
  }).then((result: SweetAlertResult<any>) => {
    if (result.isConfirmed) {
      onFertilize();
      MySwal.fire('비료주기', `${plantName}에 비료를 주었습니다.`, 'success');
    }
  });
};
