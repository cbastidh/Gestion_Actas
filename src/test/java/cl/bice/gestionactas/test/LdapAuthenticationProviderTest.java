package cl.bice.gestionactas.test;

import org.junit.Test;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;


public class LdapAuthenticationProviderTest  {

    @Test
    public void authenticate() throws Exception, UsernameNotFoundException {
        LdapContextSource contextSource = new LdapContextSource();
//        contextSource.setUrl("ldap://localhost:10389");
        contextSource.setUrl("ldap://10.110.3.17:389");
        //contextSource.setBase("dc=neoptera,dc=cl");
//        contextSource.setUserDn("uid=admin,ou=system");
        contextSource.setUserDn("CN=Adminwls,CN=Users,DC=des,DC=local");
        contextSource.setPassword("Welcome1");
//        contextSource.setPassword("sofia");
        contextSource.afterPropertiesSet();




//        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
//        ldapTemplate.afterPropertiesSet();

        Filter filter = new EqualsFilter("uid", "marcelo");

//        ldapTemplate.search()


//        boolean authed = ldapTemplate.authenticate("", filter.encode(), "sofia");

        FilterBasedLdapUserSearch search = new FilterBasedLdapUserSearch("", "(&(objectClass=user)(sAMAccountName={0}))", contextSource);
        search.setSearchSubtree(true);

        LdapUserDetailsService rememberMeUserDetailsService = new LdapUserDetailsService(search);

//        DefaultLdapAuthoritiesPopulator populator = new DefaultLdapAuthoritiesPopulator(contextSource, "ou=groups,ou=system");
//        populator.setGroupSearchFilter("ou=groups,ou=system");
//        Set<GrantedAuthority> groupMembershipRoles = populator.getGroupMembershipRoles("", "uid=admin,ou=system");


        UserDetails userDetails = rememberMeUserDetailsService.loadUserByUsername("csepulveday");


        System.out.println("tst");

// Display the results.
//        System.out.println("Authenticated: " + authed);

//        Authentication authentication = new UsernamePasswordAuthenticationToken(user,pass,null);
    }
}
