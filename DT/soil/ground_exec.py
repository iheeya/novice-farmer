from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager
import time

# ChromeOptions 설정
chrome_options = Options()

# ChromeDriver 설정
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)


# 페이지 로드
driver.get("http://soil.rda.go.kr/soil/chart/chart.jsp")

# 시도 선택
sido_select = Select(driver.find_element(By.ID, "sido_cd_mini"))
sido_options = [option.get_attribute("value") for option in sido_select.options if option.get_attribute("value")]

for sido_value in sido_options:
    sido_select.select_by_value(sido_value)
    time.sleep(2)  # 페이지가 변경되는 시간이 필요할 수 있으니 대기

    # 시군구 선택
    sgg_select = Select(driver.find_element(By.ID, "sgg_cd_mini"))
    sgg_options = [option.get_attribute("value") for option in sgg_select.options if option.get_attribute("value")]

    for sgg_value in sgg_options:
        sgg_select.select_by_value(sgg_value)
        time.sleep(2)  # 대기

        # 읍면동 선택
        umd_select = Select(driver.find_element(By.ID, "umd_cd_mini"))
        umd_options = [option.get_attribute("value") for option in umd_select.options if option.get_attribute("value")]

        for umd_value in umd_options:
            umd_select.select_by_value(umd_value)
            time.sleep(2)  # 대기

            # 리 선택
            ri_select = Select(driver.find_element(By.ID, "ri_cd_mini"))
            ri_options = [option.get_attribute("value") for option in ri_select.options if option.get_attribute("value")]

            for ri_value in ri_options:
                ri_select.select_by_value(ri_value)
                time.sleep(2)  # 선택 후 대기

# 드라이버 종료
driver.quit()
