package uz.ilmnajot.emailvertification.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import uz.ilmnajot.emailvertification.enums.RoleName;

@Data
public class UserForm {


    private String name;

    private String lastName;

    private String email;

    private String password;

//    private RoleName role;

}
