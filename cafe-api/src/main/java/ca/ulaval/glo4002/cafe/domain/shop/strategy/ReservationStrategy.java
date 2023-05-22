package ca.ulaval.glo4002.cafe.domain.shop.strategy;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.shop.Cube;

public interface ReservationStrategy {
    void reserve(String reservationName, int reservationSize, List<Cube> cubes);
}
