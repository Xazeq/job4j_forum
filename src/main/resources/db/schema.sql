CREATE TABLE IF NOT EXISTS authorities(
    id        serial primary key,
    authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE IF NOT EXISTS users(
    id           serial primary key,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL,
    enabled      BOOLEAN DEFAULT true,
    authority_id INT NOT NULL REFERENCES authorities (id)
);

CREATE TABLE IF NOT EXISTS comments(
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    created   TIMESTAMP NOT NULL DEFAULT now(),
    author_id   INT NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS posts(
    id SERIAL   PRIMARY KEY,
    name        VARCHAR (200) NOT NULL,
    description TEXT NOT NULL,
    created     TIMESTAMP NOT NULL DEFAULT now(),
    user_id     INT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS posts_comments(
    post_id    INT REFERENCES posts(id),
    comment_id INT REFERENCES comments(id)
);
