package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.record.RatingRecord;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional(readOnly = true)
    public List<RatingRecord.Vm.RatingVm> getAllRating() {
        List<Rating> ratingList = ratingRepository.findAll();

        if (ratingList.isEmpty())
            return null;

        return ratingList
                .stream()
                .map(RatingRecord.Vm.RatingVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RatingRecord.Vm.RatingVm getRatingById(int id) {
        Optional<Rating> ratingOpt = ratingRepository.findById(id);

        if (ratingOpt.isPresent())
            return new RatingRecord.Vm.RatingVm(ratingOpt.get());

        return null;
    }

    @Transactional
    public void createRating(Rating rating) {
        try {
            ratingRepository.save(rating);
            new RatingRecord.Vm.RatingVm(rating);
        } catch (Exception e) {
            throw new RuntimeException("failed to create rating", e);
        }
    }

    @Transactional
    public RatingRecord.Vm.RatingVm updateRating(int ratingId, Rating ratingRequest) {
        Optional<Rating> ratingOpt = ratingRepository.findById(ratingId);
        if (ratingOpt.isPresent()) {
            Rating rating = ratingOpt.get();

            BeanUtils.copyProperties(ratingRequest, rating, getNullPropertyNames(ratingRequest));
            ratingRepository.save(rating);
            return new RatingRecord.Vm.RatingVm(rating);
        }
        return null;
    }

    @Transactional
    public void deleteRating(int ratingId) {
        try {
            ratingRepository.deleteById(ratingId);
        } catch (Exception e) {
            throw new RuntimeException("failed to delete rating", e);
        }
    }
}
