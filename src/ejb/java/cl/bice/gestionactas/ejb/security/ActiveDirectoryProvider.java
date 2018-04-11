package cl.bice.gestionactas.ejb.security;

import cl.bice.gestionactas.ejb.dao.UserDAO;
import cl.bice.gestionactas.ejb.entity.UserBO;
import cl.bice.gestionactas.ejb.security.SecurityProvider;
import cl.bice.gestionactas.ejb.vo.UserVO;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.security.authentication.BadCredentialsException;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.IOException;
import java.security.Security;
import java.util.*;
/**
 * Date: 2/24/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 * Descripcion: Clase que se encarga de verificar el nombre de usuario y contrasena contra el activedirectory.
 */
public class ActiveDirectoryProvider extends SecurityProvider {
    private static final Logger logger = Logger.getLogger(ActiveDirectoryProvider.class);

    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static TrustManager[] TRUST_ALL_CERTS = new TrustManager[]{
    new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public void checkClientTrusted(
            java.security.cert.X509Certificate[] certs, String authType) {
        }
        public void checkServerTrusted(
            java.security.cert.X509Certificate[] certs, String authType) {
        }
    }
    };

    public ActiveDirectoryProvider(Boolean entrustActive) {
        super(entrustActive);
    }

    public ActiveDirectoryProvider() {
    }

    @Override
    public UserVO validateUser(String user, String pass, UserDAO userDAO) {
        try {
            LdapContextSource contextSource = new LdapContextSource();
            contextSource.setUrl(getLdapURL());
            contextSource.setUserDn(getLdapUserDN());
            contextSource.setPassword(getLdapPass());
            contextSource.afterPropertiesSet();
            LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
            ldapTemplate.afterPropertiesSet();
            logger.debug("usuario ---->  " + user);
            Filter filter = new EqualsFilter("sAMAccountName", user);
            boolean authed = ldapTemplate.authenticate(getLdapBaseDN(), filter.encode(), pass);
            UserVO userVO = null;
            if (authed) {
                UserBO userBO = userDAO.obtenerUsuario(user);
                if (userBO != null) {
                    userVO = new UserVO(userBO.getId(), userBO.getName(), userBO.getRut(), user );
                }
            }
            if (userVO != null && userVO.getRut() != null){
                logger.info("rut    ---->  " + (userVO!=null ? userVO.getRut() : ""));
                boolean p = this.isEntrustActive(userVO.getRut());
                if (!p){
                    return null;
                }
                return userVO;
            }
        }catch (BadCredentialsException bc){
        	bc.printStackTrace();
            logger.info("error de credenciales --> " + bc.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
            logger.info("error generico --> " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<String> retreiveRoles(String user, String pass) {

        List<String> usrRole = new ArrayList<>();

        Hashtable env = new Hashtable();
        String adminName = getLdapUserDN();
        String adminPassword = getLdapPass();
        String ldapURL = getLdapURL();
        //String ldapURL = "ldap://".concat(getLdapURL());
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        try {
            DirContext ctx = new InitialLdapContext(env, null);
            SearchControls searchCtls = new SearchControls();
            String returnedAtts[] = {"sn", "mail", "cn", "samAccountName", "memberOf"};
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";
            String searchBase = getLdapBaseDN();
            int totalResults = 0;
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
            Map<String, String> roles = new HashMap<>();
            roles.put("cargaactas_bicecorp", "ROLE_ADMINISTRATOR");
            roles.put("visualizadoractas_bicecorp", "ROLE_VIEW");

            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                totalResults++;
                Attribute memberOfAttr = sr.getAttributes().get("memberof");
                if(memberOfAttr != null){
                    for(int i=0; i < memberOfAttr.size() ; i++){
                        Attributes atts = ctx.getAttributes(memberOfAttr.get(i).toString(), new String[] { "CN" });
                        Attribute att = atts.get("CN");
                        String r = att.get().toString().toLowerCase();
                        logger.info(r);
                        if (roles.containsKey(r)){
                            usrRole.add(roles.get(r));
                        }
                    }
                }
            }
            ctx.close();
            return usrRole;
        }
        catch (NamingException e) {
            logger.debug("Problem searching directory: " + e.getMessage());
        }
        return usrRole;
    }
    
    @Override
    public String changePass(String user, String pass) {
    	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    	System.setProperty("javax.net.debug", "all");
    	String userDN = "";
    	String result = "";
        Hashtable env = new Hashtable();
    	String adminName = getLdapUserDN();
    	String adminPassword = getLdapPass();
    	String ldapURL = getLdapURLCambioPass();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    	env.put(Context.SECURITY_AUTHENTICATION, "Simple");
    	env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        //env.put("com.sun.jndi.ldap.connect.pool", "true");

        try {
        	LdapContext ctx = new InitialLdapContext(env, null);
        	
        	SearchControls searchCtls = new SearchControls();
        	String returnedAtts[] = {"sn", "cn", "samAccountName"};
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";
            //String searchBase = getLdapBaseDN();
            String searchBase = getLdapBase();
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                userDN = sr.getNameInNamespace();
                System.out.println("Usuario: "+userDN);
                logger.error("Usuario: " + userDN);
            }
            //String searchBase = getLdapBaseDN();
            try {
    	    	String quotedPassword = "\"" + pass + "\"";
    	    	char unicodePwd[] = quotedPassword.toCharArray();
    	    	byte pwdArray[] = new byte[unicodePwd.length * 2];
    	    	for (int i=0; i<unicodePwd.length; i++) {
    		    	pwdArray[i*2 + 1] = (byte) (unicodePwd[i] >>> 8);
    		    	pwdArray[i*2 + 0] = (byte) (unicodePwd[i] & 0xff);
    	    	}

    	    	ModificationItem[] mods = new ModificationItem[1];
    	    	mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
    	    	new BasicAttribute("UnicodePwd", pwdArray));

    	    	ctx.modifyAttributes(userDN, mods);
    	    	result = "Contraseña modificada correctamente";
	    	}
	    	catch (Exception e) {
	    		logger.error("Error al modificar la contraseña: " + e);
	    		result = "Error al modificar la contraseña: " + e + ldapURL;
	    		e.printStackTrace();
	    	}
        }
        catch (NamingException e) {
        	logger.error("Error buscando directorio: " + e.getMessage());
        	result = "Error buscando directorio: " + e +ldapURL;
    		e.printStackTrace();
        }
        return result;
    }

}
