package com.sociablesphere.usersociablesphere.model;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTests {
    @Nested
    @DisplayName("Update User Data")
    class UpdateUserData {

        @Test
        @DisplayName("Given a valid UserCreationDTO, Then update the user's data successfully")
        void updateUserDataValid() {
            // Given
            users user = users.builder().build();
            UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                    .description("New description")
                    .email("newemail@example.com")
                    .name("New Name")
                    .role("Admin")
                    .userName("newusername")
                    .photo("newphoto.jpg")
                    .lastName("New LastName")
                    .build();

            // When
            user.updateData(userCreationDTO);

            // Then
            assertThat(user)
                    .extracting(users::getDescription, users::getEmail, users::getName, users::getRole,
                            users::getUserName, users::getPhoto, users::getLastName)
                    .containsExactly("New description", "newemail@example.com", "New Name", "Admin",
                            "newusername", "newphoto.jpg", "New LastName");

            assertThat(user.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Given a UserCreationDTO with null fields, Then the user's data is updated with null values")
        void updateUserDataWithNullFields() {
            // Given
            users user = users.builder().build();
            UserCreationDTO userCreationDTO = UserCreationDTO.builder().build();

            // When
            user.updateData(userCreationDTO);

            // Then
            assertThat(user)
                    .extracting(users::getDescription, users::getEmail, users::getName, users::getRole,
                            users::getUserName, users::getPhoto, users::getLastName)
                    .containsExactly(null, null, null, null, null, null, null);

            assertThat(user.getUpdatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Ensure updateData updates updatedAt field to the current time")
        void updateUpdatedAtField() {
            // Given
            users user = users.builder().build();
            LocalDateTime beforeUpdate = LocalDateTime.now();
            UserCreationDTO userCreationDTO = UserCreationDTO.builder().build();

            // When
            user.updateData(userCreationDTO);

            // Then
            assertThat(user.getUpdatedAt()).isAfterOrEqualTo(beforeUpdate);
        }
    }

}
