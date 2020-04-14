CREATE USER 'root1'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root1';

insert into currency values ("001",1,"Colones",0.13);
insert into currency values ("002",573,"Dolares",0.06);
insert into currency values ("003",615,"Euros",0.05);
insert into user values("402410745","pinto99");
insert into user values("402420750","brazza");
insert into client values("CL100","Heiner","60216753","402410745");
insert into client values("CL001","Braslyn","60032274","402420750");
insert into account values(1,10000,"CL001","002",1000);
insert into account values(2,10000,"CL100","001",1000);
insert into cashier values("Braslyn","CS001","402420750");
insert into favorites values(0,2,"CL001");

select count(number) from transaction;

select * from account inner join client inner join user inner join currency on user.id="402420750" ;
select * from client c inner join user u on c.User_id = u.id where c.User_id="402420750";

select * from account a inner join client c inner join user u inner join currency on a.Client_client_cod = c.cod and c.User_id=u.id where c.cod ='CL001' and c.User_id="402420750";

select * from client where cod = "CL100";

insert into transaction values(1,"traspasoamount",1000,date("2020-04-22"),1,"001");
insert into transaction values(12,"traspasoamount",5100,date("2020-04-22"),3,"001");

select * from account a inner join client c inner join user u on u.id=c.User_id on a.Client_client_cod = c.cod inner join Currency d on a.Currency_currencyCode = d.currencyCode where a.number = 1 and a.Client_client_cod="CL001";

select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where t.number= 1 and a.Client_client_cod= 'CL001';

select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where t.Account_number= '1' and a.Client_client_cod= 'CL100' ;

select * from transaction where Account_number=1;

select count(cod) from client;

delete from transaction where number=1;

select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where a.Client_client_cod= 'CL001' and  t.date>=2020-02-01 and t.amount>0;

select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where a.Client_client_cod='CL001' and t.amount>'0';

select *  from favorites f inner join account a inner join currency c inner join client cl inner join user u on a.Client_client_id= cl.cod and u.id = cl.User_id and f.favorite_account=a.number and a.Currency_currencyCode = c.currencyCode where f.Client_cod ="CL001";

select * from account a inner join client c inner join user u inner join currency cu on a.Client_client_cod = c.cod and  c.User_id = u.id and a.Currency_currencyCode=cu.currencyCode where c.cod='CL001';

delete  from account where account.number=6;

select *  from favorites f inner join account a inner join currency c inner join client cl inner join user u on a.Client_client_cod= cl.cod and u.id = cl.User_id and f.favorite_account=a.number and a.Currency_currencyCode= c.currencyCode where f.Client_cod ='CL001';

insert into Transaction values(1,'Transferencia-Envio',12.0,date('11/04/2020 03:58:12 PM'),2,'001');

Update account set balance= 1500 , Client_client_cod= 'CL001', Currency_currencyCode= '001' where number= 1;


select * from transaction t inner join account a inner join currency cu inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and t.currencyCode= cu.currencyCode and c.User_id=u.id where t.Account_number= 4 and a.Client_client_cod= "CL001";

select * from account a inner join client c inner join user u inner join currency cu on a.Client_client_cod = c.cod and c.User_id = u.id and a.Currency_currencyCode=cu.currencyCode;

select sum(amount) from transaction t where t.date="2020-04-13" and t.Account_number=2 and t.type="Transferencia-Envio";