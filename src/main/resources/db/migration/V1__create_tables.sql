CREATE TABLE IF NOT EXISTS cg_usuario (
    id_usuario BIGSERIAL PRIMARY KEY,
    nome_usuario VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,
    senha VARCHAR(255) NOT NULL,
    papel VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cg_servico (
    id_servico BIGSERIAL PRIMARY KEY,
    id_prestador BIGINT NOT NULL,
    titulo VARCHAR(255),
    descricao VARCHAR(255),
    categoria VARCHAR(255),
    preco NUMERIC,
    situacao VARCHAR(255),
    CONSTRAINT fk_cg_prestador_usuario
        FOREIGN KEY (id_prestador) REFERENCES cg_usuario (id_usuario)
);

CREATE TABLE IF NOT EXISTS cg_contratacao (
    id_contratacao BIGSERIAL PRIMARY KEY,
    id_servico BIGINT NOT NULL,
    id_contratante BIGINT NOT NULL,
    situacao VARCHAR(255),
    CONSTRAINT fk_cg_contratacao_servico
        FOREIGN KEY (id_servico) REFERENCES cg_servico (id_servico),
    CONSTRAINT fk_cg_contratacao_contratante
        FOREIGN KEY (id_contratante) REFERENCES cg_usuario (id_usuario)
);