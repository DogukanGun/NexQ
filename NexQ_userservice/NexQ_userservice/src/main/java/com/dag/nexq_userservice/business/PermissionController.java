package com.dag.nexq_userservice.business;

import com.dag.nexq_userservice.data.entity.Permissions;
import com.dag.nexq_userservice.data.request.auth.PermissionRequest;
import com.dag.nexq_userservice.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/create")
    public Permissions createPermission(@RequestBody PermissionRequest permissions){
        return permissionService.updateItem(Permissions.builder().permission(permissions.getPermission()).build());
    }

    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable("id") Integer id){
        Permissions permissions = permissionService.getItem(id).orElseThrow(NoClassDefFoundError::new);
        permissionService.deleteItem(permissions);
    }

    @GetMapping("/")
    public List<Permissions> getPermissions(){
        return permissionService.getAll();
    }

}
