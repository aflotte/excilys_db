package com.excilys.db.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.db.dto.UserDTO;
import com.excilys.db.model.User;

@Component
public class UserMapper {
    public UserDTO toDTo(User user) {
        return new UserDTO(user.getUsername(), user.getPassword());
    }
    
    public User toUser(UserDTO dtoUser) {
        User user = new User();
        user.setEnabled(true);
        user.setPassword(dtoUser.getPassword());
        user.setUsername(dtoUser.getUsername());
        return user;
    }
    
    public List<UserDTO> toDtos(List<User> users){
        return users.stream().map(c -> toDTo(c)).collect(Collectors.toList());
    }
    
    public List<User> toUsers(List<UserDTO> dtoUsers){
        return dtoUsers.stream().map(c -> toUser(c)).collect(Collectors.toList());
}
}
