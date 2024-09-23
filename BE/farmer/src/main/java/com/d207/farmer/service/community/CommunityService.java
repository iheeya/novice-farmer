package com.d207.farmer.service.community;

import com.d207.farmer.domain.community.*;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.community.CommunityRegisterDTO;
import com.d207.farmer.dto.community.CommunityResponseDTO;
import com.d207.farmer.repository.community.*;
import com.d207.farmer.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityTagRepository communityTagRepository;
    private final CommunitySelectedTagRespository communitySelectedTagRespository;
    private final CommunityImageRepository communityImageRepository;
    private final CommunityHeartRepository communityHeartRepository;

    public List<CommunityResponseDTO> getCommunity() {
        List<Community> communities =communityRepository.findAll();
        List<CommunityResponseDTO> communityList = new ArrayList<>();

        for (Community community : communities) {
            CommunityResponseDTO dto = new CommunityResponseDTO(
                    community.getId(),
                    community.getUser().getId(),
                    community.getTitle(), // communityTitle
                    community.getContent(), // communitycontent
                    community.getWriteDate()
            );
            communityList.add(dto);
        }

        return communityList;
    }

    @Transactional
    public String registerCommunity(Long userId, CommunityRegisterDTO communityRegisterDTO) {


        User user = userRepository.findById(userId).orElseThrow();
        Community community = new Community(user, communityRegisterDTO.getCommunityTitle(), communityRegisterDTO.getCommunityContent());
        communityRepository.save(community);

        // CommunityTagRepository에서 태그 목록을 가져오기
        List<CommunityTag> communityTags = communityTagRepository.findBytagNameIn(communityRegisterDTO.getCommunityTagList());

        // id를 키로 하고 tagName을 값으로 하는 Map으로 변환
        Set<String> communityTagSet = communityTags.stream().map(CommunityTag::getTagName).collect(Collectors.toSet());

        for (String communitytagtem :communityRegisterDTO.getCommunityTagList()){
            //포함되지 않으면!! Community Tag 테이블에 save 하기
            if(!communityTagSet.contains(communitytagtem)){
                CommunityTag communityTag = new CommunityTag(communitytagtem);
                communityTagRepository.save(communityTag);
            }
        }
        communityTags = communityTagRepository.findBytagNameIn(communityRegisterDTO.getCommunityTagList());


        // community_selected_tag 안에 tag 집어넣기!
        for(CommunityTag communityTag : communityTags){
            communitySelectedTagRespository.save(new CommunitySelectedTag(community, communityTag));
        }

        // Community_image 테이블에 imagePath 집어넣기
        for(String imapgepath : communityRegisterDTO.getImagePath()){
            communityImageRepository.save(new CommunityImage(community, imapgepath));
        }



        return "register Success";
    }

    @Transactional
    public String registerHeart(Long userId, Long communityid) {

        User user = userRepository.findById(userId).orElseThrow();
        Community community = communityRepository.findById(communityid).orElseThrow();;
        CommunityHeart communityHeart = communityHeartRepository.findByCommunityAndUser(community, user).orElse(null);
        if(communityHeart !=null){
            communityHeartRepository.delete(communityHeart);
            return "delete Heart success";
        }
        else{
            communityHeartRepository.save(new CommunityHeart(community, user));
            return "register Heart success";
        }





    }
}
