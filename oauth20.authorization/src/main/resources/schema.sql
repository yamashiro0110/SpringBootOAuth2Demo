CREATE TABLE IF NOT EXISTS clients (
    `client_id` VARCHAR(255) NOT NULL PRIMARY KEY,
    `created` DATETIME NOT NULL,
    `updated` DATETIME NOT NULL,
    `client_secret` VARCHAR(255) NOT NULL,
    `client_name` VARCHAR(255) NOT NULL,
    `access_token_validity` INT NOT NULL,
    `refresh_token_validity` INT NOT NULL
);

CREATE TABLE IF NOT EXISTS scopes (
    `client_id` VARCHAR(255) NOT NULL,
    `scope` VARCHAR(255) NOT NULL,
    `created` DATETIME NOT NULL DEFAULT now(),
    CONSTRAINT fk_scopes_client_id FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

CREATE TABLE IF NOT EXISTS resource_ids (
    `client_id` VARCHAR(255) NOT NULL,
    `resource_id` VARCHAR(255) NOT NULL,
    `created` DATETIME NOT NULL DEFAULT now(),
    PRIMARY KEY(client_id, resource_id),
    CONSTRAINT fk_resource_ids_client_id FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

CREATE TABLE IF NOT EXISTS authorized_grant_types (
    `client_id` VARCHAR(255) NOT NULL,
    `authorized_grant_type` VARCHAR(255) NOT NULL,
    `created` DATETIME NOT NULL DEFAULT now(),
    PRIMARY KEY(client_id, authorized_grant_type),
    CONSTRAINT fk_authorized_grant_types_client_id FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

CREATE TABLE IF NOT EXISTS redirect_uris (
    `client_id` VARCHAR(255) NOT NULL,
    `redirect_uri` VARCHAR(255) NOT NULL,
    `created` DATETIME NOT NULL DEFAULT now(),
    PRIMARY KEY(client_id, redirect_uri),
    CONSTRAINT fk_redirect_uris_client_id FOREIGN KEY (client_id) REFERENCES clients(client_id)
);
