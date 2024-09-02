package com.nnk.springboot.domain;

import com.nnk.springboot.record.RatingRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 125)
    private String moodysRating;

    @Size(max = 125)
    private String sandPRating;

    @Size(max = 125)
    private String fitchRating;

    private Integer orderNumber;

    public Rating(RatingRecord.Api.RatingRequest ratingRequest) {
        this.moodysRating = ratingRequest.moodysRating();
        this.sandPRating = ratingRequest.sandPRating();
        this.fitchRating = ratingRequest.fitchRating();
        this.orderNumber = ratingRequest.orderNumber();
    }
}

