package satzhanzhus.jpa_lessons_satzhan;

import satzhanzhus.jpa_lessons_satzhan.entity.Category;
import satzhanzhus.jpa_lessons_satzhan.entity.Feature;
import satzhanzhus.jpa_lessons_satzhan.entity.FeatureValue;
import satzhanzhus.jpa_lessons_satzhan.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // Maven - система сборки для Java проектов позволяющая автоматизировать
        // процесс подключения внешних библиотек и процесс компоновки в
        // результирующий jar (java приложение), war (веб java приложение) или ear
        // (несколько скомпонованных приложений в одно общее) архив.

        // JPA - набор спецификаций (стандарт), который описывает как Java должна
        // взаимодействовать с реляционными (табличными как MySQL) базами данных по принципу ORM.
        // Так как JPA всего лишь стандарт, он не предоставляет ни одного конкретного
        // класса, а только описывает как они (классы) должны быть реализованы.

        // ORM (Object Relational Mapping - Объектно-реляционный маппинг) - подход
        // который подразумевает преобразование данных из таблиц к объектам языка программирования.

        // table users
        // * id bigint unsigned ...,
        // * first_name varchar(50) ...,
        // * last_name varchar(50) ...,
        // * salary decimal ...,
        // * birth_date date ...

        // class User
        // * Long id
        // * String firstName
        // * String lastName
        // * Double salary
        // * LocalDate birthDate

        // Существует множество реализаций стандарта JPA, наиболее популярные:
        // * Hibernate - применяется повсеместно, обрела большую популярность за счет
        // почти 100 % поддержки стандарта JPA.
        // * EclipseLink - ещё одна реализация.
        // * ...

        // hibernate-core - название библиотеки которую необходимо подключить для того,
        // чтобы пользоваться возможностями Hibernate и JPA в частности.

        // mvnrepository.org - центральное хранилище Java библиотек

        // Сущность - класс, который описывает определенный элемент из базы данных.

        // persistence.xml - конфигурационный файл, в котором хранятся параметры
        // подключения к базе данных.

        // Java Application -> Java MySQL Driver -> MySQL Database
        // Python Application -> Python MySQL Driver -> MySQL Database
        // C++ Application -> C++ MySQL Driver -> MySQL Database

        // EntityManagerFactory - создает подключение к базе данных и держит его в
        // открытом состоянии
        // Persistence - класс, через который создается объект EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        // EntityManager - отвечает за взаимодействие с сущностями (выборка, запись, редактирование).
        EntityManager manager = factory.createEntityManager();
        // find ->
        // Class Category, 1L -> Long 1 -> значение для поля в сущности Category,
        // которое помечено при помощи аннотации ID.
        Category category = manager.find(Category.class, 2L);
        if (category != null) {
            System.out.println(category.getName());
            List<Product> products = category.getProducts();
            long sum = 0;
            //long count = 0;
            long average = 0;
            for (Product product : products) {
                //count++;
                sum += product.getPrice();
            }
            average = sum / products.size();
            System.out.println(average);
        }
        else {
            System.out.println("Такой категории не существует!");
        }
        //Product product = manager.find(Product.class, 1L);
        // Нужно найти среднюю стоимость товара по категории


        /*Feature feature = manager.find(Feature.class, 1L);
        List<FeatureValue> featureValues2 = feature.getFeatureValues();
        for (FeatureValue featureValue: featureValues2) {
            System.out.println(featureValue.getFeatureValue());
        }*/

        // В данный момент category2 является обычным Java объектом никак не привязанным к базе данных.
        /*Category category2 = new Category();
        category2.setName("Мебель");

        Feature feature1 = new Feature();
        feature1.setCategory(category2);
        feature1.setFeatures("Материал");

        Feature feature2 = new Feature();
        feature2.setCategory(category2);
        feature2.setFeatures("Ширина");

        Feature feature3 = new Feature();
        feature3.setCategory(category2);
        feature3.setFeatures("Высота");

        List<Feature> features = new ArrayList<>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        category.setFeatures(features);*/

        // try {} catch( ) { } -> метод, который помогает найти и словить ошибку
        // Затем с помощью .getTransaction().rollback() помогает откатить
       /* try {
            // *.getTransaction().begin() - начинает новую транзакцию. Транзакция определяет
            // список изменений, которые должны быть зафиксированы в базе данных.
            manager.getTransaction().begin();
            // * .persist(Object o) - привязывает объект локально к EntityManager-у, тем самым делая его сущностью.
            manager.persist(category2);
            manager.persist(feature1);
            manager.persist(feature2);
            manager.persist(feature3);
            // *.getTransaction().commit() - отправляет все изменения зафиксированные
            // транзакцией в базу данных
            manager.getTransaction().commit();
        } catch (Exception e) {
            // *.getTransaction().rollback() - отменяет все изменения, связанные с транзакцией.
            manager.getTransaction().rollback();
            e.printStackTrace();
        }*/

        // Взаимодействие с базой данных через EntityManager:
        // 1) Начало транзакции - этап в котором создается пустой набор задач.
        // 2) Локальные изменения - операции создания новых сущностей, изменения
        // либо удаления существующих и т.д. Все локальные изменения фиксируются в транзакции.
        // 3) Подтверждение транзакции - процесс фиксации локальных изменений в соответствии
        // с состоянием базы данных на момент выполнения.

        // Изменение объекта локально от EntityManager, если не в рамках транзакции.
        // Category.class -> информация о типе класса, откуда нужно делать поиск Category
        /*Category category3 = manager.find(Category.class, 5L);
        try {
            manager.getTransaction().begin();
            category3.setName("New name");
            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }*/
        // Удаление объекта локально от EntityManager, превращая category4 из сущности
        // в обычный объект. Удаление из базы происходит, если удаление стоит в рамках транзакции.
       /* Category category4= manager.find(Category.class, 6L);
        try {
            manager.getTransaction().begin();
            manager.remove(category4);
            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }*/
        // TypedQuery<X> - объект, применяемый для написания и выполнения запросов,
        // которые подразумевают наличие результата, где X - тип данных результата.
        // Запросы в JPA формируются при помощи специального языка, JPQL.
        // По большей части JPQL - это тот же самый SQL, но вместо таблиц он
        // взаимодействует с сущностями JPA.

        // SQL -> select * from categories where name like 'A%' order by name
        // JPQL -> select c from Category c where c.name like 'A%' order by c.name
        TypedQuery<Category> query = manager.createQuery("select c from Category c", Category.class);
        //TypedQuery<String> query = manager.createQuery("select c.name from Category c", String.class);
        List<Category> categories = query.getResultList();
        for (Category category1 : categories) {
            System.out.println(category1.getName());
        }
    }

    /*private static <T> void method(Class<T> tClass) {
        System.out.println(tClass);
    }*/
}
