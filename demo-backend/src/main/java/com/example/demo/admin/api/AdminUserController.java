package com.example.demo.admin.api;

import com.example.demo.admin.dto.AdminUserResponse;
import com.example.demo.admin.service.AdminUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminUserController {

    private final AdminUserService service;

    public AdminUserController(AdminUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<AdminUserResponse> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/{id}/suspend")
    public void suspendUser(@PathVariable String id) {
        service.suspendUser(id);
    }

    @PostMapping("/{id}/promote")
    public void promoteUser(@PathVariable String id) {
        service.promoteToAdmin(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }
}