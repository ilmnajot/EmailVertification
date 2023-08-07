package uz.ilmnajot.emailvertification.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.emailvertification.dto.*;
import uz.ilmnajot.emailvertification.message.ApiResponse;
import uz.ilmnajot.emailvertification.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerAdmin")
    public HttpEntity<UserDto> registerAdmin(@RequestBody UserForm form) {
        UserDto userDto = userService.registerAdmin(form);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public HttpEntity<LoginDto> login(@RequestBody LoginForm form){
        LoginDto login = userService.login(form);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public HttpEntity<UserDto> registerUser(@RequestBody UserForm form) {
        UserDto userDto = userService.registerUser(form);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("confirm-link")
    public HttpEntity<ConfirmDto> confirmLink(@RequestParam String encoded) {
        ConfirmDto confirmDto = userService.confirmLink(encoded);
        return ResponseEntity.ok(confirmDto);
    }

    @PostMapping("set-password")
    public HttpEntity<ApiResponse> setPassword(@RequestBody ConfirmDto confirmDto) {
        ApiResponse apiResponse = userService.setPassword(confirmDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
