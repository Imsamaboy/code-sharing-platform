package platform.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entity.Code;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends CrudRepository<Code, Integer> {
    Optional<Code> getCodeById(Integer id);

    @Query(value = "select * from CODE order by CODE.DATE desc limit 10", nativeQuery = true)
    List<Code> getLatestCodes();
}
