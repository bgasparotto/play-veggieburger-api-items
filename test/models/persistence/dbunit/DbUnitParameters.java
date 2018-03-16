package models.persistence.dbunit;

/**
 * {@code DbUnit} constants for database connection.
 * 
 * @author Bruno Gasparotto
 *
 */
public enum DbUnitParameters {

	/**
	 * Driver class.
	 */
	DRIVER_CLASS("org.h2.Driver"),

	/**
	 * Connection URL of the database.
	 */
	CONNECTION_URL("jdbc:h2:mem:play;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;INIT=runscript from 'sql/h2-ddl.sql'"),

	/**
	 * Username to be used to access the database.
	 * */
	USERNAME("sa"),

	/**
	 * Password used by the {@link DbUnitParameters#USERNAME USERNAME}.
	 */
	PASSWORD(""),

	/**
	 * Path of the {@code xml} file containing the data to be used on persitence
	 * tests by {@code DbUnit}.
	 */
	FLAX_XML_FILE("test/dbunit-test-db.xml");

	private final String value;

	/**
	 * Constructor.
	 * 
	 * @param value
	 *            The constant value
	 */
	private DbUnitParameters(String value) {
		this.value = value;
	}

	/**
	 * The the value of this enum element.
	 * 
	 * @return Value of this enum element
	 */
	public String getValue() {
		return value;
	}
}