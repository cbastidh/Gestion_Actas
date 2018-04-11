from java.io import FileInputStream
import java.lang
import os
import string

propInputStream = FileInputStream('domain.properties')
configProps = Properties()
configProps.load(propInputStream)

wlpath = configProps.get('weblogic.path')
dmpass = configProps.get('domain.password')
dmpath = configProps.get('domain.path')
dmport = configProps.get('domain.port')

print '=========================================='
print '===   Se ha iniciado el proceso        ==='
print '=========================================='
readTemplate(wlpath+'/wlserver/common/templates/wls/wls.jar')
cd('Servers/AdminServer')

print '=========================================='
print '=== Configurando el puerto             ==='
print '=========================================='
set('ListenPort',long(dmport))

cd('/')
cd('Security/base_domain/User/weblogic')
print '=========================================='
print '=== Configurando el password           ==='
print '=========================================='
cmo.setPassword(dmpass)

cd('/')
setOption('OverwriteDomain','true')
setOption('ServerStartMode','dev')



cd('/')
create('gaXADS', 'JDBCSystemResource')
cd('JDBCSystemResource/gaXADS/JdbcResource/gaXADS')
create('myJdbcDriverParams','JDBCDriverParams')
cd('JDBCDriverParams/NO_NAME_0')
set('DriverName','oracle.jdbc.OracleDriver')
set('URL','jdbc:oracle:thin:@10.1.1.231:1521:flexgold')
set('PasswordEncrypted', 'bice')
set('UseXADataSourceInterface', 'false')
create('myProps','Properties')
cd('Properties/NO_NAME_0')
create('user', 'Property')
cd('Property/user')
cmo.setValue('bice')
cd('/JDBCSystemResource/gaXADS/JdbcResource/gaXADS')
create('myJdbcDataSourceParams','JDBCDataSourceParams')
cd('JDBCDataSourceParams/NO_NAME_0')
set('JNDIName', java.lang.String("jdbc/gaXADS"))

cd('/JDBCSystemResource/gaXADS/JdbcResource/gaXADS')
create('myJdbcConnectionPoolParams','JDBCConnectionPoolParams')
cd('JDBCConnectionPoolParams/NO_NAME_0')
set('TestTableName','SQL SELECT 1 FROM DUAL')
assign('JDBCSystemResource', 'gaXADS', 'Target', 'AdminServer')




writeDomain(dmpath)

print '=========================================='
print '=== Se ha creado el dominio            ==='
print '=========================================='
closeTemplate()
dumpStack()