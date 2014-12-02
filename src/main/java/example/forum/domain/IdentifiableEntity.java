package example.forum.domain;

import java.io.Serializable;

/**
 * Used as a "base" entity for all entities with "id" property.
 * 
 * @author nevenc
 *
 */
public class IdentifiableEntity implements Serializable {

    private static final long serialVersionUID = 4873857249660783170L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
