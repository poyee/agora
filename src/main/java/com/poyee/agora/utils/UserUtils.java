package com.poyee.agora.utils;

import com.poyee.agora.bean.UserDto;
import com.poyee.agora.entity.Role;
import com.poyee.agora.entity.User;
import com.poyee.agora.user.LocalUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtils {
    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public static UserDto buildUserDto(LocalUser localUser) {
        List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        User user = localUser.getUser();
        return new UserDto(user.getId(), user.getDisplayName(), user.getEmail(), roles);
    }
}
