package com.example.spyglass.domain.goals.services;
import com.example.spyglass.domain.goals.exceptions.GoalNotFoundException;
import com.example.spyglass.domain.goals.models.CompletedGoal;
import com.example.spyglass.domain.goals.models.Goal;

import com.example.spyglass.domain.goals.models.Goal;
import com.example.spyglass.domain.goals.repos.GoalRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService{
    private static final Logger logger = LoggerFactory.getLogger(GoalServiceImpl.class);

    private GoalRepo goalRepo;

    @Autowired
    public GoalServiceImpl(GoalRepo goalRepo){
        this.goalRepo = goalRepo;
    }

    @Override
    public Goal createGoal(Goal goal) {
        goal.setLeftToBeSaved(leftToSave(goal.getEndGoal(),goal.getSavedSoFar()));
        goal.setProgressBar(progressBarCal(goal.getSavedSoFar(),goal.getEndGoal()));
        return goalRepo.save(goal);

    }

    @Override
    public Goal findById(Long Id) {
        return null;
    }

    @Override
    public Goal updateGoal(Goal goal) {return null;
    }

    @Override
    public void deleteGoal(Long Id) throws GoalNotFoundException {
    }

    @Override
    public String progressBarCal(Double savedSoFar, Double endGoal) {

        Double progressBarCalculation = (savedSoFar / endGoal) * 100;
        String formattedDecimal = String.format("%.2f", progressBarCalculation);
        String formattedAnswer = formattedDecimal +"%";
        logger.info("Calculated progress bar %", formattedAnswer);
        return formattedAnswer;
    }

    @Override
    public String leftToSave(Double endGoal, Double savedSoFar) {
        Double moneyLeftToSave = endGoal - savedSoFar;
        String formattedAnswer = "$" + String.format("%.2f",moneyLeftToSave);
        return formattedAnswer;
    }

    @Override
    public Double setGoal(Double endGoal) {
        return null;
    }

    public ArrayList<CompletedGoal> completedGoals(Double savedSoFar, Double endGoal){
        ArrayList<CompletedGoal> allGoals = new ArrayList<>();
        CompletedGoal completedGoal = new CompletedGoal();
        Goal goal = new Goal();
        goal.setSavedSoFar(savedSoFar);
        goal.setEndGoal(endGoal);
        if(goal.getSavedSoFar()== goal.getEndGoal()){
            allGoals.add(completedGoal);
        }
        return allGoals;
    }

}
