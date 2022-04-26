truncate table features_values;
truncate table catalog_features;
truncate table catalog_products;
truncate table catalog_categories;

insert into catalog_categories (name)
values ('Монитор'),
       ('Смартфон');

insert into catalog_products (category_id, name, price)
values (1, 'Samsung M700+', 120000),
       (2, 'IPhone 12', 450000),
       (3, 'LG L900U', 100000),
       (4, 'Samsung S10', 250000);

insert into catalog_features (category_id, features)
values (1, 'Диагональ'),
       (1, 'Матрица'),
       (2, 'Объём ОЗУ'),
       (2, '2 SIM');

insert into features_values (product_id, feature_id, feature_value)
values (1, 1, '21.5'),
       (1, 2, 'TN'),
       (2, 3, '6'),
       (2, 4, 'Нет'),
       (3, 1, '23'),
       (3, 2, 'IPS'),
       (4, 3, '4'),
       (4, 4, 'Да');
