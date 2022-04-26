create table catalog_categories
(
    id   bigint unsigned auto_increment,
    name varchar(50) not null,
    primary key (id)
);

create table catalog_products
(
    id          bigint unsigned auto_increment,
    category_id bigint unsigned not null,
    name        varchar(50)     not null,
    price       bigint unsigned not null,
    primary key (id),
    foreign key (category_id) references catalog_categories (id)
);

create table catalog_features
(
    id          bigint unsigned auto_increment,
    category_id bigint unsigned not null,
    features    varchar(150)    not null,
    primary key (id),
    foreign key (category_id) references catalog_categories (id)
);

create table features_values
(
    id            bigint unsigned auto_increment,
    product_id    bigint unsigned not null,
    feature_id    bigint unsigned not null,
    feature_value varchar(50)     not null,
    primary key (id),
    foreign key (product_id) references catalog_products (id),
    foreign key (feature_id) references catalog_features (id)
);