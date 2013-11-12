drop table usertable
drop table TaggedPhoto
drop table Tag
drop table archive

create table usertable (
userid varchar(10),
uname varchar(10),
primary key(userid))

insert into usertable values('1','kent')
insert into usertable values('2','kelly')
insert into usertable values('3','john')
insert into usertable values('4','teddy')
insert into usertable values('5','zeo')
select * from usertable;

create table TaggedPhoto (
tpid varchar(10),
author varchar(10),
from_user varchar(10),
data int,
photo varchar(20),
userid varchar(10),
primary key(tpid),
foreign key(userid) references usertable)

insert into TaggedPhoto values ('001','kt','ak',2012,'flower.jpg', '1')
insert into TaggedPhoto values ('002','ac','ab',2011,'tiger.jpg','1')
insert into TaggedPhoto values ('003','ad','yk',2010,'tank.jpg','2')
insert into TaggedPhoto values ('004','dz','zz',2009,'building.jpg','2')
insert into TaggedPhoto values ('005','qj','ob',2008,'computer.jpg','5')
select * from TaggedPhoto;

create table Tag(
tno varchar(10),
value varchar(10),
tpid varchar(10),
primary key(tno))

insert into Tag values('01','time','002')
insert into Tag values('02','CPScordinate', '003')
insert into Tag values('03','data','004')
insert into Tag values('04','time','003')
insert into Tag values('05','subject','005')
select * from Tag;

create table archive(
ano varchar(10),
servername varchar(10),
location varchar(20),
tpid varchar(10),
primary key(ano))

insert archive values ('10','db1','www.ace.com123','001')
insert archive values ('11','db2','www.cbe.com234','001')
insert archive values ('12','db3','www.bed.com345','002')
insert archive values ('13','db4','www.ghj.com456','004')
insert archive values ('14','db5','www.ace.com123','001')
select * from archive