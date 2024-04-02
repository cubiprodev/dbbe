package ecommerce.serviceImpl;

import ecommerce.dto.UserDto;
import ecommerce.entity.Role;
import ecommerce.entity.User;
import ecommerce.repository.UserRepo;
import ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.InvalidKeyException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
        User map = modelMapper.map(userDto, User.class);
        User save = userRepo.save(map);
        return modelMapper.map(save, UserDto.class);
    }

    @Override
    public UserDto changePassword(String phoneNumber, UserDto userDto) throws InvalidKeyException {
//        Optional<User> user = userRepo.findByPhoneNumber(phoneNumber);
//        if (user.isPresent()){
//            User user1=new User();
//            user.setPassword(userDto.getPassword());
//            User save = userRepo.save(user);
//            return save;
//        }


        Optional<User> userOptional = userRepo.findByPhoneNumber(phoneNumber);
        User user1 = userOptional.get();

        if (phoneNumber == null) {
            throw new IllegalArgumentException("Please provide a Phone Number.");
        }
//        Optional<User> userOptional = userRepo.findByPhoneNumber(phoneNumber);
        User user = userOptional.get();
        Role role = user.getRole();
        if (role != Role.ADMIN) {
            throw new AccessDeniedException("Access denied. Only administrators are allowed to change passwords.");
        }
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found for Phone Number: " + phoneNumber);
        }

//        User user = userOptional.get();

        // Update the profile with the data from profileDTO
        user.setPassword(userDto.getPassword());
//        user.setEmail(profileDTO.getEmail());

        user = userRepo.save(user);

        return modelMapper.map(user, UserDto.class);
    }
    }
