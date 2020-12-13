package application.service;

import application.entity.RoleEntity;
import application.entity.UserEntity;
import application.repository.IRoleRepository;
import application.repository.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermitService {
    private final IUserRepository iUserRepository;
    private final UserService userService;
    private final IRoleRepository roleRepository;

    public PermitService(IUserRepository iUserRepository, UserService userService, IRoleRepository roleRepository) {
        this.iUserRepository = iUserRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public Map<Long, List<RoleEntity>> getPermit() {
        Map<Long, List<RoleEntity>> mapRole = new HashMap<>();
        List<UserEntity> userEntities = iUserRepository.findAll();
        if (!CollectionUtils.isEmpty(userEntities)) {
            userEntities.forEach(userEntity -> {
                List<RoleEntity> roleEntities = userService.getListRoleActiveOfUser(userEntity.getId());
                mapRole.put(userEntity.getId(), roleEntities);
            });
        }
        return mapRole;
    }

    public List<RoleEntity> getAllRole() {
        return roleRepository.findAll();
    }

    public List<UserEntity> getAllUSer() {
        return iUserRepository.findAll();
    }
}
