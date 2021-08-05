package ru.geekbrains.shop.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.geekbrains.shop.dto.RoleDTO;

@Component
public class StringToRoleDTOConverter implements Converter<String, RoleDTO> {

    @Override
    public RoleDTO convert(String s) {
        String[] arr = s.split(";");
        return new RoleDTO(Long.parseLong(arr[0]), arr[1]);
    }
}
