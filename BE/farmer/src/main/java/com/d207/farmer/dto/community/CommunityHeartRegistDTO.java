package com.d207.farmer.dto.community;

import com.d207.farmer.domain.community.Community;
import com.d207.farmer.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityHeartRegistDTO {

    private Community community;
    private User user;

}
