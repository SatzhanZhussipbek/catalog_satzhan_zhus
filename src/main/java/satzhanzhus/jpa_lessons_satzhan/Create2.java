package satzhanzhus.jpa_lessons_satzhan;

import satzhanzhus.jpa_lessons_satzhan.entity.Category;
import satzhanzhus.jpa_lessons_satzhan.entity.Feature;
import satzhanzhus.jpa_lessons_satzhan.entity.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    public class Create2 {
        public static void main(String[] args) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
            EntityManager manager = factory.createEntityManager();

            Scanner scanner = new Scanner(System.in);

            Product product = new Product();
        /*long minPrice = 100000;
        long maxPrice = 200000;*/
            // between -> age >= 18 and age <= 45 - входит ли возраст в диапазон от 18 до 45.
            // age between 18 and 45 - входит ли возраст в диапазон от 18 до 45.
            // Между словами нужно проставлять пробелы, чтобы не было ошибок в запросе.
            //TypedQuery<Product> query = manager.createQuery("select p from Product p where p.price > " + minPrice, Product.class);
            //TypedQuery<Product> query = manager.createQuery("select p from Product p where p.price between " + minPrice + " and " + maxPrice, Product.class);
            //TypedQuery<Product> query = manager.createQuery("select p from Product p where p.price > 100000", Product.class);
       /* TypedQuery<Product> query = manager.createQuery("select p from Product p where p.price between ?1 and ?2", Product.class);
        query.setParameter(1, minPrice);
        query.setParameter(2, maxPrice);
        List<Product> products = query.getResultList();

        for (Product product2: products) {
            System.out.println(product2.getName());
        }*/
            // Query -> объект для выполнения запросов на изменение данных, т.е. запросов
            // по типу update, delete, insert, alter и т.д.
            TypedQuery<Category> query2 = manager.createQuery("Select c from Category c", Category.class);
            List<Category> categories = query2.getResultList();
            for (Category category: categories) {
                System.out.println(category.getName() + "[" + category.getId() + "]");
            }

            //try {
                //manager.getTransaction().begin();
                // Прибавить 15000 к стоимости всех товаров, которые стоят меньше 50000
                /*Query query = manager.createQuery("update Product p set p.price = p.price * 2 where p.price < ?1");
                query.setParameter(1, 120000L);
                query.executeUpdate();*/
                //query.setParameter(2, 116000L);
                // *.executeUpdate() - метод для выполнения запросов без результата.
                //Product product = manager.find(Product.class, 17L);
                //manager.remove(product);
                //query.executeUpdate();
                //manager.getTransaction().commit();
            //}
            //catch (Exception e) {
                //manager.getTransaction().rollback();
                //e.printStackTrace();
            //}
            //--- Создание категории ---
            // Введите название категории: ___
            // введите характеристики категории через запятую: ___

            // Пример:
            // --- Создание категории ---
            // Введите название категории: Смартфоны
            // Введите характеристики категории через запятую: Материал корпуса, Емкость аккумулятора, Количество ОЗУ

            // В базе должна создаться новая категория с 3 характеристиками:
            // Смартфоны
            // * Материал корпуса
            // * Емкость аккумулятора
            // * Количество ОЗУ
            // Предметы искусства -> * Вид предмета, *Ширина, *Высота
            Category category = new Category();
            List<Feature> features = new ArrayList<>();
            try {
                manager.getTransaction().begin();
                System.out.println("Введите название категории: ");
                String categoryNameIn = scanner.nextLine();
                TypedQuery<Long> query4 = manager.createQuery("Select count(c.id) from Category c where c.name = ?1 ", Long.class);
                query4.setParameter(1, categoryNameIn);
                List<Long> categories3 = query4.getResultList();
                // while (categories3.get(0) != 0)
                while (!categories3.contains(0L))
                {
                    System.out.println("Такая категория уже существует!");
                    System.out.println("Введите название категории: ");
                    categoryNameIn = scanner.nextLine();
                    query4.setParameter(1, categoryNameIn);
                    categories3 = query4.getResultList();
                }
                    category.setName(categoryNameIn);
                    System.out.println("Введите характеристики категории через запятую: ");
                    String categoryFeatures = scanner.nextLine();
                    String[] singleFeature = categoryFeatures.split(", ");
                    manager.persist(category);
                    for (String a : singleFeature) {
                        Feature feature = new Feature();
                        feature.setCategory(category);
                        feature.setFeatures(a);
                        features.add(feature);
                        category.setFeatures(features);
                        manager.persist(feature);
                    }

                    manager.getTransaction().commit();
            }
            catch (Exception e ) {
                manager.getTransaction().rollback();
                e.printStackTrace();
            }
            /*try {
                manager.getTransaction().begin();
                System.out.println("Введите название категории: ");
                String categoryNameIn = scanner.nextLine();
                // Сделать проверку на уникальность названия категории
                // Если такая категория есть, то вывести сообщение, что она уже существует.
                TypedQuery<Category> query3 = manager.createQuery("Select c from Category c where c.name = ?1 ", Category.class);
                // TypedQuery<Category> query3 = manager.createQuery( "Select count(c.id) from Category c where c.name = ?1 ", Category.class);
                // TypedQuery<Category> query3 = manager.createQuery("Select c from Category c where c.name = '" + categoryNameIn + "'";
                query3.setParameter(1, categoryNameIn);
                List<Category> categories2 = query3.getResultList();
                //System.out.println(categories2.isEmpty());
                while (!categories2.isEmpty()) {
                        System.out.println("Такая категория уже существует!");
                        System.out.println("Введите название категории: ");
                        categoryNameIn = scanner.nextLine();
                        query3.setParameter(1, categoryNameIn);
                        categories2 = query3.getResultList();
                }
                        category.setName(categoryNameIn);
                        System.out.println("Введите характеристики категории через запятую: ");
                        String categoryFeatures = scanner.nextLine();
                        String[] singleFeature = categoryFeatures.split(", ");
                        manager.persist(category);
                        for (String a : singleFeature) {
                            Feature feature = new Feature();
                            feature.setCategory(category);
                            feature.setFeatures(a);
                            features.add(feature);
                            category.setFeatures(features);
                            manager.persist(feature);
                        }

                        manager.getTransaction().commit();

            }
            catch (Exception e) {
                manager.getTransaction().rollback();
                e.printStackTrace();
            }*/



        }

    }


