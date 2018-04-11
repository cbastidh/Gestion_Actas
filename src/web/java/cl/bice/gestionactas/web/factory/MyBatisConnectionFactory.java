package cl.bice.gestionactas.web.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory;
        static {
            try {
                Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
                if (sqlSessionFactory == null) {
                    sqlSessionFactory =
                            new SqlSessionFactoryBuilder().build(reader);
                }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}