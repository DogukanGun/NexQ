package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.data.entity.Permissions;
import com.dag.nexq_userservice.repository.UserPermissionRepository;
import com.dag.nexq_userservice.services.interfaces.ICrudService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ICrudService<UserPermissionRepository, Permissions,Integer>{

    public PermissionService(UserPermissionRepository repository) {
        super(repository);
    }
}
