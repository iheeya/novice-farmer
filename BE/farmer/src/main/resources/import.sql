INSERT INTO plant (plant_degree_day,plant_is_on,plant_id,plant_name) VALUES
                 (1980,1,1,'토마토'),
                 (2000,1,2,'고추'),
                 (2000,0,3,'상추'),
                 (2000,0,4,'깻잎'),
                 (2000,0,5,'배추');

update plant_seq set next_val = 101;

INSERT INTO place (place_is_on,place_id,place_desc,place_name) VALUES
                   (1,1,'베란다 접근성 미쳤고','베란다'),
                   (1,2,'주말농장 재밌음','주말농장'),
                   (0,3,'개인 땅이라니 부럽다','개인 텃밭'),
                   (0,4,'잼민이 주의','스쿨팜');

update place_seq set next_val = 101;

INSERT INTO weekend_farm (weekend_farm_id,weekend_farm_addr,weekend_farm_desc,weekend_farm_image_path,weekend_farm_latitude,weekend_farm_longitude,weekend_farm_name,weekend_farm_tel) VALUES
                       (1,'경북 구미시 산동읍 성수4길 65 쭌이네비닐하우스','성수리 현대정비에서 산길따라~~쭈우쭈욱 올라오세요^^ (현대정비를 왼쪽에두고 굴다리지남^^)','https://image.com/fdf','36.1573359','128.4209952','쭌이네, BINIL HAUS','0507-1317-4536'),
                       (2,'경북 구미시 지산양호9길 10','최원석, 배성연 부부가  땀으로 정직하게 농사짓고 있는  딸기, 토마토 농장 베리마토 입니다.   판매상품 당일 수확한 딸기 딸기 체험 주말 가족체험','https://blog.naver.com/seog1017','36.1358311','128.3632623','베리마토 딸기농장','010-2600-5603');

update weekend_farm_seq set next_val = 101;

INSERT INTO plant_growth_illust (plant_growth_illust_step,plant_growth_illust_id,plant_id,plant_growth_illust_image_path) VALUES
                                  (0,1,1,'sample illust image path for 토마토 0단계'),
                                  (1,2,1,'sample illust image path for 토마토 1단계'),
                                  (2,3,1,'sample illust image path for 토마토 2단계'),
                                  (3,4,1,'sample illust image path for 토마토 3단계'),
                                  (0,5,2,'sample illust image path for 고추 0단계'),
                                  (1,6,2,'sample illust image path for 고추 1단계'),
                                  (2,7,2,'sample illust image path for 고추 2단계'),
                                  (3,8,2,'sample illust image path for 고추 3단계'),
                                  (0,9,3,'sample illust image path for 상추 0단계'),
                                  (1,10,3,'sample illust image path for 상추 1단계'),
                                  (2,11,3,'sample illust image path for 상추 2단계'),
                                  (3,12,3,'sample illust image path for 상추 3단계'),
                                  (0,13,4,'sample illust image path for 깻잎 0단계'),
                                  (1,14,4,'sample illust image path for 깻잎 1단계'),
                                  (2,15,4,'sample illust image path for 깻잎 2단계'),
                                  (3,16,4,'sample illust image path for 깻잎 3단계'),
                                  (0,17,5,'sample illust image path for 배추 0단계'),
                                  (1,18,5,'sample illust image path for 배추 1단계'),
                                  (2,19,5,'sample illust image path for 배추 2단계'),
                                  (3,20,5,'sample illust image path for 배추 3단계');

update plant_growth_illust_seq set next_val = 101;

INSERT INTO plant_threshold (plant_threshold_degree_day,plant_threshold_step,plant_id,plant_threshold_id) VALUES
                              (300,1,1,1),
                              (1000,2,1,2),
                              (1500,3,1,3),
                              (300,1,2,4),
                              (1000,2,2,5),
                              (1500,3,2,6),
                              (300,1,3,7),
                              (1000,2,3,8),
                              (1500,3,3,9),
                              (300,1,4,10),
                              (1000,2,4,11),
                              (1500,3,4,12),
                              (300,1,5,13),
                              (1000,2,5,14),
                              (1500,3,5,15);

update plant_threshold_seq set next_val = 101;

INSERT INTO user (user_age,user_allow_push,user_is_first_login,user_id,user_reg_date,user_addr,user_email,user_image_path,user_nickname,user_pwd,user_gender) VALUES
              (21,1,1,1,now(),'대구광역시 달서구','test1@email.com','','test1','1234','MALE'),
              (22,1,1,2,now(),'대구광역시 달서구','test2@email.com','','test2','1234','MALE'),
              (23,1,1,3,now(),'대구광역시 달서구','test3@email.com','','test3','1234','MALE'),
              (24,1,1,4,now(),'대구광역시 달서구','test4@email.com','','test4','1234','MALE'),
              (25,1,1,5,now(),'대구광역시 달서구','test5@email.com','','test5','1234','MALE'),
              (26,1,1,6,now(),'대구광역시 달서구','test6@email.com','','test6','1234','MALE'),
              (27,1,1,7,now(),'대구광역시 달서구','test7@email.com','','test7','1234','MALE'),
              (28,1,1,8,now(),'대구광역시 달서구','test8@email.com','','test8','1234','MALE'),
              (29,1,1,9,now(),'대구광역시 달서구','test9@email.com','','test9','1234','MALE'),
              (30,1,1,10,now(),'대구광역시 달서구','test10@email.com','','test10','1234','MALE'),
              (25,1,1,11,now(),'경상북도 구미시','farm1@email.com','','farmer1','1234','MALE'),
              (25,1,1,12,now(),'경상북도 구미시','farm2@email.com','','farmer2','1234','MALE'),
              (30,1,1,13,now(),'경상북도 포항시','mainpage@email.com','','mainpage','1234','MALE');

