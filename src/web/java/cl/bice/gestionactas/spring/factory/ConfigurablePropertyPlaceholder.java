package cl.bice.gestionactas.spring.factory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class ConfigurablePropertyPlaceholder extends PropertyPlaceholderConfigurer {


    private String propertyLocationSystemProperty;
    private String defaultPropertyFileName;


    public String getPropertyLocationSystemProperty() {
        return propertyLocationSystemProperty;
    }

    public void setPropertyLocationSystemProperty(String propertyLocationSystemProperty) {
        this.propertyLocationSystemProperty = propertyLocationSystemProperty;
    }

    public String getDefaultPropertyFileName() {
        return defaultPropertyFileName;
    }

    public void setDefaultPropertyFileName(String defaultPropertyFileName) {
        this.defaultPropertyFileName = defaultPropertyFileName;
    }

    /**
     * Overridden to fill the location with the path from the {@link #propertyLocationSystemProperty}
     *
     * @param props propeties instance to fill
     * @throws IOException
     */

    @Override
    protected void loadProperties(Properties props) throws IOException {
        Resource location = null;
        System.out.println(propertyLocationSystemProperty);
        System.out.println(defaultPropertyFileName);
        if(StringUtils.isNotEmpty(propertyLocationSystemProperty)){

        	String propertyFilePath = System.getenv(propertyLocationSystemProperty);
            //String propertyFilePath = System.getProperties().getProperty(propertyLocationSystemProperty);
            StringBuilder pathBuilder = new StringBuilder(propertyFilePath+"/properties/gestionActas");
            System.out.println(propertyFilePath);
            System.out.println(pathBuilder);

            if(StringUtils.isNotEmpty(defaultPropertyFileName) && !propertyFilePath.endsWith(defaultPropertyFileName)){
                pathBuilder.append("/").append(defaultPropertyFileName);
                System.out.println(pathBuilder);
            }

            location = new FileSystemResource(pathBuilder.toString());
        }

        setLocation(location);
        super.loadProperties(props);
    }
}