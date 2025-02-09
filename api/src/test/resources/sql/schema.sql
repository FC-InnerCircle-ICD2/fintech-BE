create table if not exists  merchant
(
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    id         varchar(255) not null
        primary key,
    name       varchar(255) not null,
    password   varchar(255) not null,
    token      varchar(255) not null
        unique,
    username   varchar(255) not null
        unique
);

create table if not exists  payment
(
    account_id   bigint,
    created_at   timestamp(6) not null,
    id           bigint       not null
        primary key,
    updated_at   timestamp(6) not null,
    card_number  varchar(255),
    currency     varchar(255) not null,
    merchant_id  varchar(255) not null,
    order_id     varchar(255) not null,
    order_name   varchar(255),
    payment_key  varchar(255) not null,
    payment_type varchar(255) not null
);

create table if not exists  payment_request
(
    amount        numeric(38, 2) not null,
    account_id    bigint,
    created_at    timestamp(6)   not null,
    id            bigint         not null
        primary key,
    request_time  timestamp(6)   not null,
    updated_at    timestamp(6)   not null,
    card_number   varchar(255),
    merchant_id   varchar(255)   not null,
    order_id      varchar(255)   not null,
    order_name    varchar(255),
    order_status  varchar(255)   not null,
    payment_key   varchar(255),
    payment_token varchar(255),
    payment_type  varchar(255)
);


create table if not exists  payment_transaction
(
    amount          numeric(38, 2) not null,
    completion_time timestamp(6),
    created_at      timestamp(6)   not null,
    id              bigint         not null
        primary key,
    request_time    timestamp(6)   not null,
    updated_at      timestamp(6)   not null,
    payment_key     varchar(255)   not null,
    reason          varchar(255),
    status          varchar(255)   not null
);

create table if not exists  user_card
(
    is_representative boolean      not null,
    account_id        bigint       not null,
    created_at        timestamp(6) not null,
    id                bigint generated by default as identity
        primary key,
    updated_at        timestamp(6) not null,
    card_number       varchar(255) not null
        unique,
    cvc               varchar(255) not null,
    expiration_period varchar(255) not null
);

INSERT INTO merchant (created_at, updated_at, id, name, password, token, username) VALUES ('2025-02-05 19:18:16.000000', '2025-02-05 19:18:16.000000', '1', 'pay200', 'test1234', 'cGF5MjAwOg==', 'pay200-merchant');

CREATE TABLE IF NOT EXISTS account (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  uuid UUID NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  status INT NOT NULL,
  created_at TIMESTAMP(6) NOT NULL,
  updated_at TIMESTAMP(6) NOT NULL
);

COMMENT ON TABLE account IS '사용자 계정 정보를 저장하는 테이블';

COMMENT ON COLUMN account.id IS '계정의 기본 키 (Auto Increment)';
COMMENT ON COLUMN account.uuid IS '외부 API에서 식별할 수 있는 UUID';
COMMENT ON COLUMN account.email IS '사용자 이메일 (로그인 ID)';
COMMENT ON COLUMN account.password IS '사용자 비밀번호 (암호화 저장)';
COMMENT ON COLUMN account.status IS '계정 상태 (0: 활성, 1: 비활성, 2: 정지)';

