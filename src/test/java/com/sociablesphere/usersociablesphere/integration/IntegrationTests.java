package com.sociablesphere.usersociablesphere.integration;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class IntegrationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    private static final String BASE_URL = "/v1/users";

    @AfterEach
    void tearDown() {
        userRepository.deleteAll().block();
    }

    @Nested
    @DisplayName("POST /v1/users/create")
    class RegisterUsuariosTests {

        @Test
        @DisplayName("1 llamada a POST /v1/users/create")
        void testRegisterUser_1Call() {
            int numberOfCalls = 1;
            long totalTime = makeMultipleRegisterCalls(numberOfCalls);
            System.out.println("Tiempo total para 1 llamada: " + totalTime + " ms");
        }

        @Test
        @DisplayName("10 llamadas a POST /v1/users/create")
        void testRegisterUser_10Calls() {
            int numberOfCalls = 10;
            long totalTime = makeMultipleRegisterCalls(numberOfCalls);
            System.out.println("Tiempo total para 10 llamadas: " + totalTime + " ms");
        }

        @Test
        @DisplayName("100 llamadas a POST /v1/users/create")
        void testRegisterUser_100Calls() {
            int numberOfCalls = 100;
            long totalTime = makeMultipleRegisterCalls(numberOfCalls);
            System.out.println("Tiempo total para 100 llamadas: " + totalTime + " ms");
        }

        private long makeMultipleRegisterCalls(int numberOfCalls) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfCalls; i++) {
                UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                        .userName("testuser" + i)
                        .name("Test")
                        .lastName("User")
                        .email("testuser" + i + "@example.com")
                        .password("Password123")
                        .role("USER")
                        .photo("photoUrl")
                        .description("Test description")
                        .build();

                webTestClient.post()
                        .uri(BASE_URL + "/create")
                        .bodyValue(userCreationDTO)
                        .exchange()
                        .expectStatus().isCreated()
                        .expectBody(UserDetailDTO.class)
                        .value(userDetail -> {
                            assertThat(userDetail.getUserName()).isEqualTo(userCreationDTO.getUserName());
                        });
            }

            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

    @Nested
    @DisplayName("POST /v1/users/login")
    class LoginUsuariosTests {

        @BeforeEach
        void setupUsers() {
            // Crear usuarios para las pruebas de login
            Flux<Usuarios> users = Flux.range(0, 100)
                    .map(i -> Usuarios.builder()
                            .userName("testuser" + i)
                            .name("Test")
                            .lastName("User")
                            .email("testuser" + i + "@example.com")
                            .password(PasswordUtil.hashPassword("Password123"))
                            .role("USER")
                            .photo("photoUrl")
                            .description("Test description")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
                    );
            userRepository.saveAll(users).blockLast();
        }

        @Test
        @DisplayName("1 llamada a POST /v1/users/login")
        void testLoginUser_1Call() {
            int numberOfCalls = 1;
            long totalTime = makeMultipleLoginCalls(numberOfCalls);
            System.out.println("Tiempo total para 1 llamada: " + totalTime + " ms");
        }

        @Test
        @DisplayName("10 llamadas a POST /v1/users/login")
        void testLoginUser_10Calls() {
            int numberOfCalls = 10;
            long totalTime = makeMultipleLoginCalls(numberOfCalls);
            System.out.println("Tiempo total para 10 llamadas: " + totalTime + " ms");
        }

        @Test
        @DisplayName("100 llamadas a POST /v1/users/login")
        void testLoginUser_100Calls() {
            int numberOfCalls = 100;
            long totalTime = makeMultipleLoginCalls(numberOfCalls);
            System.out.println("Tiempo total para 100 llamadas: " + totalTime + " ms");
        }

        private long makeMultipleLoginCalls(int numberOfCalls) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfCalls; i++) {
                UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                        .email("testuser" + i + "@example.com")
                        .password("Password123")
                        .build();

                webTestClient.post()
                        .uri(BASE_URL + "/login")
                        .bodyValue(userLoginDTO)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(UserDetailDTO.class)
                        .value(userDetail -> {
                            assertThat(userDetail.getEmail()).isEqualTo(userLoginDTO.getEmail());
                        });
            }

            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

    @Nested
    @DisplayName("GET /v1/users")
    class GetAllUsersTests {

        @BeforeEach
        void setupUsers() {
            // Crear usuarios para las pruebas de obtención
            Flux<Usuarios> users = Flux.range(0, 10) // Puedes ajustar el número de usuarios
                    .map(i -> Usuarios.builder()
                            .userName("testuser" + i)
                            .name("Test")
                            .lastName("User")
                            .email("testuser" + i + "@example.com")
                            .password(PasswordUtil.hashPassword("Password123"))
                            .role("USER")
                            .photo("photoUrl")
                            .description("Test description")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
                    );
            userRepository.saveAll(users).blockLast();
        }

        @Test
        @DisplayName("1 llamada a GET /v1/users")
        void testGetAllUsers_1Call() {
            int numberOfCalls = 1;
            long totalTime = makeMultipleCalls(numberOfCalls);
            System.out.println("Tiempo total para 1 llamada: " + totalTime + " ms");
        }

        @Test
        @DisplayName("10 llamadas a GET /v1/users")
        void testGetAllUsers_10Calls() {
            int numberOfCalls = 10;
            long totalTime = makeMultipleCalls(numberOfCalls);
            System.out.println("Tiempo total para 10 llamadas: " + totalTime + " ms");
        }

        @Test
        @DisplayName("100 llamadas a GET /v1/users")
        void testGetAllUsers_100Calls() {
            int numberOfCalls = 100;
            long totalTime = makeMultipleCalls(numberOfCalls);
            System.out.println("Tiempo total para 100 llamadas: " + totalTime + " ms");
        }

        private long makeMultipleCalls(int numberOfCalls) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfCalls; i++) {
                webTestClient.get()
                        .uri(BASE_URL)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(UserDetailDTO.class)
                        .value(userList -> {
                            assertThat(userList).isNotEmpty();
                        });
            }

            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

    @Nested
    @DisplayName("PUT /v1/users/{id}/password")
    class UpdatePasswordTests {

        private Usuarios usuarios;

        @BeforeEach
        void setupUser() {
            usuarios = Usuarios.builder()
                    .userName("testuser")
                    .name("Test")
                    .lastName("User")
                    .email("testuser@example.com")
                    .password(PasswordUtil.hashPassword("OldPassword123"))
                    .role("USER")
                    .photo("photoUrl")
                    .description("Test description")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            usuarios = userRepository.save(usuarios).block();
        }

        @Test
        @DisplayName("1 llamada a PUT /v1/users/{id}/password")
        void testUpdatePassword_1Call() {
            int numberOfCalls = 1;
            long totalTime = makeMultipleUpdatePasswordCalls(numberOfCalls);
            System.out.println("Tiempo total para 1 llamada: " + totalTime + " ms");
        }

        @Test
        @DisplayName("10 llamadas a PUT /v1/users/{id}/password")
        void testUpdatePassword_10Calls() {
            int numberOfCalls = 10;
            long totalTime = makeMultipleUpdatePasswordCalls(numberOfCalls);
            System.out.println("Tiempo total para 10 llamadas: " + totalTime + " ms");
        }

        @Test
        @DisplayName("100 llamadas a PUT /v1/users/{id}/password")
        void testUpdatePassword_100Calls() {
            int numberOfCalls = 100;
            long totalTime = makeMultipleUpdatePasswordCalls(numberOfCalls);
            System.out.println("Tiempo total para 100 llamadas: " + totalTime + " ms");
        }

        private long makeMultipleUpdatePasswordCalls(int numberOfCalls) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfCalls; i++) {
                usuarios.setPassword(PasswordUtil.hashPassword("OldPassword123"));
                userRepository.save(usuarios).block();

                UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                        .oldPassword("OldPassword123")
                        .newPassword("NewPassword123")
                        .build();

                webTestClient.put()
                        .uri(BASE_URL + "/" + usuarios.getId() + "/password")
                        .bodyValue(userPasswordDTO)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(UserDetailDTO.class)
                        .value(userDetail -> {
                            assertThat(userDetail.getEmail()).isEqualTo(usuarios.getEmail());
                        });
            }

            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

    @Nested
    @DisplayName("PUT /v1/users/{id}")
    class UpdateUsuariosTests {

        private Usuarios usuarios;

        @BeforeEach
        void setupUser() {
            usuarios = Usuarios.builder()
                    .userName("testuser")
                    .name("Test")
                    .lastName("User")
                    .email("testuser@example.com")
                    .password(PasswordUtil.hashPassword("Password123"))
                    .role("USER")
                    .photo("photoUrl")
                    .description("Test description")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(usuarios).block();
        }

        @Test
        @DisplayName("1 llamada a PUT /v1/users/{id}")
        void testUpdateUser_1Call() {
            int numberOfCalls = 1;
            long totalTime = makeMultipleUpdateUserCalls(numberOfCalls);
            System.out.println("Tiempo total para 1 llamada: " + totalTime + " ms");
        }

        @Test
        @DisplayName("10 llamadas a PUT /v1/users/{id}")
        void testUpdateUser_10Calls() {
            int numberOfCalls = 10;
            long totalTime = makeMultipleUpdateUserCalls(numberOfCalls);
            System.out.println("Tiempo total para 10 llamadas: " + totalTime + " ms");
        }

        @Test
        @DisplayName("100 llamadas a PUT /v1/users/{id}")
        void testUpdateUser_100Calls() {
            int numberOfCalls = 100;
            long totalTime = makeMultipleUpdateUserCalls(numberOfCalls);
            System.out.println("Tiempo total para 100 llamadas: " + totalTime + " ms");
        }

        private long makeMultipleUpdateUserCalls(int numberOfCalls) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfCalls; i++) {
                UserCreationDTO updateDTO = UserCreationDTO.builder()
                        .userName("updateduser" + i)
                        .name("Updated")
                        .lastName("User")
                        .email("updateduser" + i + "@example.com")
                        .password("Password123")
                        .role("USER")
                        .photo("photoUrl")
                        .description("Updated description")
                        .build();

                webTestClient.put()
                        .uri(BASE_URL + "/" + usuarios.getId())
                        .bodyValue(updateDTO)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(UserDetailDTO.class)
                        .value(userDetail -> {
                            assertThat(userDetail.getUserName()).isEqualTo(updateDTO.getUserName());
                        });
            }

            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

    @Nested
    @DisplayName("DELETE /v1/users/{id}")
    class DeleteUsuariosTests {

        @BeforeEach
        void setupUsers() {
            Flux<Usuarios> users = Flux.range(0, 100)
                    .map(i -> Usuarios.builder()
                            .userName("testuser" + i)
                            .name("Test")
                            .lastName("User")
                            .email("testuser" + i + "@example.com")
                            .password("Password123")
                            .role("USER")
                            .photo("photoUrl")
                            .description("Test description")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
                    );
            userRepository.saveAll(users).blockLast();
        }

        @Test
        @DisplayName("1 llamada a DELETE /v1/users/{id}")
        void testDeleteUser_1Call() {
            int numberOfCalls = 1;
            long totalTime = makeMultipleDeleteUserCalls(401,numberOfCalls);
            System.out.println("Tiempo total para 1 llamada: " + totalTime + " ms");
        }

        @Test
        @DisplayName("10 llamadas a DELETE /v1/users/{id}")
        void testDeleteUser_10Calls() {
            int numberOfCalls = 10;
            long totalTime = makeMultipleDeleteUserCalls(501,numberOfCalls);
            System.out.println("Tiempo total para 10 llamadas: " + totalTime + " ms");
        }

        @Test
        @DisplayName("100 llamadas a DELETE /v1/users/{id}")
        void testDeleteUser_100Calls() {
            int numberOfCalls = 100;
            long totalTime = makeMultipleDeleteUserCalls(301,numberOfCalls);
            System.out.println("Tiempo total para 100 llamadas: " + totalTime + " ms");
        }

        private long makeMultipleDeleteUserCalls(int firstId,int numberOfCalls) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfCalls; i++) {
                Long userId = (long) firstId+i;

                webTestClient.delete()
                        .uri(BASE_URL + "/" + userId)
                        .exchange()
                        .expectStatus().isNoContent();
            }

            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

    @Nested
    @DisplayName("GET /apiToken tests")
    class GetUserByApiTokenTests {

        @BeforeEach
        void setupUsers() {
            Flux<Usuarios> users = Flux.range(0, 100)
                    .map(i -> Usuarios.builder()
                            .userName("testuser" + i)
                            .name("Test")
                            .lastName("User")
                            .email("testuser" + i + "@example.com")
                            .password(PasswordUtil.hashPassword("Password123"))
                            .role("USER")
                            .photo("photoUrl")
                            .description("Test description")
                            .apiToken("validApiToken" +i)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
                    );
            userRepository.saveAll(users).blockLast();
        }


        @Test
        @DisplayName("1 llamada a GET /apiToken")
        void testGetUserByApiToken_1Call() {
            int numberOfCalls = 1;
            long totalTime = makeMultipleCalls(numberOfCalls);
            System.out.println("Tiempo total para 1 llamada: " + totalTime + " ms");
        }

        @Test
        @DisplayName("10 llamadas a GET /apiToken")
        void testGetUserByApiToken_10Calls() {
            int numberOfCalls = 10;
            long totalTime = makeMultipleCalls(numberOfCalls);
            System.out.println("Tiempo total para 10 llamadas: " + totalTime + " ms");
        }

        @Test
        @DisplayName("100 llamadas a GET /apiToken")
        void testGetUserByApiToken_100Calls() {
            int numberOfCalls = 100;
            long totalTime = makeMultipleCalls(numberOfCalls);
            System.out.println("Tiempo total para 100 llamadas: " + totalTime + " ms");
        }

        private long makeMultipleCalls(int numberOfCalls) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfCalls; i++) {
                int finalI = i;
                int finalI1 = i;
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(BASE_URL+"/apiToken")
                                .queryParam("apiToken", "validApiToken"+ finalI)
                                .build())
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(UserDetailDTO.class)
                        .value(userDetailDTO -> {
                            assertThat(userDetailDTO).isNotNull();
                            assertThat(userDetailDTO.getUserName()).isEqualTo("testuser"+ finalI1);
                            assertThat(userDetailDTO.getEmail()).isEqualTo("testuser" + finalI1 + "@example.com");
                        });
            }

            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }
}
