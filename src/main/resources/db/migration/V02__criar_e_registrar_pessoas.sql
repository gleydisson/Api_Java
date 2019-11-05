create table pessoa(
   codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
   nome VARCHAR(50) NOT NULL,
   ativo BOOLEAN NOT NULL,
   logradouro VARCHAR(50) NULL,
   numero VARCHAR(8) NULL,
   complemento VARCHAR(50) NULL,
   bairro VARCHAR(50) NULL,
   cep VARCHAR(9) NULL,
   cidade VARCHAR(50) NULL,
   estado VARCHAR(50) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values ('Gleydisson',true,'av Rosemount','158','Segundo andar','vila italia','2n2m6h','Toronto','Ontario');
