package cl.bice.gestionactas.test;

import org.junit.Test;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;

/**
 * Date: 5/16/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class LdapBiceTest {

    @Test
    public void getRoles(){
        Hashtable env = new Hashtable();
        String adminName = "uid=admin,ou=system";
        String adminPassword = "sofia";
        String ldapURL = "ldap://localhost:10389";
        env.put(Context.INITIAL_CONTEXT_FACTORY,  "com.sun.jndi.ldap.LdapCtxFactory");
        // set security credentials
        //env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        // specify use of ssl
        //env.put(Context.SECURITY_PROTOCOL, "ssl");
        // connect to my domain controller
        env.put(Context.PROVIDER_URL, ldapURL);
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        try {
            // Create the initial directory context
            DirContext ctx = new InitialLdapContext(env, null);
            // Validate user
            // Create the search controls
            SearchControls searchCtls = new SearchControls();
            // Specify the attributes to return
            String returnedAtts[] = { "sn", "mail", "cn", "samAccountName","member" };
            searchCtls.setReturningAttributes(returnedAtts);
            // Specify the search scope
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            // specify the LDAP search filter
            //String searchFilter = "(&(objectClass=user)(mail=*))";
            String searchFilter = "(&(objectClass=person))";
//            String searchFilter = "(&(objectClass=person)(uid=admin))";
            //String searchFilter = "(&(&(objectClass=users)(objectCategory=group)))";
            // Specify the Base for the search
            String searchBase = "ou=groups,ou=system";
            // initialize counter to total the results
            int totalResults = 0;
            // Search for objects using the filter
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
            // Loop through the search results
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                totalResults++;
                System.out.println(">>>" + sr.getName());
                // Print out some of the attributes, catch the exception if the
                // attributes have no values
                Attributes attrs = sr.getAttributes();
                if (attrs != null) {
                    try {
                        //System.out.println("   atributos: " + attrs.getIDs());
                        //System.out.println("   surname: " 	+ attrs.get("sn").get());
                        //System.out.println("   firstname: " + attrs.get("givenName").get());
                        System.out.println("   Username: " 		+ attrs.get("samAccountName").get());
                        //System.out.println("   Member: " 		+ attrs.get("member").get());
                    }
                    catch (NullPointerException e) {
                        System.out.println("Errors listing attributes: " + e);
                    }
                }
            }
            System.out.println("Total results: " + totalResults);
            ctx.close();
        }
        catch (NamingException e) {
            System.err.println("Problem searching directory: " + e);
        }
    }
}
