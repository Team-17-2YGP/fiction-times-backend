package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.model.Types.SubscriptionStatus;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReaderRepository {

    private PreparedStatement statement;

    public void verifyReaderSubscription(int userId) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement("UPDATE reader SET subscriptionStatus = ? WHERE userId = ?");
        statement.setString(1, SubscriptionStatus.VERIFIED.toString());
        statement.setInt(2, userId);
        statement.execute();
    }
}
