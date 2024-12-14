## * *users table*

create table users(

user_id int primary key auto_increment,

username varchar(200) unique not null,

email varchar(200) unique not null,

password varchar(200) unique not null,

created_at timestamp default current_timestamp

);

ex :

 desc users;

 insert into users(user_id,username,email,password) values(0,'KD','kd@gmail.com','12345');

select * from users;

truncate table users;

## *  products *table*

create table products(

product_id int primary key auto_increment,

name varchar(200) unique not null,

description text(200),

price decimal(8,2) not null,

stock_quantity int not null check (stock_quantity >= 0),

created_at timestamp default current_timestamp

);

ex:

desc products;

insert into products(name,description,price,stock_quantity) values ('marie','It is cookie',10.00,3);

select * from products;

truncate table products;

## * cart *table*

create table cart(

cart_id int primary key auto_increment,

user_id int unique not null,

product_id int unique not null,

quantity int not null check(quantity>0),

added_at timestamp default current_timestamp,

foreign key(user_id) references users(user_id) on delete cascade on update cascade,

foreign key(product_id) references products(product_id) on delete cascade on update cascade

);

ex :

desc cart;

insert into cart (user_id, product_id, quantity) values(1, 1, 1) on duplicate key update quantity = quantity + 1;

truncate table cart;

## * orders table

create table orders(

order_id int primary key auto_increment,

user_id int not null,

total_price decimal(8,2) not null,

order_status enum('Pending', 'Processing', 'Shipped', 'Delivered', 'Cancelled') not null,

order_date timestamp default current_timestamp,

foreign key(user_id) references users(user_id) on delete cascade on update cascade

);

ex :

desc orders;

insert into orders (user_id, total_price, order_status) values
(1, 1200.99, 'Processing'),
(2, 1598.98, 'Pending');

select * from orders;

truncate table orders;


## * orders_item table

create table order_item(

order_item_id int primary key auto_increment,

order_id int not null,

product_id int not null,

quantity int not null,

price decimal(8,2) not null,

foreign key(order_id) references orders(order_id) on delete cascade on update cascade,

foreign key(product_id) references products(product_id) on delete cascade on update cascade,

constraint fk_order_exists foreign key (order_id) references orders(order_id) on delete cascade

);

ex : 

desc order_item;

insert into order_item (order_id, product_id, quantity, price) values
(1, 1, 1, 1200.99),
(2, 2, 2, 799.49);

truncate table orders_item;



## * transaction_history table

create table transaction_history(

transaction_id int primary key auto_increment,

order_id int not null,

user_id int not null,

amount decimal(8,2) not null,

transaction_date timestamp default current_timestamp,

payment_method enum('Credit Card', 'PayPal', 'Bank Transfer') not null,

status enum('Success', 'Failed', 'Pending') not null,

foreign key(order_id) references orders(order_id) on delete cascade on update cascade,

foreign key(user_id) references users(user_id) on delete cascade on update cascade

);

ex :

desc transaction_history;

insert into transaction_history (order_id, user_id, amount, payment_method, status) values
(1, 1, 1200.99, 'Credit Card', 'Success'),
(2, 2, 1598.98, 'PayPal', 'Pending');

select * from transaction_history;

truncate table transaction_history;


when there is problem while truncating

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Cart;
SET FOREIGN_KEY_CHECKS = 1;
