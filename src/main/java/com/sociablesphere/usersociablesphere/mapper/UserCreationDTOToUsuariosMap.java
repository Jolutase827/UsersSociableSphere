package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import com.sociablesphere.usersociablesphere.privacy.ApiTokenGenerator;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;

public class UserCreationDTOToUsuariosMap extends PropertyMap<UserCreationDTO, Usuarios> {

    @Override
    protected void configure() {

        using(ctx -> PasswordUtil.hashPassword(((UserCreationDTO) ctx.getSource()).getPassword()))
                .map(source, destination.getPassword());
        using(ctx -> ApiTokenGenerator.generateRandomApiToken())
                .map(source, destination.getApiToken());
        using(ctx -> LocalDateTime.now())
                .map(source, destination.getCreatedAt());
        using(ctx -> LocalDateTime.now())
                .map(source, destination.getUpdatedAt());
        using(ctx -> 0D)
                .map(source, destination.getWallet());
    }
}
