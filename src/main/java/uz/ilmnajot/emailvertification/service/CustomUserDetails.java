package uz.ilmnajot.emailvertification.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.ilmnajot.emailvertification.entity.User;
import uz.ilmnajot.emailvertification.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetails implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(username);
        return optionalUser.map(User::new).orElse(null);
    }
}
