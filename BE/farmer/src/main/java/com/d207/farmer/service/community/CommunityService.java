package com.d207.farmer.service.community;

import com.d207.farmer.domain.community.Community;
import com.d207.farmer.dto.community.CommunityResponseDTO;
import com.d207.farmer.repository.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

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
}
