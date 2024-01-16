package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName (String roleName){
        return  roleRepository.findByName(roleName).get();
    }

    public boolean existsByName(String user) {
        return roleRepository.existsByName(user);
    }

    public void saveRole(Role userRole) {
        roleRepository.save(userRole);
    }
}
