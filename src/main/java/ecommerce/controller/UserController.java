package ecommerce.controller;

import ecommerce.dto.UserDto;
import ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
@PostMapping(value="/create")
    public UserDto createUser(@RequestBody UserDto userDto)
    {
        return userService.createUser(userDto);
    }
@PutMapping("/update")
public ResponseEntity<String> changePassword(@RequestParam(required = false) String phoneNumber, @RequestBody UserDto userDto)
{
    try {
        // Check if the user is an admin before allowing the password change
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        boolean isAdmin = authorities.stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
//        if (!isAdmin) {
//            // Return a custom message indicating that only admins can perform this action
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins are allowed to change passwords.");
//        }

        userService.changePassword(phoneNumber, userDto);
        return ResponseEntity.ok("Password updated successfully");


    }
//    catch (InvalidKeyException ex)
//    {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
//    }
    catch(IllegalArgumentException | EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating password");
    }

//    return userService.changePassword(phoneNumber, userDto);
}
}
