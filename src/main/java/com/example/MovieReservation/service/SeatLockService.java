package com.example.MovieReservation.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SeatLockService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String SEAT_LOCK_PREFIX = "seat_lock:";
    private static final int LOCK_TIMEOUT_MINUTES = 10;

    public boolean lockSeats(Long showTimeId, List<Long> seatIds, String userId) {
        List<String> lockKeys = seatIds.stream()
                .map(seatId -> SEAT_LOCK_PREFIX + showTimeId + ":" + seatId)
                .collect(Collectors.toList());

        for (String lockKey : lockKeys) {
            Boolean lockAcquired = redisTemplate.opsForValue()
                    .setIfAbsent(lockKey, userId, LOCK_TIMEOUT_MINUTES, TimeUnit.MINUTES);

            if (!Boolean.TRUE.equals(lockAcquired)) {
                // If any seat lock fails, release all previously acquired locks
                unlockSeats(showTimeId, seatIds, userId);
                return false;
            }
        }
        return true;
    }

    public void unlockSeats(Long showTimeId, List<Long> seatIds, String userId) {
        List<String> lockKeys = seatIds.stream()
                .map(seatId -> SEAT_LOCK_PREFIX + showTimeId + ":" + seatId)
                .collect(Collectors.toList());

        for (String lockKey : lockKeys) {
            String lockOwner = redisTemplate.opsForValue().get(lockKey);
            if (userId.equals(lockOwner)) {
                redisTemplate.delete(lockKey);
            }
        }
    }

    public boolean isSeatsLocked(Long showTimeId, List<Long> seatIds) {
        return seatIds.stream()
                .map(seatId -> SEAT_LOCK_PREFIX + showTimeId + ":" + seatId)
                .anyMatch(lockKey -> Boolean.TRUE.equals(redisTemplate.hasKey(lockKey)));
    }
}