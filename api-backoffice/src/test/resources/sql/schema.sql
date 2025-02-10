create table if not exists payment_transaction
(
    id           bigint         not null  primary key,
    payment_key  varchar(255)   not null,
    amount       numeric(38, 2) not null,
    status       varchar(255)   not null,
    reason       varchar(255),
    requested_at timestamp(6)   not null,
    created_at   timestamp(6)   not null,
    updated_at   timestamp(6)   not null
);
