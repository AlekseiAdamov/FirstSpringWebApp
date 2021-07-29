package ru.geekbrains.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserListParamsDTO {
    private String userName;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;
}
