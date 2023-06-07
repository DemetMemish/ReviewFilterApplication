package com.example.reviewfilterapplication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {
    private List<Review> reviews;

    @PostConstruct
    public void init() throws IOException {
        // read reviews from the reviews.json file
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        reviews = mapper.readValue(new File("C:\\Users\\Demet\\IdeaProjects\\ReviewFilterApplication\\src\\main\\resources\\reviews (2) (2) (2).json"), new TypeReference<List<Review>>() {});
    }
    @GetMapping
    public ModelAndView getFilteredReviews(@ModelAttribute("filterForm") FilterForm filterForm, Model model) {
        List<Review> filteredReviews = filterReviews(filterForm);
        model.addAttribute("filteredReviews", filteredReviews);
        return new ModelAndView("reviews");
    }

    @PostMapping
    public ModelAndView applyFilters(@ModelAttribute("filterForm") FilterForm filterForm) {
        return new ModelAndView("redirect:/reviews");
    }


//    private List<Review> filterReviews(FilterForm filterForm) {
//        List<Review> filteredReviews = reviews.stream()
//                .filter(review -> review.getRating() >= filterForm.getMinRating())
//                .collect(Collectors.toList());
//
//        if (filterForm.getPrioritizeText() != null && filterForm.getPrioritizeText().equals("Yes")) {
//            Comparator<Review> textComparator = Comparator.comparing(Review::getReviewText, Comparator.nullsLast(String::compareTo));
//            Comparator<Review> ratingComparator = Comparator.comparing(Review::getRating).reversed();
//            Comparator<Review> dateComparator = Comparator.comparing(Review::getReviewCreatedOn);
//
//            List<Review> reviewsWithText = filteredReviews.stream()
//                    .filter(review -> review.getReviewText() != null && !review.getReviewText().isEmpty())
//                    .sorted(dateComparator.thenComparing(ratingComparator).thenComparing(textComparator))
//                    .collect(Collectors.toList());
//
//            List<Review> reviewsWithoutText = filteredReviews.stream()
//                    .filter(review -> review.getReviewText() == null || review.getReviewText().isEmpty())
//                    .sorted(dateComparator.thenComparing(ratingComparator))
//                    .collect(Collectors.toList());
//
//            filteredReviews = new ArrayList<>();
//            filteredReviews.addAll(reviewsWithText);
//            filteredReviews.addAll(reviewsWithoutText);
//        } else {
//            Comparator<Review> ratingComparator = Comparator.comparing(Review::getRating).reversed();
//            Comparator<Review> dateComparator = Comparator.comparing(Review::getReviewCreatedOn);
//
//            filteredReviews = filteredReviews.stream()
//                    .sorted(ratingComparator.thenComparing(dateComparator))
//                    .collect(Collectors.toList());
//        }
//
//        if (filterForm.getOrderByRating() != null && filterForm.getOrderByRating().equals("LowestFirst")) {
//            filteredReviews.sort(Comparator.comparing(Review::getRating));
//        } else {
//            filteredReviews.sort(Comparator.comparing(Review::getRating).reversed());
//        }
//
//        if (filterForm.getOrderByDate() != null && filterForm.getOrderByDate().equals("NewestFirst")) {
//            filteredReviews.sort(Comparator.comparing(Review::getReviewCreatedOn).reversed());
//        }
//
//        return filteredReviews;
//    }




//        if (prioritizeByText) {
//        List<Review> reviewsWithText = new ArrayList<>(filteredReviews.stream().filter(r -> r.getReviewText() != null && !r.getReviewText().isEmpty()).toList());
//        List<Review> reviewsWithoutText = new ArrayList<>(filteredReviews.stream().filter(r -> r.getReviewText() == null || r.getReviewText().isEmpty()).toList());
//
//        // sort reviews with text
//        reviewsWithText.sort(orderByRating.equals("Highest First") ? Comparator.comparing(Review::getRating).reversed() : Comparator.comparing(Review::getRating));
//        reviewsWithText.sort(orderByDate.equals("Newest First") ? Comparator.comparing(Review::getReviewCreatedOnDate).reversed() : Comparator.comparing(Review::getReviewCreatedOnDate));
//
//        // sort reviews without text
//        reviewsWithoutText.sort(orderByRating.equals("Highest First") ? Comparator.comparing(Review::getRating).reversed() : Comparator.comparing(Review::getRating));
//        reviewsWithoutText.sort(orderByDate.equals("Newest First") ? Comparator.comparing(Review::getReviewCreatedOnDate).reversed() : Comparator.comparing(Review::getReviewCreatedOnDate));
//
//        reviewsWithText.addAll(reviewsWithoutText);
//
//        return reviewsWithText;
//    }
//        else {
//        filteredReviews.sort(orderByRating.equals("Highest First") ? Comparator.comparing(Review::getRating).reversed() : Comparator.comparing(Review::getRating));
//        filteredReviews.sort(orderByDate.equals("Newest First") ? Comparator.comparing(Review::getReviewCreatedOnDate).reversed() : Comparator.comparing(Review::getReviewCreatedOnDate));
//        return filteredReviews;
//    }

    private List<Review> filterReviews(FilterForm filterForm) {
        List<Review> filteredReviews = reviews.stream()
                .filter(review -> review.getRating() >= filterForm.getMinRating())
                .collect(Collectors.toList());

        Comparator<Review> ratingComparator;
        if (filterForm.getOrderByRating() != null && filterForm.getOrderByRating().equals("LowestFirst")) {
            ratingComparator = Comparator.comparing(Review::getRating);
        } else {
            ratingComparator = Comparator.comparing(Review::getRating).reversed();
        }

        Comparator<Review> dateComparator;
        if (filterForm.getOrderByDate() != null && filterForm.getOrderByDate().equals("NewestFirst")) {
            dateComparator = Comparator.comparing(Review::getReviewCreatedOnDate).reversed();
        } else {
            dateComparator = Comparator.comparing(Review::getReviewCreatedOnDate);
        }

        Comparator<Review> textComparator = Comparator.comparing(Review::getReviewText, Comparator.nullsLast(String::compareTo));

        List<Review> reviewsWithText = new ArrayList<>(filteredReviews.stream()
                .filter(review -> review.getReviewText() != null && !review.getReviewText().isEmpty())
                .sorted(textComparator.thenComparing(ratingComparator).thenComparing(dateComparator))
                .collect(Collectors.toList()));

        List<Review> reviewsWithoutText = new ArrayList<>(filteredReviews.stream()
                .filter(review -> review.getReviewText() == null || review.getReviewText().isEmpty())
                .sorted(ratingComparator.thenComparing(dateComparator))
                .collect(Collectors.toList()));

        if (filterForm.getPrioritizeText() != null && filterForm.getPrioritizeText().equals("Yes")) {
            if (filterForm.getOrderByRating() != null && filterForm.getOrderByRating().equals("LowestFirst")) {
                reviewsWithText.sort(Comparator.comparing(Review::getRating));
                reviewsWithoutText.sort(Comparator.comparing(Review::getRating));
            } else {
                reviewsWithText.sort(Comparator.comparing(Review::getRating).reversed());
                reviewsWithoutText.sort(Comparator.comparing(Review::getRating).reversed());
            }

            reviewsWithText.addAll(reviewsWithoutText);
            return reviewsWithText;
        } else {
            filteredReviews = filteredReviews.stream()
                    .sorted(ratingComparator.thenComparing(dateComparator).thenComparing(textComparator))
                    .collect(Collectors.toList());

            return filteredReviews;
        }
    }













    @ModelAttribute("filterForm")
    public FilterForm getFilterForm() {
        return new FilterForm();
    }


}
