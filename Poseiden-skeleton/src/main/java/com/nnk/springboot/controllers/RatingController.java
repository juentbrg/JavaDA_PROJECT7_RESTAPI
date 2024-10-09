package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.record.RatingRecord;
import com.nnk.springboot.service.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model) {
        List<RatingRecord.Vm.RatingVm> ratingVms = ratingService.getAllRating();
        model.addAttribute("ratings", ratingVms);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.createRating(rating);
        model.addAttribute("ratings", ratingService.getAllRating());

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingRecord.Vm.RatingVm ratingVm = ratingService.getRatingById(id);
        if (null != ratingVm) {
            model.addAttribute("rating", ratingVm);
            return "rating/update";
        }
        return "redirect:/rating/list";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors())
            return "rating/update";

        RatingRecord.Vm.RatingVm ratingVm = ratingService.updateRating(id, rating);
        model.addAttribute("ratings", ratingVm);

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        model.addAttribute("ratings", ratingService.getAllRating());

        return "redirect:/rating/list";
    }
}
