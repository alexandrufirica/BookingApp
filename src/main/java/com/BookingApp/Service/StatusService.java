package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Status;
import com.BookingApp.Data.Repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService implements  IStatusService{

    private final StatusRepository statusRepository;

    public StatusService( StatusRepository statusRepository){
        this.statusRepository= statusRepository;
    }
    @Override
    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }

    public Status getStatusById(long id){
        return statusRepository.getStatusById(id);
    }
}
