insert into currency values ("001",1,"Colones");
insert into user values("402410745","pinto99");
insert into client values("CL100","Heiner","60216753","402410745");
insert into account values(2,10000,"CL100","001");
insert into user values("402420750","brazza");
insert into client values("CL001","Braslyn","60032274","402420750");
insert into account values(1,1000,"CL001","001");
insert into cashier values("Braslyn","CS001","402420750");

select * from account inner join client inner join user inner join currency on user.id="402420750" ;
select * from client inner join user on client.user_id = user.id where client.User_id="402420750";

select * from account a inner join client c inner join user u inner join currency on a.Client_client_cod = c.cod and c.User_id=u.id where c.cod ='CL001' and c.User_id="402420750";

select * from client where cod = "CL100";