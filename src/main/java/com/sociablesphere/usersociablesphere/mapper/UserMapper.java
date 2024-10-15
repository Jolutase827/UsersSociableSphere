package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import org.modelmapper.ModelMapper;

public class UserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        configureMappings();
    }

    private static void configureMappings() {
        modelMapper.addMappings(new UserCreationDTOToUsuariosMap());
    }

    public static Usuarios toUser(UserCreationDTO userCreationDTO) {
        return modelMapper.map(userCreationDTO, Usuarios.class);
    }

    public static UserResponseDTO toUserResponseDTO(Usuarios user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public static UserDetailDTO toUserDetailDTO(Usuarios user) {
        return modelMapper.map(user, UserDetailDTO.class);
    }
}
