package ca.ulaval.glo4002.cafe.application.shop;

import java.util.List;

import ca.ulaval.glo4002.cafe.application.shop.dtos.CubeDto;
import ca.ulaval.glo4002.cafe.domain.shop.Cube;

public class CubeDtoAssembler {
    private final SeatDtoAssembler seatDtoAssembler;

    public CubeDtoAssembler(SeatDtoAssembler seatDtoAssembler) {
        this.seatDtoAssembler = seatDtoAssembler;
    }

    private CubeDto cubeToDto(Cube cube) {
        return new CubeDto(cube.getName(), seatDtoAssembler.seatsToDtos(cube.getSeats()));
    }

    public List<CubeDto> cubesToDtos(List<Cube> cubes) {
        return cubes.stream().map(this::cubeToDto).toList();
    }
}
