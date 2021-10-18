CREATE TABLE IF NOT EXISTS public.users (
    id SERIAL PRIMARY KEY,
    user_name character varying(255),
    full_name character varying(255),
    password character varying(255),
    account_non_expired boolean NOT NULL DEFAULT false,
    account_non_locked boolean NOT NULL DEFAULT false,
    credentials_non_expired boolean NOT NULL DEFAULT false,
    enabled boolean NOT NULL DEFAULT false,
    UNIQUE(user_name)
);