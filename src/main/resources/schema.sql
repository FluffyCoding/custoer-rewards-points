CREATE TABLE IF NOT EXISTS customer
(
    id         bigint auto_increment not null,
    first_name varchar(50)           not null,
    last_name  varchar(50)           not null,
    email      varchar(50)           not null unique,
    primary key (id)
);

CREATE TABLE transactions
(
    id                bigint auto_increment not null,
    transaction_amount decimal               not null,
    points_awarded     smallint,
    transaction_date  DATE,
    customer_id       bigint,
    primary key (id),
    foreign key (customer_id) references customer (id)
);

INSERT INTO customer values (default,'Faisal','Shahzad','f.shahzad@hotmail.com');
INSERT INTO customer values (default,'Umer','Khan','umer@hotmail.com');
INSERT INTO customer values (default,'safa','mrawah','safa@hotmail.com');
INSERT INTO customer values (default,'Noreen','Khan','j.khan@hotmail.com');
INSERT INTO customer values (default,'Jameel','Raja','jameel@hotmail.com');

INSERT INTO transactions values (default,797.00,1444,'2023-03-23',2);
INSERT INTO transactions values (default,1126.00,2002,'2023-02-03',3);
INSERT INTO transactions values (default,531.00,912,'2023-01-05',2);
INSERT INTO transactions values (default,477.00,804,'2023-02-07',2);
INSERT INTO transactions values (default,744.00,1338,'2023-01-03',1);
INSERT INTO transactions values (default,997.00,1844,'2023-03-24',2);
INSERT INTO transactions values (default,652.00,1154,'2023-01-28',2);
INSERT INTO transactions values (default,1078.00,2006,'2023-01-13',3);
INSERT INTO transactions values (default,657.00,1164,'2023-02-25',2);
INSERT INTO transactions values (default,623.00,1096,'2023-02-09',2);
INSERT INTO transactions values (default,790.00,1430,'2023-01-03',1);
INSERT INTO transactions values (default,1133.00,2116,'2023-03-28',2);


