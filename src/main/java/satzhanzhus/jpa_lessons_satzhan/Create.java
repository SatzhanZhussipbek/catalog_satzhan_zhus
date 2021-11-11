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
import java.util.Scanner;

public class Create {

    private static Scanner scanner = new Scanner(System.in);

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");

    public static void main(String[] args) {

        System.out.println("Создание категории[1]\nИзменение товара[2]\nУдаление категории[3]\nВыберите действие: ");
        Scanner scannerApp = new Scanner(System.in);
        String answerIn = scannerApp.nextLine();
        switch (answerIn) {
            case "1":
                create();
                break;
            case "2":
                change();
                break;
            case "3":
                delete();
                break;
        }

    }

    private static void create() {

        EntityManager manager = factory.createEntityManager();

        TypedQuery<Category> query2 = manager.createQuery("Select c from Category c", Category.class);
        List<Category> categories = query2.getResultList();
        for (Category category: categories) {
            System.out.println(category.getName() + "[" + category.getId() + "]");
        }
        //--- Создание категории ---
        // Введите название категории: ___
        // введите характеристики категории через запятую: ___

        Category category = new Category();
        List<Feature> features = new ArrayList<>();
        try {
            manager.getTransaction().begin();
            System.out.println("Введите название категории: ");
            String categoryNameIn = scanner.nextLine();
            TypedQuery<Long> selectedCatQuery = manager.createQuery("Select count(c.id) from Category c where c.name = ?1 ", Long.class);
            selectedCatQuery.setParameter(1, categoryNameIn);
            List<Long> categories3 = selectedCatQuery.getResultList();
            // while (categories3.get(0) != 0)
            while (!categories3.contains(0L))
            {
                System.out.println("Такая категория уже существует!");
                System.out.println("Введите название категории: ");
                categoryNameIn = scanner.nextLine();
                selectedCatQuery.setParameter(1, categoryNameIn);
                categories3 = selectedCatQuery.getResultList();
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
    }

    private static void change() {

        EntityManager manager = factory.createEntityManager();
        // Создание запроса в JPQL
        TypedQuery<Product> selectedProdQuery = manager.createQuery("select p from Product p", Product.class);
        List<Product> products = selectedProdQuery.getResultList();

        try {
            // Еще одно задание: реализовать обновление товара, т.е.
            // Запрос id товара, потом ввести новое название и новую цену, затем необходимо записать запрошенные данные в базу.
            // Изменить значения характеристик товара
            for (Product prod : products) {
                System.out.println(prod.getName() + "[" + prod.getId() + "]");
            }
            System.out.println("Выберите номер товара: ");
            String productIdIn = scanner.nextLine();
            Product product = manager.find(Product.class, Long.parseLong(productIdIn));
            // если ничего не вводится, то должно остаться предыдущее значение при изменении
            // товара и его характеристик
            System.out.println("Введите новое название: ");
            String productNameInNew = scanner.nextLine();
            if (!productNameInNew.isEmpty()) {
                product.setName(productNameInNew);
            }

            System.out.println("Введите новую цену: ");
            String productPriceInNew = scanner.nextLine();
            if (!productPriceInNew.isEmpty()) {
                product.setPrice(Long.parseLong(productPriceInNew));
            }
            manager.persist(product);
            // Здесь начинается изменение значения характеристик товара
            for (Feature feature: product.getCategory().getFeatures() ) {
                System.out.println(feature.getFeatures() + "[" + feature.getId() + "]");
                // ?1 -> ? - параметр, 1 - номер параметра. Эти параметры называются порядковыми.
                // :a -> : - параметр, "a" - название параметра. Эти параметры называются наименованными.
                TypedQuery<FeatureValue> selectedFValueQuery = manager
                        .createQuery("select fv from FeatureValue fv where fv.product.id = ?1 and fv.feature.id = ?2", FeatureValue.class);
                selectedFValueQuery.setParameter(1, product.getId());
                //query4.setParameter("a", product.getId());
                selectedFValueQuery.setParameter(2, feature.getId());
                List <FeatureValue> featureValues = selectedFValueQuery.getResultList();
                for (FeatureValue fVal: featureValues) {
                    System.out.println(fVal.getFeature().getFeatures());
                    System.out.println("Введите новое значение: ");
                    String fValName = scanner.nextLine();
                    if (!fValName.isEmpty()) {
                        fVal.setFeatureValue(fValName);
                    }
                    manager.persist(fVal);
                }
            }

            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    private static void delete() {

        EntityManager manager = factory.createEntityManager();

        TypedQuery<Category> categoryToDelQuery = manager.createQuery("Select c from Category c", Category.class);
        List<Category> categories = categoryToDelQuery.getResultList();
        for (Category category: categories) {
            System.out.println(category.getName() + "[" + category.getId() + "]");
        }
        System.out.println("Выберите категорию, которую хотите удалить: ");
        String categoryId = scanner.nextLine();
       // Удаление объекта локально от EntityManager, превращая category4 из сущности
        // в обычный объект. Удаление из базы происходит, если удаление стоит в рамках транзакции.
        Category categoryToDelete = manager.find(Category.class, Long.parseLong(categoryId));
        try {
            manager.getTransaction().begin();
            manager.remove(categoryToDelete);
            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
