package jhttpcrowler.core.plugin;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;

/**
 * Implementation of {@link InitialContextFactory} to be able to test using JNDI
 *
 * @author Sergey Prilukin
 */
public final class TestInitialContextFactory implements InitialContextFactory {

    public static Context context = new TestContext();

    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        return context;
    }

    static class TestContext implements Context {

        private Hashtable env = new Hashtable();
        private Hashtable<Name, Object> bindings = new Hashtable<Name, Object>();

        public Object addToEnvironment(String propName, Object propVal) throws NamingException {
            return null;
        }

        public void bind(Name name, Object obj) throws NamingException {
            bindings.put(name, obj);
        }

        public void bind(String name, Object obj) throws NamingException {
            bind(new CompositeName(name), obj);
        }

        public void close() throws NamingException {
        }

        public Name composeName(Name name, Name prefix) throws NamingException {
            return null;
        }

        public String composeName(String name, String prefix) throws NamingException {
            return null;
        }

        public Context createSubcontext(Name name) throws NamingException {
            return null;
        }

        public Context createSubcontext(String name) throws NamingException {
            return null;
        }

        public void destroySubcontext(Name name) throws NamingException {
        }

        public void destroySubcontext(String name) throws NamingException {
        }

        public Hashtable<?, ?> getEnvironment() throws NamingException {
            return env;
        }

        public String getNameInNamespace() throws NamingException {
            return null;
        }

        public NameParser getNameParser(Name name) throws NamingException {
            return null;
        }

        public NameParser getNameParser(String name) throws NamingException {
            return null;
        }

        public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
            return null;
        }

        public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
            return null;
        }

        public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
            return null;
        }

        public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
            return null;
        }

        public Object lookup(Name name) throws NamingException {
            return bindings.get(name);
        }

        public Object lookup(String name) throws NamingException {
            return lookup(new CompositeName(name));
        }

        public Object lookupLink(Name name) throws NamingException {
            return null;
        }

        public Object lookupLink(String name) throws NamingException {
            return null;
        }

        public void rebind(Name name, Object obj) throws NamingException {
            bind(name, obj);
        }

        public void rebind(String name, Object obj) throws NamingException {
            bind(name, obj);
        }

        public Object removeFromEnvironment(String propName) throws NamingException {
            return env.remove(propName);
        }

        public void rename(Name oldName, Name newName) throws NamingException {
            if (bindings.contains(oldName)) {
                bindings.put(newName, bindings.remove(oldName));
            }
        }

        public void rename(String oldName, String newName) throws NamingException {
            rename(new CompositeName(oldName), new CompositeName(newName));
        }

        public void unbind(Name name) throws NamingException {
            bindings.remove(name);
        }

        public void unbind(String name) throws NamingException {
            unbind(new CompositeName(name));
        }

    }
}
