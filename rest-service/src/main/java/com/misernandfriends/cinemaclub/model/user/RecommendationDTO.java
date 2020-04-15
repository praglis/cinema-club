package com.misernandfriends.cinemaclub.model.user;


import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.cache.LazyCache;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "USR_RECOMMEND")
public class RecommendationDTO implements Serializable {

    public interface Type {

        String Category = "C";
        String Actor = "A";
        String Director = "D";

        static String getQueryParameter(String type) {
            switch (type){
                case Category:
                    return LazyCache.getValue(CacheValue._QUERY_PARAM.WITH_GENRES);
                case Actor:
                    return LazyCache.getValue(CacheValue._QUERY_PARAM.WITH_CAST);
                case Director:
                    return LazyCache.getValue(CacheValue._QUERY_PARAM.WITH_CREW);
                default:
                    throw new RuntimeException("No query parameter found for " + type);
            }
        }

    }

    @Id
    @Column(name = "URM_ID")
    @SequenceGenerator(name = "SEQ_USR_URM_ID", sequenceName = "SEQ_USR_URM_ID")
    @GeneratedValue(generator = "SEQ_USR_URM_ID", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "URM_USR_ID", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "URM_USR_ID", updatable = false, insertable = false)
    private UserDTO user;

    @Column(name = "URM_TYPE", nullable = false, length = 1)
    private String type;

    @Column(name = "URM_VALUE", nullable = false)
    private String value;

    @Column(name = "URM_FIT_LEVEL")
    private Double fitLevel = 0.2;

}
