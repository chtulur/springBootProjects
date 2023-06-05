package com.chtulur.invoiceapp.service;

import com.chtulur.invoiceapp.dto.UserDTO;
import com.chtulur.invoiceapp.model.User;

public interface UserService {
    UserDTO createUser(User user);
}
