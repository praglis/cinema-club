package com.misernandfriends.cinemaclub.pojo;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserLikes {

    private Long id;
    private Long likes;
    private String statement;
    private Date infoCD;
    private UserDTO infoCU;
    private boolean isLiked;
    private List<UserLikes> replies;

}
