package ca.ulaval.glo4002.cafe.application.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.cafe.application.shop.dtos.CubeDto;
import ca.ulaval.glo4002.cafe.domain.shop.Cube;
import ca.ulaval.glo4002.cafe.domain.shop.Seat;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CubeDtoAssemblerTest {
    public static final String MERRYWEATHER = "Merryweather";
    public static final String WANDA = "Wanda";
    public static final String BLOOM = "Bloom";

    @Mock
    private SeatDtoAssembler seatDtoAssembler;
    private CubeDtoAssembler cubeDtoAssembler;

    @BeforeEach
    void setUp() {
        cubeDtoAssembler = new CubeDtoAssembler(seatDtoAssembler);
    }

    @Test
    void givenListOfCubes_whenCubesToDtos_thenCubeDtosMatchCubes() {
        var cubes = new ArrayList<Cube>();
        cubes.add(new Cube(WANDA, new ArrayList<>()));
        cubes.add(new Cube(MERRYWEATHER, new ArrayList<>()));
        var expectedCubeNames = Arrays.asList(WANDA, MERRYWEATHER);

        var actualCubeDtos = cubeDtoAssembler.cubesToDtos(cubes);

        var actualCubeNames = actualCubeDtos.stream().map(CubeDto::name).toList();
        assertTrue(actualCubeNames.containsAll(expectedCubeNames));
    }

    @Test
    void givenListOfSingleCube_whenCubesToDtos_thenAssembleCubeSeatsToDtos() {
        Cube cubeToBeAssembled = getCubeWithSeats();
        List<Cube> cubesToBeAssembled = List.of(cubeToBeAssembled);

        cubeDtoAssembler.cubesToDtos(cubesToBeAssembled);

        verify(seatDtoAssembler).seatsToDtos(cubeToBeAssembled.getSeats());
    }

    @Test
    void givenListOfMultipleCube_whenCubesToDtos_thenAssembleSeatsMultipleTimes() {
        Cube firstCubeToBeAssembled = getCubeWithSeats();
        Cube secondCubeToBeAssembled = getCubeWithSeats();
        List<Cube> cubesToBeAssembled = List.of(firstCubeToBeAssembled, secondCubeToBeAssembled);

        cubeDtoAssembler.cubesToDtos(cubesToBeAssembled);

        verify(seatDtoAssembler).seatsToDtos(firstCubeToBeAssembled.getSeats());
        verify(seatDtoAssembler).seatsToDtos(secondCubeToBeAssembled.getSeats());
    }

    private static Cube getCubeWithSeats() {
        var seats = new ArrayList<Seat>();
        seats.add(new Seat(1));
        seats.add(new Seat(2));
        return new Cube(BLOOM, seats);
    }
}
