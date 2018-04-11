package cl.bice.gestionactas.ejb.dao.jpa;

/**
 * Date: 2/5/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class BicePhysicalNamingStrategy{

//        extends PhysicalNamingStrategyStandardImpl {
//    public static final String PREFIX = "ga_";
//
//    @Override
//    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
//        return Identifier.toIdentifier(PREFIX + name.getText());
//    }
//
//    @Override
//    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//        return Identifier.toIdentifier(classToTableName(name.getText()));
//    }
//
//    @Override
//    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
//        return Identifier.toIdentifier(PREFIX + name.getText());
//    }
//
//    public String classToTableName(String className) {
//        if (className.endsWith("BO")) {
//            className = className.substring(0, className.length() - 2);
//        }
//        return PREFIX + StringHelper.unqualify(className);
//    }
//
//    private Identifier convert(Identifier identifier) {
//        if (identifier == null || StringUtils.isEmpty(identifier.getText())) {
//            return identifier;
//        }
//
//        String regex = "([a-z])([A-Z])";
//        String replacement = "$1_$2";
//        String newName = identifier.getText().replaceAll(regex, replacement).toLowerCase();
//        return Identifier.toIdentifier(classToTableName(newName));
//    }
}
