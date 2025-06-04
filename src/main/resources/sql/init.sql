CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(220) NOT NULL,
  country VARCHAR(120)
);

-- Add some sample data
INSERT INTO users (name, email, country) VALUES 
('John Doe', 'john@example.com', 'USA'),
('Jane Smith', 'jane@example.com', 'Canada'),
('Alice Johnson', 'alice@example.com', 'Australia');
