CREATE TABLE grid (
    id SMALLSERIAL PRIMARY KEY,
    size_x INTEGER NOT NULL,
    size_y INTEGER NOT NULL
);

CREATE TABLE entity (
    id SMALLSERIAL PRIMARY KEY,
    grid_id INTEGER,
    pos_x INTEGER NOT NULL,
    pos_y INTEGER NOT NULL,
    CONSTRAINT entity_grid_id_fkey FOREIGN KEY (grid_id)
        REFERENCES grid (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE dinosaur (

) INHERITS (entity);

CREATE TABLE robot (
    direction VARCHAR(1) NOT NULL
) INHERITS (entity);
