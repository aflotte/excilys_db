package com.excilys.db.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Close {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Close.class);
    
    private Close() {
        
    }
    
    public static void closeQuietly(ResultSet resultSet)
    {
      try
      {
        if (resultSet!= null)
        {
          resultSet.close();
        }
      }
      catch (SQLException e)
      {
        logger.error("An error occurred closing result set.", e);
      }
    }
}
