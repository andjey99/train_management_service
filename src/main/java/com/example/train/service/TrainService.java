package com.example.train.service;

import com.example.train.model.Train;
import com.example.train.repo.Train_Repository;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TrainService {
    private Train_Repository repo;

    public TrainService(Train_Repository rep) {
        this.repo = rep;
    }

    public void addTrain(Train train) {
        repo.save(train);
    }

    public void updateTrainStatuses() {
        List<Train> list = repo.findAll();
        LocalTime time = LocalTime.now();

        for (Train train : list) {
            // check if departure time has passed
            if (time.isAfter(train.getDepartureTime()) == true) {
                if (!"Finalised".equals(train.getStatus())) {
                    train.setStatus("Finalised");
                    repo.save(train);
                }
            }
        }
    }

    public Optional<Train> getTrain(String id) {
        return repo.findById(id);
    }

    public List<Train> listTrains() {
        return repo.findAll();
    }

    public void removeTrain(String id)   {
        repo.delete(id);
    }
}
