package com.example.train.repo;

import com.example.train.model.Train;
import java.util.List;
import java.util.Optional;

public interface Train_Repository 
{
    void save (Train train);

    Optional <Train> findById (String id);

    List <Train> findAll ();

    void delete (String id);
}
