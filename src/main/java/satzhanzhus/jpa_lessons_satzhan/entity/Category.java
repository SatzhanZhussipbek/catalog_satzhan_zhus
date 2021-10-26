package satzhanzhus.jpa_lessons_satzhan.entity;

import javax.persistence.*;
import java.util.List;

@Entity // класс яв-ся сущностью, будет взаимодействовать с базой данных
@Table(name = "catalog_categories")
public class Category {
    @Id // указание поля первичного ключа (primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // аннотация, которая указывает тип генерации для значения поля
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Feature> features;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
