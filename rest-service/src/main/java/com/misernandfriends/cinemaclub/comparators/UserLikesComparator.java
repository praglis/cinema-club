package com.misernandfriends.cinemaclub.comparators;

import com.misernandfriends.cinemaclub.pojo.user.UserLikes;

import java.util.Comparator;

public class UserLikesComparator implements Comparator<UserLikes> {

    @Override
    public int compare(UserLikes o1, UserLikes o2) {
        return Boolean.compare(o2.getHighlighted(), o1.getHighlighted());
    }
}
