package com.wia1002.eGringottsBackEnd.service;



import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.wia1002.eGringottsBackEnd.model.UserAvatar;

public interface UserAvatarService {
    UserAvatar createUserAvatar(UserAvatar userAvatar);
    UserAvatar getUserAvatarById(String account_number);
    // UserAvatar updateUserAvatar(String account_number, UserAvatar updatedUserAvatar);

   



    


}
