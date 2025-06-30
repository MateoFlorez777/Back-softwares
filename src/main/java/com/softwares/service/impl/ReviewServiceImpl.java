package com.softwares.service.impl;

import com.softwares.exceptions.ReviewNotFoundException;
import com.softwares.models.Product;
import com.softwares.models.Review;
import com.softwares.models.User;
import com.softwares.repository.ReviewRepository;
import com.softwares.request.CreateReviewRequest;
import com.softwares.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;


    @Override
    public Review createReview(CreateReviewRequest req,
                               User user,
                               Product product) {
        Review newReview = new Review();

        newReview.setReviewText(req.getReviewText());
        newReview.setRating(req.getReviewRating());
        newReview.setProductImages(req.getProductImages());
        newReview.setUser(user);
        newReview.setProduct(product);

        product.getReviews().add(newReview);

        return reviewRepository.save(newReview);
    }

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findReviewsByProductId(productId);
    }


    @Override
    public Review updateReview(Long reviewId,
                               String reviewText,
                               double rating,
                               Long userId) throws ReviewNotFoundException, AuthenticationException {
        Review review=reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("Reseña no encontrada."));

        if(review.getUser().getId()!=userId){
            throw new AuthenticationException("No tienes permiso para eliminar esta reseña.");
        }

        review.setReviewText(reviewText);
        review.setRating(rating);
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId,Long userId) throws ReviewNotFoundException,
            AuthenticationException {
        Review review=reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("Reseña no encontrada."));
        if(review.getUser().getId()!=userId){
            throw new AuthenticationException("No tienes permiso para eliminar esta reseña.");
        }
        reviewRepository.delete(review);
    }

}
