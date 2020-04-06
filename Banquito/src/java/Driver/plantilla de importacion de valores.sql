insert into currency values ("001",1,"Colones");
insert into user values("402410745","pinto99");
insert into client values("CL100","Heiner","60216753","402410745");
insert into account values(2,10000,"CL100","001");
insert into user values("402420750","brazza");
insert into client values("CL001","Braslyn","60032274","402420750");
insert into account values(3,10000,"CL001","001");
insert into cashier values("Braslyn","CS001","402420750");

select * from account inner join client inner join user inner join currency on user.id="402420750" ;
select * from client c inner join user u on c.User_id = u.id where c.User_id="402420750";

select * from account a inner join client c inner join user u inner join currency on a.Client_client_cod = c.cod and c.User_id=u.id where c.cod ='CL001' and c.User_id="402420750";

select * from client where cod = "CL100";

insert into transaction values(1,"traspasoamount",1000,date("2020-04-22"),1,"001");
insert into transaction values(10,"traspasoamount",500,date("2020-04-22"),1,"001");

select * from account a inner join client c inner join user u on u.id=c.User_id on a.Client_client_cod = c.cod inner join Currency d on a.Currency_currencyCode = d.currencyCode where a.number = 1 and a.Client_client_cod="CL001";

select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where t.number= 1 and a.Client_client_cod= 'CL001';

select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where t.Account_number= '1' and a.Client_client_cod= 'CL100' ;

select * from transaction where Account_number=1;

select count(cod) from client;

select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where a.Client_client_cod= 'CL001' and  t.date>=2020-02-01 and t.amount>0;

