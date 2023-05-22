package ca.ulaval.glo4002.cafe.domain.shop;

import java.util.ArrayList;
import java.util.List;

public class ShopBuilder {
    private final int seatsPerRoom;
    private final List<Cube> cubes;
    private int seatNumber;

    public ShopBuilder(int seatsPerRoom) {
        this.seatsPerRoom = seatsPerRoom;
        this.cubes = new ArrayList<>();
        this.seatNumber = 1;
    }

    public void addCube(String name) {
        cubes.add(new Cube(name, cubeSeats()));
    }

    private List<Seat> cubeSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int max = seatNumber + seatsPerRoom; seatNumber < max; seatNumber++) {
            seats.add(new Seat(seatNumber));
        }
        return seats;
    }

    public Shop build(String name) {
        return new Shop(name, cubes);
    }
}
