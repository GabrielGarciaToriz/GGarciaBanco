package com.digis.ggarciabanco.util;

import com.digis.ggarciabanco.dto.Result;
import java.util.UUID;

public class GeneradorUUID {
    public static Result generarUUID(boolean incluirGuiones){
        try {
            String uuid = UUID.randomUUID().toString();
            if(!incluirGuiones){
                uuid = uuid.replace("-","");
            }
            return Result.ok(uuid);
            
        } catch (Exception e) {
            return Result.error("ERR_UUID","Error al generar el UUID");
        }
    }
}
