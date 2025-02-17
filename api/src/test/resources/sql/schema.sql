create table if not exists merchant
(
    id         bigint not null
        primary key,
    email   varchar(255) not null
        unique,
    password   varchar(255) not null,
    token      varchar(255) not null
        unique,
    name       varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);

create table if not exists payment_request
(
    id            bigint         not null
        primary key,
    order_id      varchar(255)   not null,
    order_name    varchar(255),
    order_status  varchar(255)   not null,
    account_id    bigint,
    card_number   varchar(255),
    payment_type  varchar(255),
    merchant_id   bigint   not null,
    payment_key   varchar(255),
    amount        numeric(38, 2) not null,
    payment_token varchar(255),
    request_time  timestamp(6)   not null,
    created_at    timestamp(6)   not null,
    updated_at    timestamp(6)   not null,
    merchant_name varchar(255)
);

create table if not exists user_card
(
    id                bigint       not null
        primary key,
    account_id        bigint       not null,
    is_representative boolean      not null,
    card_number       varchar(255) not null
        unique,
    expiration_period varchar(255) not null,
    cvc               varchar(255) not null,
    created_at        timestamp(6) not null,
    updated_at        timestamp(6) not null
);


create table if not exists payment
(
    id           bigint       not null
        primary key,
    payment_key  varchar(255) not null,
    card_number  varchar(255),
    currency     varchar(255) not null,
    account_id   bigint,
    merchant_id  bigint not null,
    payment_type varchar(255) not null,
    order_id     varchar(255) not null,
    order_name   varchar(255),
    created_at   timestamp(6) not null,
    updated_at   timestamp(6) not null
);

create table if not exists payment_transaction
(
    id           bigint         not null
        primary key,
    payment_key  varchar(255),
    amount       numeric(38, 2) not null,
    status       varchar(255)   not null,
    reason       varchar(255),
    requested_at timestamp(6)   not null,
    created_at   timestamp(6)   not null,
    updated_at   timestamp(6)   not null
);

create table if not exists account
(
    id         bigint                                 not null
        primary key,
    email      varchar(255)                           not null
        unique,
    password   varchar(255)                           not null,
    status     integer                                not null,
    created_at timestamp(6) default CURRENT_TIMESTAMP not null,
    updated_at timestamp(6) default CURRENT_TIMESTAMP not null
);

INSERT INTO account (id, email, password, status, created_at, updated_at) VALUES (293847562342874239, 'test@test.com', '$2a$10$S09UPOa5ZQh4n/Yb1PdRnuoJgWJ.f.Z20', 0, '2025-02-12 22:05:32.901464', '2025-02-12 22:05:32.901464');
INSERT INTO merchant (id, email, password, token, name, created_at, updated_at) VALUES (435345345, 'pay200', 'pay200', 'pay200', 'pay200', '2025-02-11 20:25:42.000000', '2025-02-11 20:25:42.000000');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, created_at, updated_at) VALUES (7295051915259393268, 5, true, '4567-8923-6378-3982', '03/28', '654', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, created_at, updated_at) VALUES (7295051915258292529, 3, true, '9876-5432-1098-7654', '01/27', '789', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, created_at, updated_at) VALUES (7295051915259438759, 2, true, '5678-1234-5678-9012', '10/23', '456', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, created_at, updated_at) VALUES (7295051915262387503, 293847562342874239, true, '1234-5678-9012-3456', '12/25', '123', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, created_at, updated_at) VALUES (7295051915258934220, 1, true, '4321-8765-4321-8765', '03/24', '987', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');


