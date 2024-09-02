package com.nnk.springboot.record;

import com.nnk.springboot.domain.Rating;

public interface RatingRecord {

    interface Vm {

        record RatingVm(
                String moodysRating,
                String sandPRating,
                String fitchRating,
                Integer orderNumber
        ){
            public RatingVm(Rating rating) {
                this(
                        rating.getMoodysRating(),
                        rating.getSandPRating(),
                        rating.getFitchRating(),
                        rating.getOrderNumber()
                );
            }
        }
    }

    interface Api {
        record RatingRequest(
                String moodysRating,
                String sandPRating,
                String fitchRating,
                Integer orderNumber
        ){}
    }
}
