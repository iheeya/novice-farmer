import csv
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager

# ChromeOptions 설정
chrome_options = Options()

# ChromeDriver 설정
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)

# 페이지 로드
driver.get("http://soil.rda.go.kr/soil/chart/chart.jsp")

# 페이지 로드 대기
time.sleep(5)

# 시도 선택
sido_select = Select(driver.find_element(By.ID, "sido_cd_mini"))
sido_options = [option.get_attribute("value") for option in sido_select.options if option.get_attribute("value")]

# CSV 파일 열기
csv_filename = "soil_analysis_results.csv"
with open(csv_filename, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    # 헤더 작성
    writer.writerow(["선택지역", "작물", "최적지", "적지", "가능지", "저위생산지", "기타"])

    # 시도, 시군구, 읍면동, 리를 선택 후 데이터 추출
    for sido_value in sido_options:
        sido_select.select_by_value(sido_value)
        time.sleep(2)

        # 시군구 선택
        sgg_select = Select(driver.find_element(By.ID, "sgg_cd_mini"))
        sgg_options = [option.get_attribute("value") for option in sgg_select.options if option.get_attribute("value")]

        for sgg_value in sgg_options:
            sgg_select.select_by_value(sgg_value)
            time.sleep(2)

            # 읍면동 선택
            umd_select = Select(driver.find_element(By.ID, "umd_cd_mini"))
            umd_options = [option.get_attribute("value") for option in umd_select.options if option.get_attribute("value")]

            for umd_value in umd_options:
                umd_select.select_by_value(umd_value)
                time.sleep(2)

                # 리 선택
                ri_select = Select(driver.find_element(By.ID, "ri_cd_mini"))
                ri_options = [option.get_attribute("value") for option in ri_select.options if option.get_attribute("value")]

                for ri_value in ri_options:
                    ri_select.select_by_value(ri_value)
                    time.sleep(2)

                    # '과채'에서 '토마토', '오이', '가지', '고추' 항목을 선택
                    crops = ["토마토", "오이", "가지", "고추"]

                    for crop in crops:
                        try:
                            # 작물 선택 (예: '토마토' 클릭)
                            crop_element = driver.find_element(By.LINK_TEXT, crop)
                            crop_element.click()
                            time.sleep(2)  # 로딩 대기

                            # 선택 지역 정보 추출
                            try:
                                location_element = driver.find_element(By.XPATH, "//div[contains(@style, 'text-align: left')]")
                                location = location_element.text.strip()
                            except Exception as e:
                                print(f"선택지역 정보를 찾는 도중 오류 발생: {e}")
                                location = "정보 없음"

                            # 행정구역별 토양 적성 정보 추출 (최적지, 적지, 가능지, 저위생산지, 기타)
                            try:
                                table_element = driver.find_element(By.XPATH, "//table[@class='tbl_chart']")
                                rows = table_element.find_elements(By.TAG_NAME, "tr")
                                ha_values = [row.find_elements(By.TAG_NAME, "td")[1].text for row in rows if len(row.find_elements(By.TAG_NAME, "td")) > 1]

                                # CSV 파일에 기록
                                writer.writerow([location, crop] + ha_values[:5])  # 첫 5개 값만 기록
                                print(f"선택지역: {location}, 작물: {crop}, 데이터 저장 완료")

                            except Exception as e:
                                print(f"표 데이터를 찾는 도중 오류 발생: {e}")

                        except Exception as e:
                            print(f"작물 '{crop}' 선택 중 오류 발생: {e}")

# 드라이버 종료
driver.quit()
