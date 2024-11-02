CREATE TABLE movements (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    movement_type VARCHAR(15) NOT NULL CHECK (movement_type IN ('ENTRY', 'EXIT')),
    quantity INT NOT NULL CHECK (quantity >= 0),
    location VARCHAR(100),
    movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
