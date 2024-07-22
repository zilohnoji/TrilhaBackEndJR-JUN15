package com.donatoordep.rg.code.builders.dtos.response;

import com.donatoordep.rg.code.builders.Builder;
import com.donatoordep.rg.code.dtos.response.TaskResponseGetAllDTO;

import java.util.UUID;

public interface TaskResponseGetAllDTOSpecificationBuilder extends Builder<TaskResponseGetAllDTO> {

    TaskResponseGetAllDTOSpecificationBuilder identifier(UUID identifier);

    TaskResponseGetAllDTOSpecificationBuilder title(String title);

    TaskResponseGetAllDTOSpecificationBuilder content(String content);

    TaskResponseGetAllDTOSpecificationBuilder status(String status);
}