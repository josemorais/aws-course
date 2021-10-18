CREATE TABLE public.tb_book
(
    id SERIAL PRIMARY KEY,
    author character varying,
    launch_date timestamp with time zone,
    price double precision,
    title character varying
)
