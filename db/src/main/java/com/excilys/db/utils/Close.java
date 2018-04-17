package com.excilys.db.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Close {

    /**
     *
     */
    private Close() {

    }

    /**
     *
     * @param resultSet le résult set à fermer
     */
    public static void closeQuietly(ResultSet resultSet) {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
      } catch (SQLException e) {
      }
    }
}
