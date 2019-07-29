package vklindukhov.authenticator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final SecurityGroupRepository securityGroupRepository;

    @Override
    public UserDetails loadUserByUsername(String name) {
        return Optional
                .of(userRepository.findByUsername(name))
                .filter(users -> !users.isEmpty())
                .map(users -> users.get(0))
                .map(user -> new UserDetailsEntity(user, securityGroupRepository.listUserGroups(user.getCompanyId(), user)))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find the user " + name));
    }
}
