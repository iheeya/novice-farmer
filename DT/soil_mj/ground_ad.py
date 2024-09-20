import csv
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

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
sido_options = [option for option in sido_select.options if option.get_attribute("value")]

# CSV 파일 열기
csv_filename = "soil_analysis_results_kbd.csv"
with open(csv_filename, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    # 헤더 작성
    writer.writerow(["지역", "작물", "최적지", "적지", "가능지", "저위생산지", "기타", "합계"])

    # 시도, 시군구, 읍면동, 리를 선택 후 데이터 추출
    for sido_option in sido_options:
        sido_select.select_by_value(sido_option.get_attribute("value"))
        sido_text = sido_option.text.strip()  # 시도 텍스트 값 추출
        time.sleep(2)

        # 시군구 선택
        sgg_select = Select(driver.find_element(By.ID, "sgg_cd_mini"))
        sgg_options = [option for option in sgg_select.options if option.get_attribute("value")]

        for sgg_option in sgg_options:
            sgg_select.select_by_value(sgg_option.get_attribute("value"))
            sgg_text = sgg_option.text.strip()  # 시군구 텍스트 값 추출
            time.sleep(2)

            # 읍면동 선택
            umd_select = Select(driver.find_element(By.ID, "umd_cd_mini"))
            umd_options = [option for option in umd_select.options if option.get_attribute("value")]

            for umd_option in umd_options:
                umd_select.select_by_value(umd_option.get_attribute("value"))
                umd_text = umd_option.text.strip()  # 읍면동 텍스트 값 추출
                time.sleep(2)

                # 리 선택
                ri_select = Select(driver.find_element(By.ID, "ri_cd_mini"))
                ri_options = [option for option in ri_select.options if option.get_attribute("value")]

                for ri_option in ri_options:
                    ri_select.select_by_value(ri_option.get_attribute("value"))
                    ri_text = ri_option.text.strip()  # 리 텍스트 값 추출
                    time.sleep(2)

                    # 지역 정보를 합쳐서 하나의 키로 만든다.
                    location = f"{sido_text} {sgg_text} {umd_text} {ri_text}"

                    # 작물 선택 후 데이터 추출
                    crops = ["오이", "토마토", "가지", "고추", "배추", "상추", "무", "옥수수", "콩", "감자", "고구마"]

                    for crop in crops:
                        try:
                            # 작물 선택 (예: '토마토' 클릭)
                            crop_element = driver.find_element(By.LINK_TEXT, crop)
                            crop_element.click()
                            time.sleep(2)  # 로딩 대기

                            # WebDriverWait을 통해 'm2soft-crownix-text' 내부가 로드될 때까지 기다림
                            try:
                                # 'm2soft-crownix-text' 내부의 데이터를 추출
                                wait = WebDriverWait(driver, 10)
                                data_div = wait.until(EC.presence_of_element_located((By.ID, "m2soft-crownix-text")))

                                # 최적지, 적지, 가능지, 저위생산지, 기타 항목 값 추출
                                try:
                                    ha_values = []
                                    
                                    # 각 값을 위치 기반으로 찾아서 텍스트를 추출합니다.
                                    ha_elements = driver.find_elements(By.XPATH, "//div[@style[contains(., 'top: 462px')]]")
                                    
                                    # 값을 리스트로 추가
                                    for ha_element in ha_elements:
                                        ha_value = ha_element.text.strip().replace(",", "")  # 쉼표 제거 후 저장
                                        ha_values.append(ha_value)
                                    
                                    # 최적지, 적지, 가능지, 저위생산지, 기타 값 추출 후 CSV 저장
                                    if len(ha_values) >= 6:
                                        writer.writerow([location, crop] + ha_values[:6])
                                        print(f"선택지역: {location}, 작물: {crop}, 데이터 저장 완료")

                                except Exception as e:
                                    print(f"표 데이터를 찾는 도중 오류 발생: {e}")

                            except Exception as e:
                                print(f"데이터 로딩 중 오류 발생: {e}")

                        except Exception as e:
                            print(f"작물 '{crop}' 선택 중 오류 발생: {e}")

# 드라이버 종료
driver.quit()
