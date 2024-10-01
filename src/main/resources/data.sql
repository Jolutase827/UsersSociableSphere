CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    photo VARCHAR(255),
    description TEXT,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    wallet DECIMAL(10, 2),
    api_token VARCHAR(255) UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
INSERT INTO users (
    id, user_name, name, last_name, email, photo, description, password, role, wallet, api_token, created_at, updated_at
) VALUES (
    1, 'john_doe', 'John', 'Doe', 'john.doe@example.com', 'https://example.com/photos/john.jpg', 'Software Engineer with 10 years of experience.', 'hashedpassword123', 'USER', 100.50, 'api_token_12345', NOW(), NOW()
);

INSERT INTO users (
    id, user_name, name, last_name, email, photo, description, password, role, wallet, api_token, created_at, updated_at
) VALUES (
    2, 'jane_smith', 'Jane', 'Smith', 'jane.smith@example.com', 'https://example.com/photos/jane.jpg', 'Project Manager specializing in IT projects.', 'hashedpassword456', 'ADMIN', 250.00, 'api_token_67890', NOW(), NOW()
);
