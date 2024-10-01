CREATE TABLE users (
    id Bi PRIMARY KEY,
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

-- Datos de ejemplo para la tabla 'users'
INSERT INTO users (
    id, user_name, name, last_name, email, photo, description, password, role, wallet, api_token, created_at, updated_at
) VALUES (
    '550e8400-e29b-41d4-a716-446655440000',
    'johndoe',
    'John',
    'Doe',
    'johndoe@example.com',
    'https://th.bing.com/th/id/R.660ba35a6fdaf53661d654625447a8de?rik=LIDc4v6h%2bfGJrg&pid=ImgRaw&r=0',
    'A regular user',
    'hashed_password_example',
    'USER',
    100.50,
    'some-api-token',
    NOW(),
    NOW()
);
INSERT INTO users (
    id, user_name, name, last_name, email, photo, description, password, role, wallet, api_token, created_at, updated_at
) VALUES (
    '123e4567-e89b-12d3-a456-426614174000',
    'maryjane',
    'Mary',
    'Jane',
    'maryjane@example.com',
    'https://example.com/jane_photo.jpg',
    'Enthusiastic community member',
    'hashed_password_example2',
    'ADMIN',
    250.75,
    'another-api-token',
    NOW(),
    NOW()
);

