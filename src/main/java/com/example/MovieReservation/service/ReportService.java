package com.example.MovieReservation.service;


import com.example.MovieReservation.repository.ReservationRepository;
import com.example.MovieReservation.repository.ShowTimeRepository;
import com.example.MovieReservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("todayRevenue", reservationRepository.getTodayRevenue() != null ?
                reservationRepository.getTodayRevenue() : BigDecimal.ZERO);
        stats.put("todayReservations", reservationRepository.getTodayReservationCount());
        stats.put("totalUsers", userRepository.count());
        stats.put("totalShowTimes", showTimeRepository.count());

        return stats;
    }

    public Map<String, Object> getShowTimeReport(Long showTimeId) {
        Map<String, Object> report = new HashMap<>();

        long reservedSeats = showTimeRepository.countReservedSeats(showTimeId);
        report.put("reservedSeats", reservedSeats);

        return report;
    }
}

