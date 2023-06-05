package com.chtulur.invoiceapp.service.implementation;

import com.chtulur.invoiceapp.dto.UserDTO;
import com.chtulur.invoiceapp.dto.dtomapper.UserDTOMapper;
import com.chtulur.invoiceapp.model.User;
import com.chtulur.invoiceapp.repo.UserRepository;
import com.chtulur.invoiceapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;

    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
