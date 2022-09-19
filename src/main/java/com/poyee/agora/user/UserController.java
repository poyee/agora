package com.poyee.agora.user;

import com.poyee.agora.bean.UpdateDisplayNameRequest;
import com.poyee.agora.bean.UserDto;
import com.poyee.agora.config.CurrentUser;
import com.poyee.agora.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserDto getCurrentUser(@CurrentUser LocalUser user) {
        return UserUtils.buildUserDto(user);
    }

    @PutMapping("/name")
    @PreAuthorize("hasRole('USER')")
    public UserDto updateDisplayName(@Valid @RequestBody UpdateDisplayNameRequest request, @CurrentUser LocalUser user) {
        return service.updateDisplayName(request.getDisplayName(), user);
    }
}
