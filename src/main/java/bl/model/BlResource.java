package bl.model;

import java.util.UUID;

/**
 * author Miara Omer @ 08/08/2022
 */

public class BlResource {

    //region Members
    private String id;
    //endregion

    //region Constructor
    public BlResource() {
        this.id = generateUUID();
    }
    //endregion

    //region Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    //endregion

    //region Override Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlResource that = (BlResource) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    //endregion

    //region Private Methods
    private String generateUUID() {
        String retVal;

        UUID uuid = UUID.randomUUID();
        retVal = uuid.toString();

        return retVal;
    }
    //endregion
}
