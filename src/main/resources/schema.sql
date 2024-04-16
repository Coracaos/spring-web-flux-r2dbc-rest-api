CREATE TABLE IF NOT EXISTS public.product(
	product_id SERIAL PRIMARY KEY,
	name VARCHAR(80),
	description VARCHAR(2000),
	price NUMERIC(10,2),
	created_at TIMESTAMP CONSTRAINT nn_product_createat NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP
);
