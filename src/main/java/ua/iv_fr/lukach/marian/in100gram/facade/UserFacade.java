package ua.iv_fr.lukach.marian.in100gram.facade;

import org.springframework.stereotype.Component;
import ua.iv_fr.lukach.marian.in100gram.dto.UserDTO;
import ua.iv_fr.lukach.marian.in100gram.entity.User;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());
        return userDTO;
    }
}
