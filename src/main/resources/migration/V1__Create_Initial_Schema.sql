CREATE SCHEMA IF NOT EXISTS DZ8;

create table book_borrows
(
	id bigint not null
		constraint book_borrows_pkey
			primary key,
	borrowed_book_id bigint,
	expiry_date timestamp,
	reader_id bigint
);

create table book_category
(
	book_id bigint not null
		constraint fk7k0c5mr0rx89i8jy5ges23jpe
			references books,
	category_id bigint not null
		constraint fkiwvwb2bwuvg0017hh8kg5e8g1
			references categories,
	constraint book_category_pkey
		primary key (book_id, category_id)
);

create table book_copies
(
	id bigint not null
		constraint book_copies_pkey
			primary key,
	isbn varchar(255),
	original_id bigint,
	position varchar(255)
);

create table books
(
	id bigint not null
		constraint books_pkey
			primary key,
	author varchar(255),
	pages_amount integer,
	publisher_id bigint,
	title varchar(255),
	year integer
);

create table categories
(
	id bigint not null
		constraint categories_pkey
			primary key,
	parent_id bigint,
	title varchar(255)
		constraint uk_tkwloef0k6ccv94cipgnmvma8
			unique
);

create table publishers
(
	id bigint not null
		constraint publishers_pkey
			primary key,
	address varchar(255)
		constraint uk_6hfeubb8cqc6wuj84uu5k3u4v
			unique,
	name varchar(255)
		constraint uk_an1ucpx8sw2qm194mlok8e5us
			unique
);

create table readers
(
	id bigint not null
		constraint readers_pkey
			primary key,
	address varchar(255),
	date_of_birth timestamp,
	name varchar(255),
	surname varchar(255)
);