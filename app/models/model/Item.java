package models.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class that represents and Item resource of the system.
 *
 * @author Bruno Gasparotto
 */
public class Item {
    private Long id;
    private String name;
    private BigDecimal price;

    /**
     * Constructor.
     */
    public Item() {
        this(null, "", BigDecimal.ZERO);
    }

    public Item(String name, BigDecimal price) {
        this(null, name, price);
    }

    /**
     * Constructor.
     *
     * @param id    The Item's {@code id} to set
     * @param name  The Item's {@code name} to set
     * @param price The Item's {@code price} to set
     */
    public Item(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Gets the Item's {@code id}.
     *
     * @return The Item's {@code id}
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the Item's {@code id}.
     *
     * @param id The Item's {@code id} to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the Item's {@code name}.
     *
     * @return The Item's {@code name}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Item's {@code name}.
     *
     * @param name The Item's {@code name} to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Item's {@code price}.
     *
     * @return The Item's {@code price}
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the Item's {@code price}.
     *
     * @param price The Item's {@code price} to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}