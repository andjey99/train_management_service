package com.example.train.repo;

import com.example.train.model.Staff;
import java.util.List;
import java.util.Optional;

public interface Staff_Repository
 {
    void save (Staff staff);

    Optional <Staff> findById (String id);

    Optional <Staff> findByEmail (String email);

    List <Staff> findAll ();

    void delete (String id);
}
