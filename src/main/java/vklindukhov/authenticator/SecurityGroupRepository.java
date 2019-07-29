package vklindukhov.authenticator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SecurityGroupRepository extends JpaRepository<SecurityGroup, Long> {
//    @Query(nativeQuery = true,
//            value = "select sg.* from security_group sg " +
//                    "join group_user gu on sg.id = gu.group_id " +
//                    "where sg.company_id = :companyId and gu.user_id = :userId")

    @Query(value = "select distinct sg from SecurityGroup sg inner join sg.users u where sg.companyId = :companyId and u = :user")
    Set<SecurityGroup> listUserGroups(@Param("companyId") String companyId, @Param("user") User user);
}
