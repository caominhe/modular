
// # Logic truy vấn user từ DB (gọi sang module Identity)
package com.fcar.be.core.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Inject UserRepository từ module Identity khi bạn tạo xong
    // private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Giả lập tìm kiếm user từ DB (Dựa theo bảng users trong V1__init_database.sql)
        // User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(...));

        return new CustomUserDetails(
                1L, // id
                email,
                "password_hash", // lấy từ db
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
