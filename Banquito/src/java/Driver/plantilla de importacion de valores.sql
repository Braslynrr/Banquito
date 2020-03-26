insert into currency values ("001",1,"Colones");
insert into user values("111111111","xd");
insert into client values("CL002","Paco","8888888","111111111");
insert into account values(2,2000,"CL002","001");
insert into user values("402420750","brazza");
insert into client values("CL001","Braslyn","60032274","402420750");
insert into account values(1,1000,"CL001","001");
insert into cashier values("Braslyn","CS001","402420750");

select * from account inner join client inner join user on account.Client_client_cod like "CL002"