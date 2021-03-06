package com.misernandfriends.cinemaclub.model.movie;

public abstract class RatingEntity {

    public abstract Double getRating();

    public abstract void setRating(Double rating);

    public abstract void setVotesNumber(Long votesNumber);

    public abstract Long getVotesNumber();

    public void recalculateRating(Integer oldRate, Integer newRate) {
        Double rating = getRating();
        Long size = getVotesNumber();
        if (size == null) {
            size = 0L;
        }
        boolean isNew = false;
        if (oldRate == null) {
            isNew = true;
            oldRate = 0;
        }
        if(newRate == null){
            newRate = 0;
        }
        Double rateValue = rating * size + (newRate - oldRate);
        setRating(rateValue);
        setVotesNumber(size + (isNew ? 1 : 0));
        setRating(getRating() / getVotesNumber());
    }

}
