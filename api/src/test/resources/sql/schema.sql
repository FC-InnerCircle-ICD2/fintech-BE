create table if not exists merchant
(
    id         bigint not null
        primary key,
    email   varchar(255) not null
        unique,
    password   varchar(255) not null,
    name       varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);

create table if not exists api_key
(
    id         bigint not null
        primary key,
    merchant_id   bigint   not null,
    token      varchar(255) not null
        unique,
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
    card_company      varchar(255) not null,
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
    status     varchar(30)                            not null,
    created_at timestamp(6) default CURRENT_TIMESTAMP not null,
    updated_at timestamp(6) default CURRENT_TIMESTAMP not null
);

INSERT INTO account (id, email, password, status, created_at, updated_at) VALUES (293847562342874239, 'test@test.com', '$2a$10$8I6102l2DJvon6gkFJUHh.ZAeszd5swcabHdpr8iFsDXJ6WOZ51v2', 'ACTIVE', '2025-02-12 22:05:32.901464', '2025-02-12 22:05:32.901464');
INSERT INTO merchant (id, email, password, name, created_at, updated_at) VALUES (680785231122250286, 'pay200-shop@pay.com', '$2a$10$hk5caerjOASeaFaKPCwcZebXd/YRTcMJoAs0/sikxkvB6BQgZ5w7G', '테스트상점', '2025-02-11 20:25:42.000000', '2025-02-11 20:25:42.000000');
INSERT INTO api_key (id, merchant_id, token, created_at, updated_at) VALUES (679851702594364766, 680785231122250286, 'ilfwYbPuUuXl-cyWCB7J390A5IfLi7fSu7ZNXD4kreM=', '2025-02-11 20:25:42.000000', '2025-02-11 20:25:42.000000');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, card_company, created_at, updated_at) VALUES (7295051915259393268, 5, true, '4567-8923-6378-3982', '03/29', '654', '삼성', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, card_company, created_at, updated_at) VALUES (7295051915258292529, 3, true, '9876-5432-1098-7654', '01/27', '789', '신한', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, card_company, created_at, updated_at) VALUES (7295051915259438759, 2, true, '5678-1234-5678-9012', '10/26', '456', '현대', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, card_company, created_at, updated_at) VALUES (7295051915262387503, 293847562342874239, true, '1234-5678-9012-3456', '12/25', '123', '국민', '2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');
INSERT INTO user_card (id, account_id, is_representative, card_number, expiration_period, cvc, card_company, created_at, updated_at) VALUES (7295051915258934220, 1, true, '4321-8765-4321-8765', '03/28', '987', '비씨','2025-02-11 21:20:14.188948', '2025-02-11 21:20:14.188948');

INSERT INTO payment (id, payment_key, card_number, currency, account_id, merchant_id, payment_type, order_id, order_name, created_at, updated_at)
VALUES
  (7295051915259393200, 'pay_0001', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0001', 'Laptop Purchase', '2025-01-10 10:15:30', '2025-01-10 10:15:30'),
  (7295051915259393201, 'pay_0002', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0002', 'Smartphone', '2025-01-12 14:20:00', '2025-01-12 14:20:00'),
  (7295051915259393202, 'pay_0003', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0003', 'Tablet', '2025-01-14 18:30:45', '2025-01-14 18:30:45'),
  (7295051915259393203, 'pay_0004', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0004', 'Smartwatch', '2025-01-16 09:05:20', '2025-01-16 09:05:20'),
  (7295051915259393204, 'pay_0005', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0005', 'Wireless Earbuds', '2025-01-18 22:10:50', '2025-01-18 22:10:50'),
  (7295051915259393205, 'pay_0006', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0006', 'Monitor', '2025-01-20 08:45:35', '2025-01-20 08:45:35'),
  (7295051915259393206, 'pay_0007', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0007', 'Keyboard', '2025-01-22 13:55:10', '2025-01-22 13:55:10'),
  (7295051915259393207, 'pay_0008', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0008', 'Mouse', '2025-01-24 17:40:25', '2025-01-24 17:40:25'),
  (7295051915259393208, 'pay_0009', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0009', 'Gaming Console', '2025-01-26 21:15:55', '2025-01-26 21:15:55'),
  (7295051915259393209, 'pay_0010', '1234-5678-9012-3456', 'KRW', 680784466791780727, 680785231122250286, 'CARD', 'order_0010', 'External HDD', '2025-01-28 11:05:40', '2025-01-28 11:05:40');

INSERT INTO payment_transaction (id, payment_key, amount, status, reason, requested_at, created_at, updated_at)
VALUES
  (7295051915259393300, 'pay_0001', 1500.00, 'APPROVED', NULL, '2025-01-10 10:10:30', '2025-01-10 10:15:30', '2025-01-10 10:15:30'),
  (7295051915259393301, 'pay_0001', -1500.00, 'CANCELED', 'Customer returned item', '2025-01-15 11:00:00', '2025-01-15 11:05:00', '2025-01-15 11:05:00'),
  (7295051915259393302, 'pay_0002', 800.00, 'APPROVED', NULL, '2025-01-12 14:15:00', '2025-01-12 14:20:00', '2025-01-12 14:20:00'),
  (7295051915259393303, 'pay_0003', 1200.00, 'APPROVED', NULL, '2025-01-14 18:25:45', '2025-01-14 18:30:45', '2025-01-14 18:30:45'),
  (7295051915259393304, 'pay_0003', -1200.00, 'CANCELED', 'Payment failed', '2025-01-14 19:00:00', '2025-01-14 19:05:00', '2025-01-14 19:05:00'),
  (7295051915259393305, 'pay_0004', 300.00, 'APPROVED', NULL, '2025-01-16 09:00:20', '2025-01-16 09:05:20', '2025-01-16 09:05:20'),
  (7295051915259393306, 'pay_0005', 150.00, 'APPROVED', NULL, '2025-01-18 22:05:50', '2025-01-18 22:10:50', '2025-01-18 22:10:50'),
  (7295051915259393307, 'pay_0006', 500.00, 'APPROVED', NULL, '2025-01-20 08:40:35', '2025-01-20 08:45:35', '2025-01-20 08:45:35'),
  (7295051915259393308, 'pay_0006', -500.00, 'CANCELED', 'Defective product', '2025-01-21 10:00:00', '2025-01-21 10:05:00', '2025-01-21 10:05:00'),
  (7295051915259393309, 'pay_0007', 80.00, 'APPROVED', NULL, '2025-01-22 13:50:10', '2025-01-22 13:55:10', '2025-01-22 13:55:10'),
  (7295051915259393310, 'pay_0008', 50.00, 'APPROVED', NULL, '2025-01-24 17:35:25', '2025-01-24 17:40:25', '2025-01-24 17:40:25'),
  (7295051915259393311, 'pay_0009', 400.00, 'APPROVED', NULL, '2025-01-26 21:10:55', '2025-01-26 21:15:55', '2025-01-26 21:15:55'),
  (7295051915259393312, 'pay_0009', -400.00, 'CANCELED', 'Payment failed', '2025-01-27 08:30:00', '2025-01-27 08:35:00', '2025-01-27 08:35:00'),
  (7295051915259393313, 'pay_0010', 250.00, 'APPROVED', NULL, '2025-01-28 11:00:40', '2025-01-28 11:05:40', '2025-01-28 11:05:40'),
  (7295051915259393314, 'pay_0010', -250.00, 'CANCELED', 'Customer returned item', '2025-01-29 14:15:00', '2025-01-29 14:20:00', '2025-01-29 14:20:00');
