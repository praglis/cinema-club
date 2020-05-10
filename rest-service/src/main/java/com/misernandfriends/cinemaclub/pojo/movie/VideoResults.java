package com.misernandfriends.cinemaclub.pojo.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoResults {

    @JsonProperty("id")
    @SerializedName("id")
    String id;

    @JsonProperty("iso_639_1")
    @SerializedName("iso_639_1")
    String iso_639_1;

    @JsonProperty("iso_3166_1")
    @SerializedName("iso_3166_1")
    String iso_3166_1;

    @JsonProperty("key")
    @SerializedName("key")
    String key;

    @JsonProperty("name")
    @SerializedName("name")
    String name;

    @JsonProperty("site")
    @SerializedName("site")
    String site;

    @JsonProperty("size")
    @SerializedName("size")
    Integer size;

    @JsonProperty("type")
    @SerializedName("type")
    String type;
}
