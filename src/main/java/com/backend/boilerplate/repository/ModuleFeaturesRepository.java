package com.backend.boilerplate.repository;

import com.backend.boilerplate.entity.ModuleFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Repository
public interface ModuleFeaturesRepository extends JpaRepository<ModuleFeatures, Long> {

    Optional<Integer> countByNameIn(List<String> features);

    Optional<ModuleFeatures> findByName(String name);
}
