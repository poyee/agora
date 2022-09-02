package com.poyee.agora.user;

import com.poyee.agora.bean.UserDto;
import com.poyee.agora.config.CurrentUser;
import com.poyee.agora.utils.UserUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserDto getCurrentUser(@CurrentUser LocalUser user) {
        return UserUtils.buildUserDto(user);
    }
}
