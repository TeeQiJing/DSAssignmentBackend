package com.wia1002.eGringottsBackEnd.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wia1002.eGringottsBackEnd.exception.ResourceNotFoundException;
import com.wia1002.eGringottsBackEnd.model.UserAvatar;
import com.wia1002.eGringottsBackEnd.repository.UserAvatarRepository;
import com.wia1002.eGringottsBackEnd.service.UserAvatarService;


import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserAvatarServiceImpl implements UserAvatarService {
    @Autowired
    private UserAvatarRepository userAvatarRepository;

   

    @Override
    public UserAvatar createUserAvatar(UserAvatar userAvatar) {
        UserAvatar savedUserAvatar = userAvatarRepository.save(userAvatar);
        return savedUserAvatar;
    }




       @Override
        public UserAvatar getUserAvatarById(String account_number){
        UserAvatar userAvatar = userAvatarRepository.findById(account_number).orElseThrow(
            () -> new RuntimeException("User Avatar is not exist with given Account Number : " + account_number)
        );

        return userAvatar;
    }


   




}
