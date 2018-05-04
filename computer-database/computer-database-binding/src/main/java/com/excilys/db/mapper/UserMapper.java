package com.excilys.db.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.db.dto.UserDTO;
import com.excilys.db.model.Users;

@Component
public class UserMapper {
    public UserDTO toDTo(Users user) {
        return new UserDTO(user.getUsername(), user.getPassword());
    }
    
    public Users toUser(UserDTO dtoUser) {
        Users user = new Users();
        user.setEnabled(true);
        user.setPassword(dtoUser.getPassword());
        user.setUsername(dtoUser.getUsername());
        return user;
    }
    
    public List<UserDTO> toDtos(List<Users> users){
        return users.stream().map(c -> toDTo(c)).collect(Collectors.toList());
    }
    
    public List<Users> toUsers(List<UserDTO> dtoUsers){
        return dtoUsers.stream().map(c -> toUser(c)).collect(Collectors.toList());
}
}
