package com.digis.GGarciaBanco.dto.sp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoredProcedureResult<T> {

    private Integer codigo;
    private String mensaje;

    private T object;
    private List<T> objects;
}
