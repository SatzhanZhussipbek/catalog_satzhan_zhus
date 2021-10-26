package satzhanzhus.jpa_lessons_satzhan;

import satzhanzhus.jpa_lessons_satzhan.entity.Feature;
import satzhanzhus.jpa_lessons_satzhan.entity.FeatureValue;
import satzhanzhus.jpa_lessons_satzhan.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;
// https://habr.com/ru/post/265061/ - шпаргалка по Java

public class Create {
    public static void main(String[] args) {
        // Создать интерфейс для товара, для того, чтобы он сохранился в базе данных
        // Комплектующие [1]
        // Аудиотехника [2]
        // Мебель [3]
        // Выберите категорию (номер): ___
        // Введите название: ___
        // Введите цену: ___
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        Scanner scanner = new Scanner(System.in); // класс Scanner для считывания с консоли
        // .nextLine - метод, который считывает одну полную строку с консоли

        Product product = new Product();
        Product product2 = new Product();

        // Создание запроса в JPQL
        //TypedQuery<Category> query = manager.createQuery("select c from Category c", Category.class);
        //List<Category> categories = query.getResultList(); // вытаскивает из запроса результат в виде списка
        TypedQuery<Product> query2 = manager.createQuery("select p from Product p", Product.class);
        List<Product> products = query2.getResultList();
        //TypedQuery<Feature> query3 = manager.createQuery("select f from Feature f", Feature.class);
        //List<Feature> features = query3.getResultList();
        /*for (Category category : categories) {
            System.out.println(category.getName() + "[" + category.getId() + "]");
        }*/
        try {
            manager.getTransaction().begin();
            /*System.out.println("Выберите категорию (номер): ");
            String categoryNumIn = scanner.nextLine();
            product.setCategory(manager.find(Category.class, Long.parseLong(categoryNumIn) ));

            System.out.println("Введите название: ");
            String productNameIn = scanner.nextLine();
            product.setName(productNameIn);

            System.out.println("Введите цену");
            String productPriceIn = scanner.nextLine();
            product.setPrice(Long.parseLong(productPriceIn));*/

            //System.out.println("Выберите характеристику");
            // Вывести характеристики выбранной категории товара
            // Диагональ: ___
            // Матрица: ___
            // Записать данные характеристик в features_values
            //manager.persist(product); // нужно писать до manager.getTransaction().commit, но после
            // занесения всех данных
            String featureVal1 = "";

            /*for (Feature feature : product.getCategory().getFeatures()) {
                System.out.println(feature.getFeatures() + ":");
                featureVal1 = scanner.nextLine();

                FeatureValue featureValue = new FeatureValue();
                featureValue.setFeatureValue(featureVal1);
                featureValue.setFeature(feature);
                featureValue.setProduct(product);
                manager.persist(featureValue);
            }*/
            // Еще одно задание: реализовать обновление товара, т.е.
            // Запрос id товара, потом ввести новое название и новую цену, затем необходимо записать запрошенные данные в базу.
            // Изменить значения характеристик товара
            for ( Product prod : products) {
                System.out.println(prod.getName() + "[" + prod.getId() + "]");
            }
            System.out.println("Выберите номер товара: ");
            String productIdIn = scanner.nextLine();
            product = manager.find(Product.class, Long.parseLong(productIdIn));
            // если ничего не вводится, то должно остаться предыдущее значения при изменении товара и его характеристик
            System.out.println("Введите новое название: ");
            String productNameIn = scanner.nextLine();
            if (!productNameIn.isEmpty()) {
            product.setName(productNameIn);
            }

            System.out.println("Введите новую цену: ");
            String productPriceIn = scanner.nextLine();
            if (!productPriceIn.isEmpty()) {
                product.setPrice(Long.parseLong(productPriceIn));
            }
            manager.persist(product);
            // Здесь начинается изменение значения характеристик товара
            for (Feature feature: product.getCategory().getFeatures() ) {
//                System.out.println(feature.getFeatures() + "[" + feature.getId() + "]");
                // ?1 -> ? - параметр, 1 - номер параметра. Эти параметры называются порядковыми.
                // :a -> : - параметр, "a" - название параметра. Эти параметры называются наименованными.
                TypedQuery<FeatureValue> query4 = manager
                        .createQuery("select fv from FeatureValue fv where fv.product.id = ?1 and fv.feature.id = ?2", FeatureValue.class);
                query4.setParameter(1, product.getId());
                //query4.setParameter("a", product.getId());
                query4.setParameter(2, feature.getId());
                List <FeatureValue> featureValues = query4.getResultList();
                for (FeatureValue fVal: featureValues) {
                    System.out.println(fVal.getFeature().getFeatures());
                    System.out.println("Введите новое значение: ");
                    String fValName = scanner.nextLine();
                    if (!fValName.isEmpty()) {
                        fVal.setFeatureValue(fValName);
                    }
                    manager.persist(fVal);
                }

                /*System.out.println("Введите новое название характеристики: ");
                String featureNameIn = scanner.nextLine();
                featureValue2.setFeature(feature);
                featureValue2.setFeatureValue(featureVal1);
                featureValue2.setProduct(product);
                featureValue2.setFeature(feature);
                manager.persist(featureValue2);*/
            }

            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
