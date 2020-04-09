package com.misernandfriends.cinemaclub.pojo;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private Date enrolmentDate;
    private Long points;
    private String name;
    private String surname;
    private Date birthday;
    private String email;
    private String phoneNo;
    private String address;

    public User toUserResponse(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.enrolmentDate = userDTO.getEnrolmentDate();
        this.points = userDTO.getPoints();
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.birthday = userDTO.getBirthday();
        this.email = userDTO.getEmail();
        this.phoneNo = userDTO.getPhoneNo();
        this.address = userDTO.getAddress().toString();

        return this;
    }
}
