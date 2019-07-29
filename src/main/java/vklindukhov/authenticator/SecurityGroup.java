package vklindukhov.authenticator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "security_group")
@Entity
class SecurityGroup implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @Column(name = "company_id")
    private String companyId;

    @ManyToMany
    @JoinTable(name = "group_user", joinColumns = {@JoinColumn(name = "group_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<UserDetailsEntity> users;

    private boolean removed = false;
}
