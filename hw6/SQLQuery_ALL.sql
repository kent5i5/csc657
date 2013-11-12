drop table test_drivers
drop table car
drop table customer
drop table sales_rep

create table sales_rep (
name varchar(10), 
dept varchar(10), 
phone varchar(10),
primary key (name))

insert into sales_rep values ('andy','eng','415999')
insert into sales_rep values ('john', 'csc','415212')
insert into sales_rep values ('kelly','env','415888')
insert into sales_rep values ('kent','csc','415657')
insert into sales_rep values ('teddy','toy','415567')
select * from sales_rep;

create table car (
vin varchar(10), 
make varchar(10),
model varchar(10),
color varchar(10),
seller varchar(10),
purchased_by varchar(10),
primary key (vin),
foreign key (seller) references sales_rep)

insert into car values ('001','bmw','bmw1','red','teddy','0000001')
insert into car values ('002','VW','Bug','yellow','kelly','0000003')
insert into car values ('003','toyota','toyota1','blue','john','0000001')
insert into car values ('004','honda','honda1','red','kent','0000002')
insert into car values ('005','honda','honda2','green','teddy','0000002')
select * from car;

create table customer (
license varchar(10),
cname varchar(10),
address varchar(10),
phone varchar(10),
primary key(license)
)

insert into customer values ('0000001' ,'kent', 'abc', '415657')
insert into customer values ('0000002' ,'kelly', 'cba', '415888')
insert into customer values ('0000003' ,'teddy', 'bac', '415567')
insert into customer values ('0000004' ,'john', 'aaa', '415333')
insert into customer values ('0000005' ,'Psmith', 'cab', '415890')
insert into customer values ('0000006' ,'andy', 'sss', '415555')
insert into customer values ('0000007' ,'walker', 'sls', '415505')
select * from customer

create table test_drivers (
vin varchar(10), 
license varchar(10), 
date datetime,
primary key (vin,license), 
foreign key (vin) references car, 
foreign key (license) references customer)

insert into test_drivers values ('001','0000001','2012-11-10 00:00:00.000')
insert into test_drivers values ('002','0000003','2012-11-09 00:00:00.000')
insert into test_drivers values ('003','0000001','2011-11-09 00:00:00.000')
insert into test_drivers values ('004','0000002','2011-06-10 00:00:00.000')
insert into test_drivers values ('004','0000004','2010-05-11 00:00:00.000')
insert into test_drivers values ('005','0000001','2009-04-05 00:00:00.000')
insert into test_drivers values ('002','0000001','2009-05-06 00:00:00.000')
insert into test_drivers values ('004','0000001','2009-07-05 00:00:00.000')
insert into test_drivers values ('001','0000006','2008-06-04 00:00:00.000')
insert into test_drivers values ('002','0000006','2007-06-04 00:00:00.000')
insert into test_drivers values ('004','0000006','2006-07-08 00:00:00.000')
insert into test_drivers values ('004','0000005','2006-07-08 00:00:00.000')
select * from test_drivers;


-- question 1-12

--q1
SELECT DISTINCT name, dept
FROM sales_rep JOIN car
ON sales_rep.name = car.seller
WHERE color = 'green'

--q2
SELECT DISTINCT cname
FROM sales_rep
JOIN car
ON sales_rep.name = car.seller JOIN customer
ON car.purchased_by = customer.license
WHERE dept = 'toy'

--q3
SELECT DISTINCT name
FROM sales_rep
JOIN car
ON sales_rep.name = car.seller JOIN test_drivers
ON car.vin = test_drivers.vin
WHERE test_drivers.date < '10/01/2012 00:00.000'


--q4
SELECT DISTINCT car.vin
FROM car
JOIN test_drivers
ON car.vin = test_drivers.vin
 
INTERSECT

SELECT DISTINCT car.vin
FROM car
JOIN customer
ON car.purchased_by = customer.license

--q5
SELECT DISTINCT test_drivers.vin
FROM test_drivers
JOIN car
ON test_drivers.vin = car.vin
JOIN customer
ON car.purchased_by = customer.license
WHERE test_drivers.license <> customer.license 

--q6
SELECT DISTINCT cname 
FROM customer N 
WHERE not exists (SELECT vin 
FROM car C 
WHERE vin not in (SELECT vin 
FROM Test_drivers T 
WHERE N.license = T.license));

--q7
SELECT DISTINCT name
FROM sales_rep
WHERE sales_rep.name not in (SELECT sales_rep.name
FROM sales_rep
JOIN car
ON sales_rep.name = car.seller JOIN test_drivers
ON car.vin = test_drivers.vin JOIN customer
ON test_drivers.license = customer.license 
WHERE customer.cname = 'Psmith')

--q8
SELECT DISTINCT cname
FROM customer
JOIN test_drivers
ON test_drivers.license = customer.license
WHERE cname not in (SELECT cname
FROM test_drivers
JOIN customer
ON test_drivers.license = customer.license JOIN car
ON test_drivers.license = car.purchased_by
WHERE color != 'red')
AND cname NOT IN (SELECT cname
FROM customer
JOIN test_drivers 
ON customer.license =test_drivers.license JOIN car
ON test_drivers.license = car.purchased_by
WHERE color = 'blue')

--q9
SELECT DISTINCT cname, phone
FROM customer
WHERE cname not in ( SELECT cname
FROM test_drivers
JOIN customer
ON test_drivers.license = customer.license)

--q10
SELECT DISTINCT cname
FROM car
JOIN customer
ON  car.purchased_by = customer.license
WHERE make = 'VW' and model = 'Bug'

--q11
SELECT AVG(NUMCARS) AS AVGCARS
FROM (SELECT car.seller, count(*) AS NUMCARS
FROM car 
GROUP BY car.seller)N

--q12
SELECT DISTINCT cname, address
FROM customer c
WHERE c.license in (select top 1 license
FROM test_Drivers
GROUP BY license
ORDER BY count(*) desc);