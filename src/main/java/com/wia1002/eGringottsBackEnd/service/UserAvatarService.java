package com.wia1002.eGringottsBackEnd.service;


import com.wia1002.eGringottsBackEnd.model.UserAvatar;

public interface UserAvatarService {
    UserAvatar createUserAvatar(UserAvatar userAvatar);
    UserAvatar getUserAvatarById(String account_number);
}
