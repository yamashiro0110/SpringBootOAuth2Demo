INSERT INTO clients(client_id, created, updated, client_secret, client_name, access_token_validity, refresh_token_validity) VALUES
('client_id', now(), now(), 'client_secret', 'client_name', 3600, 86400);

INSERT INTO scopes(client_id, scope) VALUES
('client_id', 'read'),
('client_id', 'write');

INSERT INTO resource_ids(client_id, resource_id) VALUES
('client_id', 'resource_1'),
('client_id', 'resource_2');

INSERT INTO authorized_grant_types(client_id, authorized_grant_type) VALUES
('client_id', 'authorization_code'),
('client_id', 'password'),
('client_id', 'client_credentials');

INSERT INTO redirect_uris(client_id, redirect_uri) VALUES
('client_id', 'http://localhost:8080/client/api/simple/client');
