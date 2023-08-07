package uz.ilmnajot.emailvertification.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ilmnajot.emailvertification.dto.*;
import uz.ilmnajot.emailvertification.entity.User;
import uz.ilmnajot.emailvertification.enums.RoleName;
import uz.ilmnajot.emailvertification.exception.AppException;
import uz.ilmnajot.emailvertification.message.ApiResponse;
import uz.ilmnajot.emailvertification.repository.UserRepository;
import uz.ilmnajot.emailvertification.security.JwtProvider;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Value("${hash.secret}")
    String hash;
    @Value("${email.base.url}")
    String url;

    @Override
    public UserDto registerAdmin(UserForm form) {
        if (!userRepository.existsUserByEmail(form.getEmail())) {
            return UserDto.userToUserDto(userRepository.save(User.builder()
                    .name(form.getName())
                    .lastName(form.getLastName())
                    .email(form.getEmail())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .role(RoleName.DIRECTOR)
                    .enabled(true)
                    .build()
            ));
        } else {
            throw new AppException("user already exists with email " + form.getEmail());
        }
    }
    @Override
    public LoginDto login(@NotNull LoginForm form) {
        if (!userRepository.existsUserByEmail(form.getEmail())) {
            throw new AppException("User " + form.getEmail() + " does not exist");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                form.getEmail(),
                form.getPassword()
        ));
        String token = jwtProvider.generateToken(form.getEmail());
        LoginDto dto = new LoginDto();
        dto.setToken(token);
        return dto;
    }


    @Override
    public UserDto registerUser(UserForm form) {
        if (userRepository.existsUserByEmail(form.getEmail())) {
            throw new AppException("user already exists with email " + form.getEmail());
        }
        String encoded = passwordEncoder.encode(form.getEmail() + hash);
        String link = url + encoded;
        mailService.sendMail(form.getEmail(), link);
        return addUserDB(form, RoleName.USER, encoded);
    }

    @Override
    public ConfirmDto confirmLink(String encoded) {
        if (!userRepository.existsUserBySendHashCode(encoded)) {
            throw new AppException("you are wrong way, please get contacted with admin or manager");
        }
        String confirmCode = UUID.randomUUID().toString();
        User user = new User();
        user.setConfirmCode(confirmCode);
        userRepository.save(user);
        return ConfirmDto.builder()
                .code(confirmCode)
                .build();
    }

    @Override
    public ApiResponse setPassword(ConfirmDto confirmDto) {
        User user = userRepository.findUserByConfirmCode(confirmDto.getCode());
        if (user == null) {
            throw new AppException("User not found");
        }
        user.setPassword(passwordEncoder.encode(confirmDto.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return new ApiResponse("password set successfully", true);
    }

    public UserDto addUserDB(UserForm form, RoleName role, String confirmCode) {
        return UserDto.userToUserDto(User.builder()
                .name(form.getName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .role(role)
                .confirmCode(confirmCode)
                .enabled(false)
                .build());
    }


}
