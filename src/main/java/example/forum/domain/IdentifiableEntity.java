package example.forum.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Used as a "base" entity for all entities with "id" property.
 * 
 * @author nevenc
 *
 */
@MappedSuperclass
public class IdentifiableEntity implements Serializable {

    private static final long serialVersionUID = 4873857249660783170L;

    private Long id;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public boolean isIdSet() {
        return id != null;
    }

    @Override
    public int hashCode() {
       return this.isIdSet() ? 31 * this.id.hashCode() : super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if ( this.isIdSet() && object instanceof IdentifiableEntity ) {
            IdentifiableEntity other = (IdentifiableEntity) object;
            return this.getId().equals(other.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [id=" + this.getId() + "]";
    }

}
