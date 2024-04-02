package ecommerce.service;

import ecommerce.dto.UserDto;

import java.security.InvalidKeyException;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto changePassword(String phoneNumber, UserDto userDto) throws InvalidKeyException;
}
