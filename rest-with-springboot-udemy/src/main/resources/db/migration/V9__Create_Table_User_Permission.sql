CREATE TABLE public.user_permission (
    id_user bigint NOT NULL,
    id_permission bigint NOT NULL,
    CONSTRAINT fk_user_permission FOREIGN KEY (id_user)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT,
    CONSTRAINT fk_user_permission_permission FOREIGN KEY (id_permission)
        REFERENCES public.permission (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
);