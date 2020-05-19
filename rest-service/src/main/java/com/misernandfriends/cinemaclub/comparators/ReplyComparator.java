package com.misernandfriends.cinemaclub.comparators;

import com.misernandfriends.cinemaclub.pojo.user.UserLikes;

import java.util.Comparator;

public class ReplyComparator implements Comparator<UserLikes> {

    @Override
    public int compare(UserLikes o1, UserLikes o2) {
        if (o1.getInfoCD().before(o2.getInfoCD())) {
            return -1;
        } else if (o1.getInfoCD().after(o2.getInfoCD())) {
            return 1;
        } else {
            return 0;
        }
    }
}
