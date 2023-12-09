package com.sd64.novastore.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeDetailResponse {

    private Integer id;

    private String name;

    private List<Integer> listColorId;

    private List<PropertiesResponse> propertiesResponseList;
}
