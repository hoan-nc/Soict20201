package application.config;

import application.entity.RoleEntity;
import application.entity.UserEntity;
import application.repository.IUserRepository;
import application.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailAuthenticatorService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final UserService userService;

    public UserDetailAuthenticatorService(IUserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByUserNameIgnoreCase(s);
        if (optional.isPresent()) {

            boolean isActive = true;
            UserEntity userEntity = optional.get();
            List<RoleEntity> listActiveRoleEntities = userService.getListRoleActiveOfUser(userEntity.getId());
            if (listActiveRoleEntities.size() == 0) {
                isActive = false;
            }

            // roles set
            Set<GrantedAuthority> setAuths = new HashSet<>();
            for (RoleEntity roleEntity : listActiveRoleEntities) {
                setAuths.add(new SimpleGrantedAuthority(roleEntity.getRoleName()));
            }

            return new org.springframework.security.core.userdetails.User(s, userEntity.getPasswordHash(),
                    isActive, true, true, true, setAuths);
        }
        throw new UsernameNotFoundException(s + " not found");
    }
}