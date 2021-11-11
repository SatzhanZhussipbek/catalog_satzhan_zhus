package satzhanzhus.jpa_lessons_satzhan.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "catalog_features")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "feature")
    private List<FeatureValue> featureValues;

    private String features;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<FeatureValue> getFeatureValues() {
        return featureValues;
    }

    public void setFeatureValues(List<FeatureValue> featureValues) {
        this.featureValues = featureValues;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
