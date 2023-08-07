package uz.ilmnajot.emailvertification.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import uz.ilmnajot.emailvertification.entity.User;
import uz.ilmnajot.emailvertification.enums.RoleName;
@Data
public class UserDto {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private RoleName role;

    public static UserDto userToUserDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

}
