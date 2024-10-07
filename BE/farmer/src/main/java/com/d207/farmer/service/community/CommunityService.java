package com.d207.farmer.service.community;

import com.d207.farmer.domain.community.*;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.common.FileDirectory;
import com.d207.farmer.dto.community.*;
import com.d207.farmer.dto.survey.SurveyRegisterReRequestDTO;
import com.d207.farmer.dto.utils.OnlyId;
import com.d207.farmer.repository.community.*;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.utils.DateUtil;
import com.d207.farmer.utils.FileUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final CommunitySelectedTagRespository communitySelectedTagRepository;
    private final CommunityFavoriteTagRepository communityFavoriteTagRepository;
    private final DateUtil dateUtil;
    private final FileUtil fileUtil;


    public Page<CommunityResponseDTO> getCommunityWithHeart(Long userId, String filter, String search, Pageable pageable) {

        User user = userRepository.findById(userId).orElseThrow();
        // 커뮤니티를 하트 수로 가져오기
        Page<Community> communities;
        log.info("getCommunityWithHeart {}, {}", search, filter);




        // 검색어가 없는 경우
        if (search.isEmpty()) {
            // 1. 사용자 즐겨찾기 태그 가져오기
            List<CommunityFavoriteTag> communityTagId = communityFavoriteTagRepository.findByUser(user);

            if (communityTagId == null || communityTagId.isEmpty()) {
                // 전체 데이터 반환
                //communities = communityRepository.findAll(pageable); // 페이지네이션 적용

                List<CommunityTag> allTags = communityTagRepository.findAll();
                for (CommunityTag communityTag : allTags) {
                    communityTagId.add(new CommunityFavoriteTag(user,communityTag));

                }

            }

            List<Long> communityTagIds = communityTagId.stream()
                    .map(favoriteTag -> {
                        CommunityTag communityTag = favoriteTag.getCommunityTag();
                        if (communityTag == null) {
                            // 예외 처리 또는 로그 작성
                            // 예를 들어, 로그를 작성하거나 예외를 던질 수 있습니다.
                            //System.out.println("CommunityTag is null for favoriteTag: " + favoriteTag);
                            return null; // null을 반환
                        }
                        return communityTag.getId(); // ID 반환
                    })
                    .filter(Objects::nonNull) // null 값 필터링
                    .collect(Collectors.toList());
            // 하트 수가 있는 커뮤니티 조회
            communities = communityHeartRepository.findCommunitiesWithHearts(communityTagIds, pageable);

        } else {
            // 검색어가 있는 경우: 타이틀이나 콘텐츠에 검색어가 포함된 커뮤니티 조회
            communities = communityHeartRepository.findByTitleContainingOrContentContaining(search, pageable);
        }

        // DTO 변환
        return communities.map(community -> {
            // 커뮤니티에 대한 이미지 리스트 가져오기
            List<String> communityImages = communityImageRepository.findByCommunity(community)
                    .stream()
                    .map(CommunityImage::getImagePath)
                    .collect(Collectors.toList());

            // 커뮤니티에 대한 태그 리스트 가져오기
            List<CommunitySelectedTag> communityTags = communitySelectedTagRepository.findByCommunity(community);
            List<String> tagNames = communityTags.stream()
                    .map(selectedTag -> selectedTag.getCommunityTag().getTagName()) // CommunitySelectedTag에서 CommunityTag의 태그 이름 추출
                    .collect(Collectors.toList());

            Object countObject = communityHeartRepository.countByCommunity(community);
            long heartCount = countObject instanceof Number ? ((Number) countObject).longValue() : 0L; // null 체크 및 변환
            int heartCountint = (int) heartCount; // Long을 int로 변환


            // 커뮤니티 내용 제한 및 ... 추가
            String truncatedContent = community.getContent();
            if (truncatedContent.length() > 12) {
                truncatedContent = truncatedContent.substring(0, 12) + "...";
            }

            // DTO 생성
            return new CommunityResponseDTO(
                    community.getId(),
                    community.getUser().getId(),
                    community.getUser().getNickname(),
                      community.getUser().getImagePath(),

                    community.getTitle(),
                    communityImages,
                    truncatedContent,
                    community.getWriteDate(),
                    tagNames,
                    (int) heartCount, // int로 변환
                    community.getContent().length() // 또는 필요한 다른 정보를 사용
            );
        });


    }

    public Page<CommunityResponseDTO> getCommunityWithLatest(Long userId, String filter, String search, Pageable pageable) {
        log.info("getCommunityWithLatest {}, {}", search, filter);
        User user = userRepository.findById(userId).orElseThrow();

        // 날짜 기준으로 정렬할 Pageable 객체 생성
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("writeDate").descending());

        // 커뮤니티를 최신순으로 가져오기
        Page<Community> communities;
        

        // 1. 사용자 즐겨찾기 태그 가져오기
        //List<CommunityFavoriteTag> communityTagId = communityFavoriteTagRepository.findByUser(user);

        List<CommunityFavoriteTag> communityTagId = communityFavoriteTagRepository.findByUser(user);

        if (communityTagId == null || communityTagId.isEmpty()) {
            // 전체 커뮤니티 태그를 가져옵니다.
            List<CommunityTag> allTags = communityTagRepository.findAll();
            for (CommunityTag communityTag : allTags) {
                communityTagId.add(new CommunityFavoriteTag(user,communityTag));

            }
        }


        List<Long> communityTagIds = communityTagId.stream()
                .map(favoriteTag -> {
                    CommunityTag communityTag = favoriteTag.getCommunityTag();
                    if (communityTag == null) {
                        // 예외 처리 또는 로그 작성
                        // 예를 들어, 로그를 작성하거나 예외를 던질 수 있습니다.
                        System.out.println("CommunityTag is null for favoriteTag: " + favoriteTag);
                        return null; // null을 반환
                    }
                    return communityTag.getId(); // ID 반환
                })
                .filter(Objects::nonNull) // null 값 필터링
                .collect(Collectors.toList());

        // 3. 검색어가 없는 경우: 태그에 해당하는 커뮤니티 조회
        communities = communityRepository.findAllByOrderByWriteDateDesc(communityTagIds, sortedPageable); // 여기서 Page<Community>를 반환해야 함











        // 4. DTO 변환
    return communities.map(community -> {
        // 커뮤니티에 대한 이미지 리스트 가져오기
        List<String> communityImages = communityImageRepository.findByCommunity(community)
                .stream()
                .map(CommunityImage::getImagePath)
                .collect(Collectors.toList());

        // 커뮤니티에 대한 태그 리스트 가져오기
        List<CommunitySelectedTag> communityTags = communitySelectedTagRepository.findByCommunity(community);
        List<String> tagNames = communityTags.stream()
                .map(selectedTag -> selectedTag.getCommunityTag().getTagName()) // CommunitySelectedTag에서 CommunityTag의 태그 이름 추출
                .collect(Collectors.toList());


        // 커뮤니티 하트 수 가져오기
        // countByCommunity 메서드가 Object를 반환하는 경우
        Object countObject = communityHeartRepository.countByCommunity(community);
        long heartCount = countObject instanceof Number ? ((Number) countObject).longValue() : 0L; // null 체크 및 변환
        int heartCountint = (int) heartCount; // Long을 int로 변환

        // 커뮤니티 내용 제한 및 ... 추가
        String truncatedContent = community.getContent();
        if (truncatedContent.length() > 12) {
            truncatedContent = truncatedContent.substring(0, 12) + "...";
        }


        // DTO 생성
        return new CommunityResponseDTO(
                community.getId(),
                community.getUser().getId(),
                community.getUser().getNickname(),
                community.getUser().getImagePath(),
                community.getTitle(),
                communityImages,
                truncatedContent,
                community.getWriteDate(),
                tagNames,
                (int) heartCount, // int로 변환
                community.getContent().length() // 또는 필요한 다른 정보를 사용
        );
    });
    }

    @Transactional
    public Long registerCommunity(Long userId, CommunityRegisterDTO communityRegisterDTO) {


        User user = userRepository.findById(userId).orElseThrow();
        Community community = new Community(user, communityRegisterDTO.getCommunityTitle(), communityRegisterDTO.getCommunityContent());
        // 커뮤니티 저장 후 반환된 객체에서 ID를 가져옴
        Community savedCommunity = communityRepository.save(community);

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







        return savedCommunity.getId();
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

        if(community.isCheckDelete()){
            return null;
        }




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
            communityImagePathDto.add( FileDirectory.COMMUNITY.toString().toLowerCase() + "/" +communityImage.getImagePath());
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
    public String communityOneModifyRequest(Long userId, Long id, CommunityOneModifyRequestDTO communityOneModifyRequestDTO) {

        Community community = communityRepository.findByIdWithUser(id).orElseThrow();

        boolean checkMyarticle = community.getUser().getId().equals(userId);
        if(checkMyarticle){
            // 삭제할 이미지 먼저 삭제처리!
            List<String> imagePathdtos = communityOneModifyRequestDTO.getCommunityImageSubtractPaths();

            for(String imagePathdto : imagePathdtos){
                CommunityImage communityImage = communityImageRepository.findByImagePath(imagePathdto.replace("community/",""));

                // 조회된 객체가 존재하면 삭제합니다.
                if (communityImage != null) {
                    communityImageRepository.delete(communityImage);
                }
            }



//            for(MultipartFile Singlepartfile : communityOneModifyRequestDTO.getFiles()){
//                String imagePathdto = fileUtil.uploadFile(Singlepartfile, FileDirectory.COMMUNITY);
//
//                  communityImageRepository.save(new CommunityImage(community, imagePathdto));
//            }


            List<String> tagpath = communityOneModifyRequestDTO.getCommunityTagSubtractList();
            for(String tag : tagpath){
                // 태그 이름으로 CommunityTag 객체를 조회합니다.
                CommunityTag communityTag = communityTagRepository.findByTagName(tag);
                CommunitySelectedTag communitySelectedTag = communitySelectedTagRepository.findByCommunityAndCommunityTag(community, communityTag);

                // 조회된 객체가 존재하면 삭제합니다.
                if (communitySelectedTag != null) {
                    communitySelectedTagRespository.delete(communitySelectedTag);
                }
            }
            tagpath = communityOneModifyRequestDTO.getCommunityTagAddList();
            for(String tag : tagpath){
                communityTagRepository.save(new CommunityTag(tag));

            }

            // CommunityTagRepository에서 태그 목록을 가져오기
            List<CommunityTag> communityTags = communityTagRepository.findBytagNameIn(tagpath);

            // community_selected_tag 안에 tag 집어넣기!
            for(CommunityTag communityTag : communityTags){
                communitySelectedTagRespository.save(new CommunitySelectedTag(community, communityTag));
            }

            community.setTitle(communityOneModifyRequestDTO.getCommunityTitle() );
            community.setContent(communityOneModifyRequestDTO.getCommunityContent());
        }
        return "success Modify Article";
    }

    //public List<communityAllTagsResponseDTO> getCommunityAllTags(Long userId) {
    public List<communityAllTagsResponseDTO> getCommunityAllTags(Long userId) {
        // 사용자 조회
        User user = userRepository.findById(userId).orElseThrow();

        // 모든 커뮤니티 태그 조회
        List<CommunityTag> communityTags = communityTagRepository.findAll();
        List<Long> communityFavoriteTags = communityFavoriteTagRepository.findTop5FavoriteTagIds();
        List<communityAllTagsResponseDTO> communityAllTagsResponseDTOs = new ArrayList<>();

        // 사용자의 즐겨찾기 태그 조회
        List<CommunityFavoriteTag> favoriteTags = communityFavoriteTagRepository.findByUser(user);
        // 즐겨찾기 태그의 ID를 Set으로 변환하여 빠르게 조회
        Set<Long> favoriteTagIds = favoriteTags.stream()
                .map(favoriteTag -> favoriteTag.getCommunityTag() != null ? favoriteTag.getCommunityTag().getId() : null)
                .filter(id -> id != null) // null 체크
                .collect(Collectors.toSet());

        // 모든 커뮤니티 태그를 DTO로 변환 및 선택 여부 설정
        for (CommunityTag tag : communityTags) {
            communityAllTagsResponseDTO dto = new communityAllTagsResponseDTO(tag.getId(), tag.getTagName());
            dto.setSelected(favoriteTagIds.contains(tag.getId())); // 즐겨찾기 여부 설정

            // communityFavoriteTags에 tag.getId()가 포함되어 있으면 setPopular 실행
            if (communityFavoriteTags.contains(tag.getId())) {
                dto.setPopular(true); // setPopular 메서드를 호출하는 대신 필드를 직접 설정
            }

            communityAllTagsResponseDTOs.add(dto);
        }
        return communityAllTagsResponseDTOs;

    }


    @Transactional
    public String reRegisterSurvey(Long userId, SurveyRegisterReRequestDTO surveyRegisterReRequestDTO) {

        // 사용자 조회
        User user = userRepository.findById(userId).orElseThrow();

        if(surveyRegisterReRequestDTO.getDeltags().size()>0) {
        for (OnlyId delTag : surveyRegisterReRequestDTO.getDeltags()) {
            // 커뮤니티 태그 ID로 CommunityFavoriteTag 조회
            CommunityTag communityTag = communityTagRepository.findById(delTag.getId()).orElseThrow(() -> new EntityNotFoundException("CommunityTag not found"));
            ;
            CommunityFavoriteTag favoriteTag = communityFavoriteTagRepository.findByUserAndCommunityTag(user, communityTag);
            communityFavoriteTagRepository.delete(favoriteTag);
        }
    }

        if(surveyRegisterReRequestDTO.getPlustags().size()>0) {
            for (OnlyId plusTag : surveyRegisterReRequestDTO.getPlustags()) {
                // 커뮤니티 태그 ID로 CommunityFavoriteTag 조회
                CommunityTag communityTag = communityTagRepository.findById(plusTag.getId()).orElseThrow(() -> new EntityNotFoundException("CommunityTag not found"));
                ;

                communityFavoriteTagRepository.save(new CommunityFavoriteTag(user, communityTag));
            }
        }

        return "Change success";

    }


    @Transactional
    public String registerCommunityImage(Long userId, Long communityId, List<MultipartFile> file) {

        Community community = communityRepository.findById(communityId).orElseThrow();
        if(community.getUser().getId().equals(userId)){
        for( MultipartFile file2: file) {
            String userimagepath = fileUtil.uploadFile(file2,FileDirectory.COMMUNITY);
            communityImageRepository.save(new CommunityImage(community,userimagepath ));

        }
            return "Success";
            }
        else{
            return "No image";
        }


    }
}
