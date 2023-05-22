package ca.ulaval.glo4002.cafe.domain.shop.strategy;

public class ReservationStrategyFactory {
    public static ReservationStrategy create(ReservationType reservationType) {
        if (reservationType == ReservationType.DEFAULT) {
            return new DefaultReservationStrategy();
        } else if (reservationType == ReservationType.FULLCUBE) {
            return new FullCubeReservationStrategy();
        } else {
            return new NoLonersReservationStrategy();
        }
    }
}
