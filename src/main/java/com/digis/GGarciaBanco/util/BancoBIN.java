package com.digis.GGarciaBanco.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BancoBIN {
    INBURSA_DEBITO("421316", 16),
    SANTANDER_DEBITO("491333", 16),
    BANAMEX_DEBITO("520416", 16),
    BBVA_DEBITO("455511", 16);

    private final String bin;
    private final int longitd;

}
