package unit;

import com.fictiontimes.fictiontimesbackend.repository.DBConnection;
import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class DBConnectionTest {

    @Test
    public void testGetConnection() throws IOException, SQLException, ClassNotFoundException {
        Assert.assertNotNull(DBConnection.getConnection());
    }
}
