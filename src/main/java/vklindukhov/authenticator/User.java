package vklindukhov.authenticator;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
class User implements Serializable {
    @Id
    @NotNull
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @Column(name = "company_id")
    private String companyId;

    @NotNull
    private String password;

    @NotNull
    private boolean enabled;

    private boolean visible;
}