package ca.ulaval.glo4002.cafe.application.reservations;

import ca.ulaval.glo4002.cafe.application.reservations.dtos.ReservationsDto;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;

public class ReservationService {
    private final ReservationDtoAssembler reservationDtoAssembler;
    private final ShopRepository shopRepository;

    public ReservationService(ReservationDtoAssembler groupDtoAssembler,
                              ShopRepository shopRepository) {
        this.reservationDtoAssembler = groupDtoAssembler;
        this.shopRepository = shopRepository;
    }

    public ReservationsDto getReservations() {
        Shop shop = shopRepository.getShop();
        var reservations = shop.getReservations();
        return reservationDtoAssembler.reservationsToDto(reservations);
    }

    public void reserve(String reservationName, int reservationSize) {
        Shop shop = shopRepository.getShop();
        shop.reserve(reservationName, reservationSize);
        shopRepository.save(shop);
    }
}
