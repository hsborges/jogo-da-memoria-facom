INSERT OR IGNORE INTO jogadores (id, name, email, password) VALUES (1, 'Hudson Borges', 'hudson@ufms.br', '12345')
INSERT OR IGNORE INTO jogadores (id, name, email, password) VALUES (2, 'Awdren Fontao', 'fontao@ufms.br', '12345')
INSERT OR IGNORE INTO jogadores (id, name, email, password) VALUES (3, 'Bruno Cafeo', 'cafeo@ufms.br', '12345')
UPDATE sequence SET SEQ_COUNT = 4 WHERE SEQ_NAME = 'SEQ_GEN_IDENTITY' 