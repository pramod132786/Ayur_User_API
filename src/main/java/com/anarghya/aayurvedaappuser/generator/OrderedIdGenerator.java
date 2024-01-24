package com.anarghya.aayurvedaappuser.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderedIdGenerator implements IdentifierGenerator {
	
	private static final String PREFIX="OD_";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        Long sequenceNumber = getNextSequenceNumber(session.connection());

        // Generate the custom identifier
        return PREFIX + String.format("%01d", sequenceNumber);
    }

    private Long getNextSequenceNumber(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orderid_sequence_table VALUES (null)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();

            // Retrieve the last generated ID using LAST_INSERT_ID()
            try (ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()")) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }

        // Return a default value if unable to retrieve the sequence number
        return -1L;
    }
}
