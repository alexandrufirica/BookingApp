package com.BookingApp.Data;


import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleService roleService;

    @Autowired
    public DataLoader(RoleService roleService) {
        this.roleService = roleService;
    }
    @Override
    public void run(String... args) throws Exception {
        if(!roleService.existsByName("ROLE_USER")){
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleService.saveRole(userRole);
        }

        if(!roleService.existsByName("ROLE_MANAGER")){
            Role medicRole = new Role();
            medicRole.setName("ROLE_MANAGER");
            roleService.saveRole(medicRole);
        }

        if(!roleService.existsByName("ROLE_ADMIN")){
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleService.saveRole(adminRole);
        }

    }
}
