CREATE TABLE users (
    id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
);

CREATE TABLE membership_types (
  id int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  type_name varchar(50) NOT NULL,
  description varchar(255) DEFAULT NULL,
  amount decimal(10, 2) NOT NULL
);

CREATE TABLE members (
  id int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  fullname varchar(255) NOT NULL,
  dob date NOT NULL,
  gender varchar(10) NOT NULL,
  contact_number varchar(20) NOT NULL,
  email varchar(255) NOT NULL,
  address varchar(255) NOT NULL,
  country varchar(255) NOT NULL,
  postcode varchar(20) NOT NULL,
  occupation varchar(255) NOT NULL,
  membership_type_id int NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  expiry_date date DEFAULT NULL,
  FOREIGN KEY (membership_type_id) REFERENCES membership_types(id)
);
ALTER TABLE members
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
ADD COLUMN expiry_date DATE NULL DEFAULT NULL;
CREATE TABLE renew (
  id int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  member_id int NOT NULL,
  total_amount decimal(10, 2) NOT NULL,
  renew_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (member_id) REFERENCES members(id)
);
