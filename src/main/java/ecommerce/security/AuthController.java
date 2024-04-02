package ecommerce.security;

import ecommerce.dto.ApiResponse;
import ecommerce.dto.ApiResponset;
import ecommerce.dto.UserDto;
import ecommerce.entity.User;
import ecommerce.repository.UserRepo;
import ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static ecommerce.entity.Role.USER;


@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService usersService;

//    @PostMapping("/login")
//    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
//        this.authenticate(request.getUsername(), request.getPassword());
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
//        String token = this.jwtTokenHelper.generateToken(userDetails);
//
//        JwtAuthResponse response = new JwtAuthResponse();
//        response.setToken(token);
//
//        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
//                password);
//
//        try {
//
//            this.authenticationManager.authenticate(authenticationToken);
//
//        } catch (BadCredentialsException e) {
//            System.out.println("Invalid Detials !!");
//            throw new ApiException("Invalid username or password !!");
//        }
//
//    }
//

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDto userDto) {
        Optional<User> existingUser = userRepo.findByPhoneNumber(userDto.getPhoneNumber());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse("User with this Phone Number already exists", false), HttpStatus.ALREADY_REPORTED);
        }

        if(userDto.getPhoneNumber().equals(null))
        {
            return new ResponseEntity<>(new ApiResponse("Please Enter an Phone Number", false), HttpStatus.EXPECTATION_FAILED);
        }
        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setPhoneNumber(userDto.getPhoneNumber());
        newUser.setPassword(userDto.getPassword());
        String encode = passwordEncoder.encode(userDto.getPassword());
        newUser.setPassword(encode);
//        if(Objects.equals(userDto.getRole(), "USER")) {
//            newUser.setRole(USER);
//        }
//        else {
//            newUser.setRole(ADMIN);
//        }
        newUser.setRole(USER);
        try {
            newUser = userRepo.save(newUser);
            return new ResponseEntity<>(new ApiResponse("Registration successful", true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("Failed to register the user", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponset<?>> createToken(@RequestBody JwtAuthRequest request) {
            try {
                this.authenticate(request.getUsername(), request.getPassword());
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
                String token = this.jwtTokenHelper.generateToken(userDetails);

                JwtAuthResponse response = new JwtAuthResponse();
                response.setToken(token);

                return new ResponseEntity<>(new ApiResponset<>(true, "Authentication successful", response), HttpStatus.OK);
            } catch (BadCredentialsException e) {
                // Handle invalid username or password
                return new ResponseEntity<>(new ApiResponset<>(false, "Invalid username or password", null), HttpStatus.UNAUTHORIZED);

            }
    }
    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        this.authenticationManager.authenticate(authenticationToken);
    }


    @PostMapping("/adminLogin")
    public ResponseEntity<ApiResponset<?>> createToken1(@RequestBody JwtAuthRequest request) {
        try {
            this.authenticate(request.getUsername(), request.getPassword());
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

            // Check if the user has the "admin" role
            if (userHasAdminRole(userDetails)) {
                String token = this.jwtTokenHelper.generateToken(userDetails);

                JwtAuthResponse response = new JwtAuthResponse();
                response.setToken(token);

                return new ResponseEntity<>(new ApiResponset<>(true, "Authentication successful", response), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponset<>(false, "You are not authorized to log in", null), HttpStatus.UNAUTHORIZED);
            }
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiResponset<>(false, "Invalid username or password", null), HttpStatus.UNAUTHORIZED);
        }
    }

    private void authenticate2(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        this.authenticationManager.authenticate(authenticationToken);
    }

    private boolean userHasAdminRole(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }


//    @PostMapping("/userLogin")
//    public ResponseEntity<ApiResponset<?>> createToken1(@RequestBody JwtAuthRequest request) {
//        try {
//            this.authenticate1(request.getUsername(), request.getPassword());
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
//
//            // Check if the user has the "admin" role
//            if (userHasUserRole(userDetails)) {
//                String token = this.jwtTokenHelper.generateToken(userDetails);
//
//                JwtAuthResponse response = new JwtAuthResponse();
//                response.setToken(token);
//
//                return new ResponseEntity<>(new ApiResponset<>(true, "Authentication successful", response), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new ApiResponset<>(false, "You are not authorized to log in", null), HttpStatus.UNAUTHORIZED);
//            }
//        } catch (BadCredentialsException e) {
//            return new ResponseEntity<>(new ApiResponset<>(false, "Invalid username or password", null), HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    private void authenticate1(String username, String password) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        this.authenticationManager.authenticate(authenticationToken);
//    }
//
    private boolean userHasUserRole(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
    }

}