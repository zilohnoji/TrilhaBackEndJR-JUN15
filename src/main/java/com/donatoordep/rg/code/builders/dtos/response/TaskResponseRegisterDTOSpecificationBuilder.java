package com.donatoordep.rg.code.builders.dtos.response;

import com.donatoordep.rg.code.builders.Builder;
import com.donatoordep.rg.code.dtos.response.TaskResponseRegisterDTO;

import java.time.Instant;
import java.util.UUID;

public interface TaskResponseRegisterDTOSpecificationBuilder extends Builder<TaskResponseRegisterDTO> {

    TaskResponseRegisterDTOSpecificationBuilder identifier(UUID identifier);

    TaskResponseRegisterDTOSpecificationBuilder issuer(String issuer);

    TaskResponseRegisterDTOSpecificationBuilder createdAt(Instant createdAt);
}