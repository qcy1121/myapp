package test.myapp.repository;

import test.myapp.domain.WxUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WxUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WxUserRepository extends JpaRepository<WxUser, Long> {

}
