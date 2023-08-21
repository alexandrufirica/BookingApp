package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Status;

import java.util.List;

public interface IStatusService {

    List<Status> findAllStatuses();
}
