package br.com.redemobconsorcio.rocket.util;

import br.com.redemobconsorcio.rocket.dto.MunicpioDTO;

import java.util.Arrays;
import java.util.List;

public class MunicipioUtil {

    private MunicipioUtil() {}

    /**
     * O ideal seria buscar da base de dados ou de um WebService/API
     */
    public static List<MunicpioDTO> getMunicipiosGoiania() {
        return Arrays.asList(
            new MunicpioDTO(1, "Abadia de Goiás"),
            new MunicpioDTO(2, "Aparecida de Goiânia"),
            new MunicpioDTO(3, "Aragoiânia"),
            new MunicpioDTO(4, "Bela Vista de Goiás"),
            new MunicpioDTO(5, "Bonfinópolis"),
            new MunicpioDTO(6, "Brazabrantes"),
            new MunicpioDTO(7, "Caldazinha"),
            new MunicpioDTO(8, "Caturaí"),
            new MunicpioDTO(9, "Goiânia"),
            new MunicpioDTO(10, "Goianápolis"),
            new MunicpioDTO(11, "Goianira"),
            new MunicpioDTO(12, "Guapó"),
            new MunicpioDTO(13, "Hidrolândia"),
            new MunicpioDTO(14, "Inhumas"),
            new MunicpioDTO(15, "Nerópolis"),
            new MunicpioDTO(16, "Nova Veneza"),
            new MunicpioDTO(17, "Santo Antônio de Goiás"),
            new MunicpioDTO(18, "Senador Canedo"),
            new MunicpioDTO(19, "Terezópolis de Goiás"),
            new MunicpioDTO(20, "Trindade")
        );
    }
}
