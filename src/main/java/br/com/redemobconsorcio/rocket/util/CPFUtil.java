package br.com.redemobconsorcio.rocket.util;

public class CPFUtil {

    private CPFUtil() {}

    public static String removerMascara(String cpf) {
        return cpf.replace(".", "").replace("-", "");
    }
}
