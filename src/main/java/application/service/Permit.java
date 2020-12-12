package application.service;

import application.data.entity.Role;
import application.data.entity.User;
import application.data.repository.IRoleRepository;
import application.data.repository.IUserRepository;
import application.data.repository.IUserRoleRepository;
import application.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Permit {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    UserService userService;

    @Autowired
    IRoleRepository roleRepository;

    public Map getPermit(){
        Map<Integer, List<Role>> mapRole = new HashMap<Integer, List<Role>>();
        List<User> userLst = new ArrayList<User>();
        userLst = iUserRepository.getAll();
        for (int i = 0; i < userLst.size(); i++) {
            int userId = userLst.get(i).getUserId();
            List<Role> roles = userService.getActiveListRole(userId);
            mapRole.put(userId, roles);
        }
        return mapRole;
    }

    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }

    public List<User> getAllUSer(){
        return iUserRepository.getAll();
    }
}
