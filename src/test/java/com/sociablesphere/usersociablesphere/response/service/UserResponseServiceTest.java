package com.sociablesphere.usersociablesphere.response.service;


import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.response.builder.UserResponseBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.sociablesphere.usersociablesphere.response.Data.DataUserServiceImplTest.USER_RESPONSE_DTO;
import static com.sociablesphere.usersociablesphere.service.Data.DataUserServiceImplTest.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserResponseServiceTest {

    @InjectMocks
    private UserResponseService userResponseService;

    @Nested
    @DisplayName("Build Created Response")
    class BuildCreatedResponse {

        @Test
        @DisplayName("Given a valid UserDetailDTO, Then build a CREATED response")
        void buildCreatedResponseValid() {
            // Given
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;

            // When
            Mono<ResponseEntity<UserDetailDTO>> responseMono = userResponseService.buildCreatedResponse(userDetailDTO, userDetailDTO.getEmail());

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(201);
                assertThat(response.getBody()).isEqualTo(userDetailDTO);
            });
        }
    }

    @Nested
    @DisplayName("Build OK Response")
    class BuildOkResponse {

        @Test
        @DisplayName("Given a valid UserDetailDTO, Then build an OK response")
        void buildOkResponseValid() {
            // Given
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;

            // When
            Mono<ResponseEntity<UserDetailDTO>> responseMono = userResponseService.buildOkResponse(userDetailDTO);

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).isEqualTo(userDetailDTO);
            });
        }
    }

    @Nested
    @DisplayName("Build No Content Response")
    class BuildNoContentResponse {

        @Test
        @DisplayName("When building a No Content response, Then return a response with status 204")
        void buildNoContentResponse() {
            // When
            Mono<ResponseEntity<Void>> responseMono = userResponseService.buildNoContentResponse();

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(204);
                assertThat(response.getBody()).isNull();
            });
        }
    }

    @Nested
    @DisplayName("Build Users Response")
    class BuildUsersResponse {

        @Test
        @DisplayName("Given a valid UserResponseDTO, Then build an OK response")
        void buildUsersResponseValid() {
            // Given
            UserResponseDTO userResponseDTO = USER_RESPONSE_DTO;

            // When
            Flux<ResponseEntity<UserResponseDTO>> responseFlux = userResponseService.buildUsersResponse(userResponseDTO);

            // Then
            responseFlux.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).isEqualTo(userResponseDTO);
            });
        }
    }
}