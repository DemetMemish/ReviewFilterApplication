package com.example.reviewfilterapplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Review {
    private String id;
    private String reviewId;
    private String reviewFullText;
    private String reviewText;
    private int numLikes;
    private int numComments;
    private int numShares;
    private int rating;
    private String reviewCreatedOn;
    private Date reviewCreatedOnDate;
    private Long reviewCreatedOnTime;
    private String reviewerId;
    private String reviewerUrl;
    private String reviewerName;
    private String reviewerEmail;
    private String sourceType;
    private boolean isVerified;
    private String source;
    private String sourceName;
    private String sourceId;
    private List<String> tags;
    private String href;
    private String logoHref;
    private List<String> photos;}
