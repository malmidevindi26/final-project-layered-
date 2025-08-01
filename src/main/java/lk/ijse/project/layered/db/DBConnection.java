package lk.ijse.project.layered.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {




        private static DBConnection dbConnection;
        private final Connection connection;

        private DBConnection() throws ClassNotFoundException, SQLException {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/laundryfx", "root", "pmysql");
        }
        public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
//        if(dbConnection == null){
//             dbConnection = new DBConnection();
//        }
//        return dbConnection;
            return dbConnection = dbConnection == null ? new DBConnection() : dbConnection;
        }
        public Connection getConnection(){

            return this.connection;
        }

    }


