package com.pos.main.utils.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.utils.entity.AllCodeDef;

@Repository
public interface AllCodeDefRepository extends JpaRepository<AllCodeDef, Integer> {

	Optional<AllCodeDef> findByPrefixAndPojoClass(String prefix, String pojoClass);

    Optional<AllCodeDef> findByPrefixAndPojoClassAndPlazaId(
            String prefix, String pojoClass, Integer plazaId);
}
