package by.urbel.finaltask.repository;

import by.urbel.finaltask.domain.FieldType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldTypeRepository extends JpaRepository<FieldType,Long> {
}
