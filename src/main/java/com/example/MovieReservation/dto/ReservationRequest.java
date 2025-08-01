package com.example.MovieReservation.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class ReservationRequest {
    @NotNull
    private Long showTimeId;

    @NotNull
    @Size(min = 1, max = 8, message = "You can reserve 1 to 8 seats at a time")
    private List<Long> seatIds;

    // Constructors, getters, setters
    public ReservationRequest() {}

    public Long getShowTimeId() { return showTimeId; }
    public void setShowTimeId(Long showTimeId) { this.showTimeId = showTimeId; }

    public List<Long> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Long> seatIds) { this.seatIds = seatIds; }
}
