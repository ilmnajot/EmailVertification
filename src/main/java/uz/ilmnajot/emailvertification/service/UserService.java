package uz.ilmnajot.emailvertification.service;

import uz.ilmnajot.emailvertification.dto.*;
import uz.ilmnajot.emailvertification.message.ApiResponse;

public interface UserService {
    UserDto registerUser(UserForm form);

    UserDto registerAdmin(UserForm form);

    ConfirmDto confirmLink(String encoded);

    ApiResponse setPassword(ConfirmDto confirmDto);

    LoginDto login(LoginForm form);
}
