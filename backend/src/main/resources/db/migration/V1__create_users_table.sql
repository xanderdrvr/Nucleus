CREATE SCHEMA IF NOT EXISTS nucleus;

SET search_path TO nucleus;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    display_name VARCHAR(255) NOT NULL UNIQUE,
    avatar_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL
  );