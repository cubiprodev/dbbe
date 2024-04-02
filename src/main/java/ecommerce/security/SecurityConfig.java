package ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String [] PUBLIC_URLS= {
            "/login",
            "/signup",
            "/logout",
            "/userLogin",
            "/register",
            "/adminLogin"



    };


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
                csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(PUBLIC_URLS)
                .permitAll()
                .antMatchers(HttpMethod.GET)
                .permitAll()
        		.antMatchers("/user/**", "/product/**", "/order")
                .hasRole("ADMIN")
//                .antMatchers("/**" ).permitAll()
//                .hasRole("USER")
//                .antMatchers("/advertisements/**")
//                .hasRole("MANAGER")
                //  .antMatchers("/get").permitAll()
//                .antMatchers("/user/createUser", "/brands/getBrandInfo/**", "/brands/getInfo/**","/user/getInfo/allUsers/**","/categories/getAllCategory","/categories/getCategory/**","/city/getCityInfo/**","/city/getAllCity","/color/getAllColorList","/color/getColor/**","/comments/getAllComment","/comments/getComment/**","/coupon/getAllCoupon","/coupon/getCoupon/**","/productcolor/getAllProductColor","/productcolor/getProductColor/**","/product/getproductInfo/allUsers","/product/getproductInfo/**","/productsize/getAllProductSize","/productsize/getProductSize/**","/sales/getAll","/sales/getSale","/salesdetails/getAllsalesDetails","/salesdetails/getsalesDetails/**","/shipping/getshippingInfo/allUsers","/shipping/getshippingInfo/**","/size/getAllSize","/size/getSize/**")
                //.hasRole("USER")

                .anyRequest()
                .authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());
        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

        return defaultSecurityFilterChain;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}