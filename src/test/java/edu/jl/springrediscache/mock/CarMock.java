package edu.jl.springrediscache.mock;

import edu.jl.springrediscache.dto.car.CarRequestDTO;

import java.time.LocalDate;

public abstract class CarMock {

    private static final short CURRENT_YEAR = (short) LocalDate.now().getYear();

    protected static final long VALID_CAR_ID = 1L;
    protected static final long HIGHEST_VALID_CAR_ID = 1000L;
    protected static final long INVALID_CAR_ID = -90000L;

    protected static final CarRequestDTO VALID_CAR =
            new CarRequestDTO("Honda", "Odyssey", (short) 2007);

    protected static final CarRequestDTO VALID_CAR_UPDATE =
            new CarRequestDTO("NewMake", "NewModel", (short) 2024);

    protected static final CarRequestDTO INVALID_CAR_WITH_NULL_MAKE =
            new CarRequestDTO(null, "Odyssey", (short) 2007);

    protected static final CarRequestDTO INVALID_CAR_WITH_BLANK_MAKE =
            new CarRequestDTO(" ", "Odyssey", (short) 2007);

    protected static final CarRequestDTO INVALID_CAR_WITH_EMPTY_MAKE =
            new CarRequestDTO("", "Odyssey", (short) 2007);

    protected static final CarRequestDTO INVALID_CAR_WITH_NULL_MODEL =
            new CarRequestDTO("Honda", null, (short) 2007);

    protected static final CarRequestDTO INVALID_CAR_WITH_BLANK_MODEL =
            new CarRequestDTO("Honda", " ", (short) 2007);

    protected static final CarRequestDTO INVALID_CAR_WITH_EMPTY_MODEL =
            new CarRequestDTO("Honda", "", (short) 2007);

    protected static final CarRequestDTO INVALID_CAR_WITH_YEAR_BELOW_1900 =
            new CarRequestDTO("Honda", "Odyssey", (short) 1899);

    protected static final CarRequestDTO INVALID_CAR_WITH_YEAR_ABOVE_CURRENT =
            new CarRequestDTO("Honda", "Odyssey", (short) (CURRENT_YEAR + 1));
}
