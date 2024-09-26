package com.d207.farmer.utils;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.exception.FailedAuthorizationUserException;
import org.springframework.stereotype.Component;

@Component
public class UserAuthUtil {

    // FIXME spring security 도입 후 변경 필요?
    // FIXME spring interceptor로 구현 -> 원래 하려다가 security 도입하면 인가 방식이 달라질까봐 일단 노가다로 붙이기

    public boolean authorizationUser(Long userId, Object obj) {
        if(obj instanceof Farm farm) {
            if(isSameUserId(userId, farm.getUser().getId())) return true;
        } else if (obj instanceof UserPlace userPlace) {
            if(isSameUserId(userId, userPlace.getUser().getId())) return true;
        }
        throw new FailedAuthorizationUserException("잘못된 접근입니다(일치하지 않은 회원)");
    }
    private boolean isSameUserId(Long id1, Long id2) {
        return id1.equals(id2);
    }
}
