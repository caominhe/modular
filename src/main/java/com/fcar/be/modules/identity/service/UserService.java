package com.fcar.be.modules.identity.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fcar.be.core.exception.AppException;
import com.fcar.be.core.exception.ErrorCode;
import com.fcar.be.modules.identity.dto.request.UserCreationRequest;
import com.fcar.be.modules.identity.dto.response.UserResponse;
import com.fcar.be.modules.identity.entity.Role;
import com.fcar.be.modules.identity.entity.User;
import com.fcar.be.modules.identity.mapper.UserMapper;
import com.fcar.be.modules.identity.repository.RoleRepository;
import com.fcar.be.modules.identity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // Gán Role mặc định là CUSTOMER (Bạn cần đảm bảo Role này đã tồn tại trong DB)
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById("CUSTOMER").ifPresent(roles::add);
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
}
