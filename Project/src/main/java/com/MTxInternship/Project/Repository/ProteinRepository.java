package com.MTxInternship.Project.Repository;

import com.MTxInternship.Project.Model.Product;
import com.MTxInternship.Project.Model.Protein;
import com.MTxInternship.Project.Model.Protein;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProteinRepository extends JpaRepository<Protein, Integer> {
    void deleteByProteinId(UUID id);

    List<Protein> findAllByProduct(Product p);

    Protein findByProteinId(UUID id);
}
