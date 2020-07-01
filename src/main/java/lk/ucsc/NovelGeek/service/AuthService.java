package lk.ucsc.NovelGeek.service;


import lk.ucsc.NovelGeek.model.UserDetails;
import lk.ucsc.NovelGeek.model.ConfirmationToken;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.request.UserSignUpModel;
import lk.ucsc.NovelGeek.model.response.AuthResponse;
import lk.ucsc.NovelGeek.model.response.UserResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.UserRepository;
import lk.ucsc.NovelGeek.repository.ConfirmationTokenRepository;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import lk.ucsc.NovelGeek.util.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepository userRepository;

    public UserResponse createUser(UserSignUpModel userDto) {
        if(authRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Email already exists, Please try a different email");
        }
        Users users = new Users();

        BeanUtils.copyProperties(userDto, users);
        users.setUsername(userDto.getUsername().toLowerCase());
        users.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        users.setRole("USER");
        users.setProvider("local");
        Users storedUsers = authRepository.save(users);

        UserDetails userDetails = new UserDetails();
        userDetails.setName(storedUsers.getUsername());
        userDetails.setUser(storedUsers);
        userRepository.save(userDetails);

        UserResponse returnUser = new UserResponse();
        BeanUtils.copyProperties(storedUsers, returnUser);


        ConfirmationToken confirmationToken = new ConfirmationToken(storedUsers);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(storedUsers.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("chand312902@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        return returnUser;
    }

    @Override
    public UserPrincipal loadUserByUsername(String email)  {
        Users users = authRepository.findByEmail(email);
        System.out.println(users.getEmail());
        if(users == null) {
            throw new RuntimeException("Username not found");
        }
        //return new User(auth.getEmail(), auth.getPassword(), new ArrayList<>());
        return UserPrincipal.create(users);
    }

    public AuthResponse login(UserSignInModel loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Users user = authRepository.findByEmail(loginRequest.getEmail());
            String token = jwtTokenUtil.generateToken(user);
            return new AuthResponse(token, loginRequest.getEmail());
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }

    }

    public void confirmUser(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            Users user = authRepository.findByEmail(token.getUser().getEmail());
            user.setVerified(true);
            authRepository.save(user);
        }
        else
        {
//            modelAndView.addObject("message","The link is invalid or broken!");
//            modelAndView.setViewName("error");
        }

    }

    public Object forgotPassword(String email) {
        Users existingUser = authRepository.findByEmail(email);
        if (existingUser != null) {
            // Create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

            // Save it
            confirmationTokenRepository.save(confirmationToken);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("test-email@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://localhost:4200/reset-password?token="+confirmationToken.getConfirmationToken());

            // Send the email
            emailSenderService.sendEmail(mailMessage);


        } else {

        }

        return null;
    }

    public void resetPassword(String password, String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            Users user = authRepository.findByEmail(token.getUser().getEmail());
            System.out.println(token.getUser().getEmail());
            user.setVerified(true);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            authRepository.save(user);
        } else {

        }

    }
}
