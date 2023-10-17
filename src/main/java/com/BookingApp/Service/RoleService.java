package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName (String roleName){
        return  roleRepository.findByName(roleName).get();
    }
}
