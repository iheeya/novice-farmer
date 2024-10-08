package com.d207.farmer.controller.community;

import com.d207.farmer.dto.community.*;


import com.d207.farmer.dto.survey.SurveyRegisterReRequestDTO;
import com.d207.farmer.service.community.CommunityService;
import com.d207.farmer.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommunityController {

    private final CommunityService communityService;
    private final JWTUtil jwtUtil;

    @GetMapping
    public ResponseEntity<Page<CommunityResponseDTO>> getCommunityNew(@RequestHeader("Authorization") String authorization,
                                                                      SearchCondition condition,
                                                                      Pageable pageable) {
        String filter = condition.getFilter();
        String search = condition.getSearch();
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] getCommunityNew");
        if(filter.equals("new")){
            return ResponseEntity.ok(communityService.getCommunityWithLatest(userId, filter, search, pageable));
        }
        else{
           return ResponseEntity.ok(communityService.getCommunityWithHeart(userId, filter, search, pageable));
        }
    }




    /**
     * 커뮤니티 Mypage 불러오기!
     */
    @GetMapping("/mypage")
    public ResponseEntity<Page<CommunityResponseDTO>> getMyPage(@RequestHeader("Authorization") String authorization,
                                                                      Pageable pageable) {

        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] getMyPage");

        return ResponseEntity.ok(communityService.getMyPage(userId, pageable));


    }

    /**
     * 커뮤니티 글 올리기
     * //@ModelAttribute FileUploadTestRequestDTO request
     */
    @PostMapping
    public ResponseEntity<Long> registerCommunity(@RequestHeader("Authorization") String authorization,
                                                  @RequestBody CommunityRegisterDTO communityRegisterDTO
                                                    ) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("CommunityController] registerCommunity {}", userId);
        return ResponseEntity.created(URI.create("/")).body(communityService.registerCommunity(userId, communityRegisterDTO));
    }

    /**
     * 커뮤니티 글 올리기
     * //@ModelAttribute FileUploadTestRequestDTO request
     */
    @PostMapping("image/{CommunityId}")
    public ResponseEntity<String> registerCommunityImage(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable Long CommunityId,
                                                    @ModelAttribute  List<MultipartFile> files) {

        if(files.isEmpty()){
            return  ResponseEntity.created(URI.create("/")).body("Image is null") ;
        }
        else {
            Long userId = jwtUtil.getUserId(authorization);
            return ResponseEntity.created(URI.create("/")).body(communityService.registerCommunityImage(userId, CommunityId, files));
        }

    }

    /**
     * 특정 게시물 불러오기!
     */
    @GetMapping("{id}")
    public ResponseEntity<CommunityOneArticleResponseDTO> getOneCommunity(@RequestHeader("Authorization") String authorization,
                                                                          @PathVariable Long id){
        Long userId = jwtUtil.getUserId(authorization);
        log.info("CommunityController] getOneCommunity {}", userId);
        return ResponseEntity.created(URI.create("/")).body(communityService.getOneCommunity(userId, id));
    }

    /**
     * 커뮤니티 좋아요 누르기! (처음누르면 좋아요 on! / 이미 눌렀는걸 눌렀으면 좋아요 off!)
     */
    @PostMapping("{id}")
    public ResponseEntity<String> registerHeart(@RequestHeader("Authorization") String authorization,
                                                @PathVariable Long id){
        Long userId = jwtUtil.getUserId(authorization);
        log.info("CommunityController] registerHeart {}", userId);
        return ResponseEntity.created(URI.create("/")).body(communityService.registerHeart(userId, id));
    }

    /**
     * 커뮤니티 게시물 삭제하기
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCommunityArticle(@RequestHeader("Authorization") String authorization,
                                                @PathVariable Long id){
        Long userId = jwtUtil.getUserId(authorization);
        log.info("CommunityController] deleteCommunityArticle {}", userId);
        return ResponseEntity.ok(communityService.deleteCommunityArticle(userId, id));
    }

    /**
     *  커뮤니티 특정 게시물의 댓글 작성
     */
    @PostMapping("{id}/all/comment")
    public ResponseEntity<String> registerCommunityComment(@RequestHeader("Authorization") String authorization,
                                                           @PathVariable Long id,
                                                           @RequestBody CommunityCommentRegisterDTO communityCommentRegisterDTO){
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] registerCommunityComment {} // {}", userId, communityCommentRegisterDTO.getCommentContent());
        return ResponseEntity.created(URI.create("/")).body(communityService.registerCommunityComment(userId, id, communityCommentRegisterDTO));
    }

    /**
     *  커뮤니티 댓글중 특정 게시물 삭제하기!
     */
    @DeleteMapping("{id}/all/comment/{commentId}")

    public ResponseEntity<String> deleteCommunityComment(@RequestHeader("Authorization") String authorization,
                                                           @PathVariable Long id,
                                                           @PathVariable Long commentId){
        Long userId = jwtUtil.getUserId(authorization);

        return ResponseEntity.created(URI.create("/")).body(communityService.deleteCommunityComment(userId, id, commentId));
    }


    /**
     *  커뮤니티 특정 게시물의 댓글 커뮤니티 특정 게시물의 전체 댓글보기
     */
    @GetMapping("{id}/all/comment")
    public ResponseEntity<List<CommunityCommentResponseDTO> > responseCommunityComment (@RequestHeader("Authorization") String authorization,
                                                                                        @PathVariable Long id)   {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] Get Community Comment {} ", userId);
        return ResponseEntity.ok(communityService.responseCommunityComment(userId, id));
    }

    /**
     *  커뮤니티 내가 글쓴거 수정하기 버튼 눌렀을때 가져오기!
     */
    @GetMapping("{id}/all/modify")
    public ResponseEntity<CommunityOneModifyResponseDTO> communityOneModify (@RequestHeader("Authorization") String authorization,
                                                                             @PathVariable Long id)   {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] Get communityOneModify {} ", userId);
        return ResponseEntity.ok(communityService.responseCommunityOneInModify(userId, id));

    }

    /**
     *  커뮤니티 내가 글쓴거 수정하기 버튼 눌렀을때 수정하기!
     */
    @PostMapping("{id}/all/modify")
    public ResponseEntity<String> communityOneModifyRequest (@RequestHeader("Authorization") String authorization,
                                                                                   @PathVariable Long id,
                                                             @ModelAttribute CommunityOneModifyRequestDTO communityOneModifyRequestDTO
                                                             )   {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] Get communityOneModifyRequest {} ", userId);
        return ResponseEntity.ok(communityService.communityOneModifyRequest(userId, id, communityOneModifyRequestDTO));
    }

    /**
     *  전체 태그 불러오기
     */
    @GetMapping("/tags/all")
    public ResponseEntity<List<communityAllTagsResponseDTO>> getCommunityAllTags (@RequestHeader("Authorization") String authorization){

        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] Get getCommunityAllTags {} ", userId);
        return ResponseEntity.ok(communityService.getCommunityAllTags(userId));
    }

    /**
     * 태그 변경하기 클릭
     */
    @PostMapping("/tags/all")
    public ResponseEntity<String> registerSurvey(@RequestHeader("Authorization") String authorization,
                                            @RequestBody SurveyRegisterReRequestDTO SurveyRegisterReRequestDTO) {

        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] Get registerSurvey {} ", userId);
        return ResponseEntity.created(URI.create("/survey")).body(communityService.reRegisterSurvey(userId, SurveyRegisterReRequestDTO));
    }


}
