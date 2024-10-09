package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.record.RatingRecord;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional(readOnly = true)
    public List<RatingRecord.Vm.RatingVm> getAllRating() {
        logger.info("Fetching all ratings");
        List<Rating> ratingList = ratingRepository.findAll();
        logger.debug("Found {} ratings", ratingList.size());

        if (ratingList.isEmpty()) {
            logger.warn("No ratings found");
            return null;
        }

        return ratingList
                .stream()
                .map(RatingRecord.Vm.RatingVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RatingRecord.Vm.RatingVm getRatingById(int id) {
        logger.info("Fetching rating with id: {}", id);
        Optional<Rating> ratingOpt = ratingRepository.findById(id);

        if (ratingOpt.isPresent()) {
            logger.debug("Rating found with id: {}", id);
            return new RatingRecord.Vm.RatingVm(ratingOpt.get());
        }

        logger.warn("No rating found with id: {}", id);
        return null;
    }

    @Transactional
    public void createRating(Rating rating) {
        try {
            logger.info("Creating new rating");
            ratingRepository.save(rating);
            logger.debug("Rating created with id: {}", rating.getId());
        } catch (Exception e) {
            logger.error("Failed to create rating", e);
            throw new RuntimeException("Failed to create rating", e);
        }
    }

    @Transactional
    public RatingRecord.Vm.RatingVm updateRating(int ratingId, Rating ratingRequest) {
        logger.info("Updating rating with id: {}", ratingId);
        Optional<Rating> ratingOpt = ratingRepository.findById(ratingId);
        if (ratingOpt.isPresent()) {
            Rating rating = ratingOpt.get();
            BeanUtils.copyProperties(ratingRequest, rating, getNullPropertyNames(ratingRequest));
            ratingRepository.save(rating);
            logger.debug("Rating updated with id: {}", ratingId);
            return new RatingRecord.Vm.RatingVm(rating);
        }

        logger.warn("No rating found with id: {} for update", ratingId);
        return null;
    }

    @Transactional
    public void deleteRating(int ratingId) {
        try {
            logger.info("Deleting rating with id: {}", ratingId);
            ratingRepository.deleteById(ratingId);
            logger.debug("Rating deleted with id: {}", ratingId);
        } catch (Exception e) {
            logger.error("Failed to delete rating with id: {}", ratingId, e);
            throw new RuntimeException("Failed to delete rating", e);
        }
    }
}
