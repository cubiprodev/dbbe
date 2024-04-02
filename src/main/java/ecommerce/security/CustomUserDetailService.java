package ecommerce.security;

import ecommerce.entity.User;
import ecommerce.exception.ResourceNotFoundException;
import ecommerce.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // loading user from database by username
        User user = this.userRepo.findByPhoneNumber(username)
                .orElseThrow(() -> new ResourceNotFoundException("User ", " phoneNumber : " + username, 0));

        return user;
    }
}