package com.d207.farmer.service.community;

import com.d207.farmer.domain.community.*;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.community.*;
import com.d207.farmer.repository.community.*;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private final CommunityCommentRepository communityCommentRepository;
    private final DateUtil dateUtil;

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


    @Transactional
    public String deleteCommunityArticle(Long userId, Long id) {
        User user = userRepository.findById(userId).orElseThrow();
        Community community = communityRepository.findById(id).orElseThrow();

        // Community의 User와 주어진 User를 비교
        if (community.getUser().getId().equals(user.getId())) {
            community.setCheckDelete(true);
            return "delete Community success";
        } else {
            return "delete Community fail (Different User)";
        }
    }


    @Transactional
    public String registerCommunityComment(Long userId, Long id, CommunityCommentRegisterDTO communityCommentRegisterDTO) {
        Community community = communityRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        CommunityComment communityComment = new CommunityComment(community, user, communityCommentRegisterDTO.getCommentContent());
        communityCommentRepository.save(communityComment);

        return "Community Comment register Success";
    }


    public CommunityOneArticleResponseDTO getOneCommunity(Long userId, Long id) {

        User user = userRepository.findById(userId).orElseThrow();

        // 지연로딩!!!
//        Community community = communityRepository.findById(id).orElseThrow();
        Community community = communityRepository.findByIdWithUser(id).orElseThrow();




        String nicknamedto = community.getUser().getNickname();
        String imagePathdto = community.getUser().getImagePath();
        String communityTitledto = community.getTitle();
        //.substring(0,20)
        String communityContentdto = community.getContent();

        if (communityContentdto.length() > 15) {
            communityContentdto = communityContentdto.substring(0, 15); // 20글자만 잘라냄
            communityContentdto += "..."; // 예시로 '...' 추가
        }




        // 커뮤니티에 관련된 이미지를 조회
        List<CommunityImage> communityImages = communityImageRepository.findByCommunity(community);
        List<String> communityImagePath = new ArrayList<>();
        // 이미지 경로를 리스트에 추가
        for (CommunityImage communityImage : communityImages) {
            communityImagePath.add(communityImage.getImagePath());
        }

        List<CommunitySelectedTag> communitySelectedTags = communitySelectedTagRespository.findByCommunity(community);
        List<String> communityTagListdto = new ArrayList<>();
        // 커뮤니티 Tags의 tag를 추가!
        for (CommunitySelectedTag CommunitySelectedtagtem : communitySelectedTags) {
            communityTagListdto.add(CommunitySelectedtagtem.getCommunityTag().getTagName());
        }


        List<CommunityHeart> communityHearts = communityHeartRepository.findByCommunity(community);
        Long communtyHeartcountdto = (long) communityHearts.size();


        List<CommunityComment> communityComments = communityCommentRepository.findByCommunity(community);
        Long communityCommentcountdto = (long) communityComments.size();

        boolean checkIPushHeart = true;
        if(communityHeartRepository.findByCommunityAndUser(community, user).isEmpty()){
            checkIPushHeart=false;
        }




        boolean checkMyarticle = community.getUser().getId().equals(userId);

        // Community 객체에서 writeDate를 가져옵니다.
        LocalDateTime writeDate = community.getWriteDate();

        // 연도, 월, 일을 추출합니다.
        String year = String.valueOf(writeDate.getYear());
        String month = String.format("%02d", writeDate.getMonthValue()); // 2자리로 포맷팅
        String day = String.format("%02d", writeDate.getDayOfMonth()); // 2자리로 포맷팅

        CommunityOneArticleResponseDTO communityOneArticleResponseDTO = new CommunityOneArticleResponseDTO( nicknamedto, imagePathdto, communityTitledto, communityContentdto
                                                                                ,communityImagePath,  communityTagListdto, communtyHeartcountdto, communityCommentcountdto, checkIPushHeart, checkMyarticle, year, month, day);

        return communityOneArticleResponseDTO;
    }


    public List<CommunityCommentResponseDTO> responseCommunitycomment(Long userId, Long id) {

        User user = userRepository.findById(userId).orElseThrow();
        Community community = communityRepository.findById(id).orElseThrow();;
        List<CommunityComment> communityComments = communityCommentRepository.findByCommunity(community);
        List<CommunityCommentResponseDTO> communityCommentResponseDTOs = new ArrayList<>();

        // for 문을 사용하여 댓글을 순회하며 DTO에 추가
        for (CommunityComment comment : communityComments) {

            CommunityCommentResponseDTO dto = new CommunityCommentResponseDTO(
                comment.getUser().getNickname(),
                comment.getUser().getImagePath(),
                comment.getContent(),
                dateUtil.getTime(comment.getWriteDate())
            );

            communityCommentResponseDTOs.add(dto); // DTO 리스트에 추가
        }

        return communityCommentResponseDTOs;
    }


    public CommunityOneModifyResponseDTO responseCommunityOneInModity(Long userId, Long id) {
        //User user = userRepository.findById(userId).orElseThrow();
        // 지연로딩!!!
//        Community community = communityRepository.findById(id).orElseThrow();
        Community community = communityRepository.findByIdWithUser(id).orElseThrow();

        boolean checkMyarticle = community.getUser().getId().equals(userId);
        if(checkMyarticle){
        List<CommunityImage> communityImagePath =communityImageRepository.findByCommunity(community);
        List<String> communityImagePathDto = new ArrayList<>();
        for (CommunityImage communityImage : communityImagePath) {
            communityImagePathDto.add(communityImage.getImagePath());
        }

        List<CommunitySelectedTag> communitySelectedTags = communitySelectedTagRespository.findByCommunity(community);
        List<String> communityTagList = new ArrayList<>();

        for (CommunitySelectedTag communitySelectedTag : communitySelectedTags) {
            communityTagList.add(communitySelectedTag.getCommunityTag().getTagName());
        }


           return new CommunityOneModifyResponseDTO(community.getTitle(), community.getContent(), communityImagePathDto, communityTagList);
        }

        return  new CommunityOneModifyResponseDTO();
    }

    @Transactional
    public CommunityOneModifyRequestDTO communityOneModifyRequest(Long userId, Long id) {

        Community community = communityRepository.findByIdWithUser(id).orElseThrow();

        boolean checkMyarticle = community.getUser().getId().equals(userId);
        return null;
    }
}
