
-- SEQUENCES

CREATE SEQUENCE seq_plano START 1;
CREATE SEQUENCE seq_instrutor START 1;
CREATE SEQUENCE seq_aula START 1;
CREATE SEQUENCE seq_frequencia START 1;


-- TABELA PLANO

CREATE TABLE plano (

    pln_id INT PRIMARY KEY,

    pln_nome VARCHAR(100) NOT NULL,

    pln_descricao VARCHAR(255),

    pln_valor NUMERIC(10,2) NOT NULL
        CHECK (pln_valor > 0),

    pln_duracao_meses INT NOT NULL
        CHECK (pln_duracao_meses > 0),

    pln_beneficios VARCHAR(255)
);


-- TABELA INSTRUTOR

CREATE TABLE instrutor (

    id_instrutor INT PRIMARY KEY,

    ins_cpf VARCHAR(14) UNIQUE NOT NULL,

    ins_primeiro_nome VARCHAR(50) NOT NULL,

    ins_nome_meio VARCHAR(50),

    ins_ultimo_nome VARCHAR(50) NOT NULL,

    ins_telefone VARCHAR(20) NOT NULL,

    ins_especialidade VARCHAR(100) NOT NULL,

    ins_horario_trabalho VARCHAR(100) NOT NULL
);


-- TABELA ALUNO

CREATE TABLE aluno (

    id_aluno SERIAL PRIMARY KEY,

    aln_cpf VARCHAR(14) UNIQUE NOT NULL,

    aln_primeiro_nome VARCHAR(50) NOT NULL,

    aln_nome_meio VARCHAR(50),

    aln_ultimo_nome VARCHAR(50) NOT NULL,

    aln_datanascimento DATE NOT NULL,

    aln_telefone VARCHAR(20) NOT NULL,

    aln_email VARCHAR(100) UNIQUE NOT NULL,

    aln_data_matricula DATE NOT NULL,

    pln_id INT NOT NULL,

    CONSTRAINT fk_aluno_plano
    FOREIGN KEY (pln_id)
    REFERENCES plano(pln_id)
);


-- TABELA AULA

CREATE TABLE aula (

    aul_id INT PRIMARY KEY,

    aul_nome VARCHAR(100) NOT NULL,

    aul_descricao VARCHAR(255) NOT NULL,

    aul_capacidade INT NOT NULL
        CHECK (aul_capacidade > 0),

    aul_horario TIME(0) NOT NULL,

    aul_duracao INT NOT NULL
        CHECK (aul_duracao > 0),

    id_instrutor INT NOT NULL,

    CONSTRAINT fk_aula_instrutor
    FOREIGN KEY (id_instrutor)
    REFERENCES instrutor(id_instrutor)
);


-- TABELA FREQUENCIA

CREATE TABLE frequencia (

    frq_id INT PRIMARY KEY,

    frq_data DATE NOT NULL,

    frq_hora_entrada TIME(0) NOT NULL,

    frq_hora_saida TIME(0),

    id_aluno INT NOT NULL,

    CONSTRAINT fk_frequencia_aluno
    FOREIGN KEY (id_aluno)
    REFERENCES aluno(id_aluno)
);


-- TABELA INSCRICAOAULA

CREATE TABLE inscricaoaula (

    id_aluno INT,

    aul_id INT,

    ina_data_inscricao DATE NOT NULL,

    PRIMARY KEY (id_aluno, aul_id),

    CONSTRAINT fk_inscricao_aluno
    FOREIGN KEY (id_aluno)
    REFERENCES aluno(id_aluno)
    ON DELETE CASCADE,

    CONSTRAINT fk_inscricao_aula
    FOREIGN KEY (aul_id)
    REFERENCES aula(aul_id)
    ON DELETE CASCADE
);


-- FUNCTION PLANO

CREATE OR REPLACE FUNCTION gerar_id_plano()
RETURNS TRIGGER AS
$$

BEGIN

    NEW.pln_id := nextval('seq_plano');

    RETURN NEW;

END;

$$ LANGUAGE plpgsql;


-- FUNCTION INSTRUTOR

CREATE OR REPLACE FUNCTION gerar_id_instrutor()
RETURNS TRIGGER AS
$$

BEGIN

    NEW.id_instrutor := nextval('seq_instrutor');

    RETURN NEW;

END;

$$ LANGUAGE plpgsql;


-- FUNCTION AULA

CREATE OR REPLACE FUNCTION gerar_id_aula()
RETURNS TRIGGER AS
$$

BEGIN

    NEW.aul_id := nextval('seq_aula');

    RETURN NEW;

END;

$$ LANGUAGE plpgsql;


-- FUNCTION FREQUENCIA

CREATE OR REPLACE FUNCTION gerar_id_frequencia()
RETURNS TRIGGER AS
$$

BEGIN

    NEW.frq_id := nextval('seq_frequencia');

    RETURN NEW;

END;

$$ LANGUAGE plpgsql;


-- TRIGGER PLANO

CREATE TRIGGER tg_plano_id

BEFORE INSERT
ON plano

FOR EACH ROW

EXECUTE FUNCTION gerar_id_plano();


-- TRIGGER INSTRUTOR

CREATE TRIGGER tg_instrutor_id

BEFORE INSERT
ON instrutor

FOR EACH ROW

EXECUTE FUNCTION gerar_id_instrutor();


-- TRIGGER AULA

CREATE TRIGGER tg_aula_id

BEFORE INSERT
ON aula

FOR EACH ROW

EXECUTE FUNCTION gerar_id_aula();


-- TRIGGER FREQUENCIA

CREATE TRIGGER tg_frequencia_id

BEFORE INSERT
ON frequencia

FOR EACH ROW

EXECUTE FUNCTION gerar_id_frequencia();