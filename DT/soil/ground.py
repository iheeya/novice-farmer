import cv2
import numpy as np
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import Select
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
import time
import csv

# ChromeOptions 설정
chrome_options = Options()

# ChromeDriver 설정
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)

# 웹 페이지 열기
driver.get("http://soil.rda.go.kr/geoweb/soilmain.do#")

# 페이지 로딩 대기
wait = WebDriverWait(driver, 10)

# 1번째 셀렉트 박스 선택 (경상북도)
sido_select = Select(wait.until(EC.presence_of_element_located((By.ID, "sido_sel"))))
sido_select.select_by_visible_text("경상북도")

# 2번째 셀렉트 박스 선택 (구미시)
sgg_select = Select(wait.until(EC.presence_of_element_located((By.ID, "sgg_sel"))))
sgg_select.select_by_visible_text("구미시")

# 읍면동 선택 (인의동)
emd_select = Select(wait.until(EC.presence_of_element_located((By.ID, "emd_sel"))))
emd_select.select_by_visible_text("인의동")

# 잠시 대기
time.sleep(2)

# 처리할 작물 목록
crop_list = {
    "과채류": ["토마토", "고추"],
    "경엽채류": ["상추", "대파"]
}

# 색상 분석을 위한 OpenCV 처리 함수
def analyze_image_color(image_path):
    image = cv2.imread(image_path)
    hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    # 색상 범위 정의 (주어진 색상 코드를 기반으로 수정)
    color_ranges = {
        "최적지": ([50, 100, 100], [70, 255, 255]),  # #028f02 (짙은 초록색)
        "적지": ([40, 100, 100], [70, 255, 255]),   # #20bf48 (밝은 초록색)
        "가능지": ([25, 150, 150], [35, 255, 255]),  # #d1f440 (노란색)
        "저위생산지": ([25, 0, 150], [35, 50, 255]),  # #ececb0 (연한 노란색)
        "기타": ([0, 0, 200], [180, 50, 255])  # #f0f0f0 (회색)
    }

    results = { "최적지": 0, "적지": 0, "가능지": 0, "저위생산지": 0, "기타": 0 }
    total_pixels = 0

    # 색상 범위에 따라 픽셀 수를 계산
    for category, (lower, upper) in color_ranges.items():
        lower = np.array(lower, dtype="uint8")
        upper = np.array(upper, dtype="uint8")

        mask = cv2.inRange(hsv, lower, upper)
        count = cv2.countNonZero(mask)
        results[category] = count
        total_pixels += count

    # 각 카테고리의 비율 계산
    for category in results:
        if total_pixels > 0:
            results[category] = (results[category] / total_pixels) * 100  # 비율로 변환
        else:
            results[category] = 0

    return results

# CSV 파일로 저장하는 함수
def save_to_csv(data, filename):
    with open(filename, mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(["지역", "작물", "최적지(%)", "적지(%)", "가능지(%)", "저위생산지(%)", "기타(%)"])
        for location, crops in data.items():
            for crop, values in crops.items():
                writer.writerow([location, crop, values["최적지"], values["적지"], values["가능지"], values["저위생산지"], values["기타"]])

# 분석 결과를 저장할 데이터 구조
results_data = {}

# 각 작물에 대해 순차적으로 처리
for category, crops in crop_list.items():
    for crop in crops:
        # 작물 선택
        emd_select.select_by_visible_text(crop)
        time.sleep(2)  # 페이지가 로드될 시간을 기다림

        # 지도 이미지 스크린샷 저장
        map_element = driver.find_element(By.CSS_SELECTOR, "CSS_SELECTOR_FOR_MAP")  # 실제 CSS 선택자 필요
        map_element.screenshot(f"{crop}_map.png")

        # OpenCV로 색상 분석
        color_results = analyze_image_color(f"{crop}_map.png")

        # 결과 저장
        location = "경상북도 구미시 인의동"
        if location not in results_data:
            results_data[location] = {}
        results_data[location][crop] = color_results

# 분석 결과를 CSV 파일로 저장
save_to_csv(results_data, "soil_analysis_results.csv")

# 브라우저 종료
driver.quit()
