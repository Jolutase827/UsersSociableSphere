package com.sociablesphere.usersociablesphere.response.builder;


import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static com.sociablesphere.usersociablesphere.response.Data.DataUserServiceImplTest.USER_RESPONSE_DTO;
import static com.sociablesphere.usersociablesphere.service.Data.DataUserServiceImplTest.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserResponseBuilderTest {

    @Nested
    @DisplayName("Generate Created Response")
    class GenerateCreatedResponse {

        @Test
        @DisplayName("Given a valid UserDetailDTO, Then return a CREATED response")
        void generateCreatedResponseValid() {
            // Given
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;

            // When
            ResponseEntity<UserDetailDTO> responseEntity = UserResponseBuilder.generateCreatedResponse(userDetailDTO, userDetailDTO.getEmail());

            // Then
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
            assertThat(responseEntity.getBody()).isEqualTo(userDetailDTO);
        }
    }

    @Nested
    @DisplayName("Generate OK Response")
    class GenerateOkResponse {

        @Test
        @DisplayName("Given a valid UserDetailDTO, Then return an OK response")
        void generateOkResponseValid() {
            // Given
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;

            // When
            ResponseEntity<UserDetailDTO> responseEntity = UserResponseBuilder.generateOkResponse(userDetailDTO);

            // Then
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
            assertThat(responseEntity.getBody()).isEqualTo(userDetailDTO);
        }
    }

    @Nested
    @DisplayName("Generate No Content Response")
    class GenerateNoContentResponse {

        @Test
        @DisplayName("When generating a No Content response, Then return a response with status 204")
        void generateNoContentResponse() {
            // When
            ResponseEntity<Void> responseEntity = UserResponseBuilder.generateNoContentResponse();

            // Then
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
            assertThat(responseEntity.getBody()).isNull();
        }
    }

    @Nested
    @DisplayName("Generate User OK Response")
    class GenerateUserOkResponse {

        @Test
        @DisplayName("Given a valid UserResponseDTO, Then return an OK response")
        void generateUserOkResponseValid() {
            // Given
            UserResponseDTO userResponseDTO = USER_RESPONSE_DTO;

            // When
            ResponseEntity<UserResponseDTO> responseEntity = UserResponseBuilder.generateUserOkResponse(userResponseDTO);

            // Then
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
            assertThat(responseEntity.getBody()).isEqualTo(userResponseDTO);
        }
    }
}