update user_seq set next_val = 101;

INSERT INTO favorite_plant (favorite_id,plant_id,user_id) VALUES
    (1,1,11);

update favorite_plant_seq set next_val = 101;

INSERT INTO favorite_place (favorite_id,place_id,user_id) VALUES
    (1,1,11);

update favorite_place_seq set next_val = 101;

INSERT INTO user_place (place_id,user_id,user_place_id,user_place_bname1,user_place_bname2,user_place_bunji,user_place_jibun,user_place_latitude,user_place_longitude,user_place_name,user_place_sido,user_place_sigugun,zonecode) VALUES
                       (1,11,1,'','임수동','94-1','경북 구미시 임수동 94-1','36.1081929','128.4139754','베란다','경북','구미시','39388'),
                       (2,11,2,'','임수동','94-1','경북 구미시 임수동 94-1','36.1081929','128.4139754','주말농장','경북','구미시','39388'),
                       (3,11,3,'','임수동','94-1','경북 구미시 임수동 94-1','36.1081929','128.4139754','개인 텃밭','경북','구미시','39388');

update user_place_seq set next_val = 101;

INSERT INTO farm (farm_degree_day,farm_is_completed,farm_is_deleted,farm_is_harvest,farm_complete_date,farm_create_date,farm_delete_date,farm_harvest_date,farm_id,farm_seed_date,plant_id,user_id,user_place_id,farm_memo,farm_plant_name) VALUES
                (1000,0,0,0,NULL,now(),NULL,NULL,1,now(),1,11,1,'토마토 냠냠','토순이'),
                (0,1,0,1,'2024-09-25 17:03:06.531363',now(),NULL,'2024-09-25 17:03:06.531363',2,'2024-09-23 17:03:06.531363',2,11,2,'작은 고추가 매움','작매고'),
                (0,0,0,0,NULL,now(),NULL,NULL,3,now(),1,11,3,'쌈쌈','상충');

update farm_seq set next_val = 101;

INSERT INTO farm_todo (farm_todo_is_completed,farm_id,farm_todo_complete_date,farm_todo_date,farm_todo_id,farm_todo_type) VALUES
                      (0,1,NULL,date_add(now(), INTERVAL 1 DAY),1,'WATERING'),
                      (0,1,NULL,date_add(now(), INTERVAL 6 DAY),2,'FERTILIZERING');

update farm_todo_seq set next_val = 101;

INSERT INTO community_tag (community_tag_id,community_tag_name) VALUES
                            (1,'토마토'),
                            (2,'고추'),
                            (3,'상추'),
                            (4,'깻잎'),
                            (5,'배추'),
                            (6,'베란다'),
                            (7,'주말농장'),
                            (8,'개인 텃밭'),
                            (9,'스쿨팜');

update community_tag_seq set next_val = 101;

INSERT INTO community (community_check_delete,community_date,community_id,user_id,community_content,community_title) VALUES
                     (0,date_sub(now(), INTERVAL 10 second),1,11,'토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다','토마토는 맛있다1'),
                     (0,date_sub(now(), INTERVAL 8 second),2,11,'토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다','토마토는 맛있다2'),
                     (0,date_sub(now(), INTERVAL 6 second),3,11,'토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다','토마토는 맛있다3'),
                     (0,date_sub(now(), INTERVAL 4 second),4,11,'토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다','토마토는 맛있다4'),
                     (0,date_sub(now(), INTERVAL 2 second),5,11,'토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다 토마토는 맛있다','토마토는 맛있다5');

update community_seq set next_val = 101;

INSERT INTO community_selected_tag (community_id,community_selected_tag,community_tag_id) VALUES
                                  (1,1,1),
                                  (2,2,1),
                                  (3,3,1),
                                  (4,4,1),
                                  (5,5,1);

update community_selected_tag_seq set next_val = 101;

INSERT INTO community_heart (community_favorite_tag_id,community_heart_date,community_id,user_id) VALUES
                          (1,now(),1,11),
                          (2,now(),2,11),
                          (3,now(),2,11),
                          (4,now(),3,11),
                          (5,now(),3,11),
                          (6,now(),3,11),
                          (7,now(),4,11),
                          (8,now(),4,11),
                          (9,now(),4,11),
                          (10,now(),4,11),
                          (11,now(),5,11),
                          (12,now(),5,11),
                          (13,now(),5,11),
                          (14,now(),5,11),
                          (15,now(),5,11);

update community_heart_seq set next_val = 101;
