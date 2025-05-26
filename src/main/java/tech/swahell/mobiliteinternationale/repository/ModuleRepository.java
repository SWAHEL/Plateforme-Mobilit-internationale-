package tech.swahell.mobiliteinternationale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.swahell.mobiliteinternationale.entity.Module;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    /**
     * 🔍 Get all modules for a given semester ID
     */
    List<Module> findBySemesterId(Long semesterId);

    /**
     * 🔍 Get all modules marked as PFE for a given semester
     */
    List<Module> findBySemesterIdAndIsPfeTrue(Long semesterId);

    /**
     * 🔍 Get all modules that are not PFE (regular subjects)
     */
    List<Module> findBySemesterIdAndIsPfeFalse(Long semesterId);

    /**
     * 🔍 Search modules by name keyword
     */
    List<Module> findByNameContainingIgnoreCase(String keyword);
}
