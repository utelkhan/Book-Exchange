
insert into ROLE_(role) values('USER');
insert into ROLE_(role) values('ADMIN');


insert into PERMISSION_(permission) values('userPermissions');
insert into PERMISSION_(permission) values('adminPermissions');

insert into ROLE_PERMISSIONS_ (role_id, permission_id) values (1, 1);
insert into ROLE_PERMISSIONS_ (role_id, permission_id) values (2, 1);
insert into ROLE_PERMISSIONS_ (role_id, permission_id) values (2, 2);


insert into STATUS_(status) values ('ACTIVE');
insert into STATUS_(status) values ('BLOCKED');

insert into USER_(firstName, lastName, username, password, email,roleId, statusId) values ('Azamat', 'Utelkhan','admin', '$2y$10$kVHEEITaHEhJKQbpVpiwlePxpI9i4hGcOytTtT1pyyI/2CQwTcR9G','someEmail@mail.ru',1,1);
insert into USER_(firstName, lastName, username, password, email,roleId, statusId) values ('Azamat', 'Utelkhan','user', '$2y$10$3rdSQucb.U9F7eaeRI9hnO.NvOIc5jQGYwtGDV9EEMH9IsUkUjena','someEmail@mail.ru',1,1);


insert into BOOK_(isThisBookAvailableForExchange, owner_id, rating, title) values(true, 1, 8.8, 'Abay zholy');
insert into BOOK_(isThisBookAvailableForExchange, owner_id, rating, title) values(true, 2, 8.8, 'Kanmen zhazylgan ritap');

insert into AUTHOR_(firstName, lastName) values('Mukhtar', 'Auezov');
insert into AUTHOR_(firstName, lastName) values('Bauyrzhan', 'Momyshuly');

insert into BOOK_AUTHORS_(book_id, author_id) values(1, 1);
insert into BOOK_AUTHORS_(book_id, author_id) values(2, 2);

insert into GENRE_(genre) values('biography');
insert into GENRE_(genre) values('Military');
insert into BOOK_GENRES_(book_id, genre_id) values(1, 1);
insert into BOOK_GENRES_(book_id, genre_id) values(2, 2);
