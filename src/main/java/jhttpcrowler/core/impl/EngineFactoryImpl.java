package jhttpcrowler.core.impl;

import jhttpcrowler.core.DynamicEngine;
import jhttpcrowler.core.Engine;
import jhttpcrowler.core.Queue;
import jhttpcrowler.core.EngineFactory;
import jhttpcrowler.utils.properties.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * TODO: add description
 *
 * @author Sergey Prilukin
 *         Date: 15.09.11 17:05
 */
public final class EngineFactoryImpl implements EngineFactory {
    private static final Log log = LogFactory.getLog(EngineFactoryImpl.class);

    public static final String PROPERTIES_FILE = "jhttpcrowler.properties";
    public static final String PROPERTIES_ENGINE_KEY_PREFIX = "engine.";
    public static final String PROPERTIES_DEFAULT_ENGINE_KEY = PROPERTIES_ENGINE_KEY_PREFIX + "default";
    public static final String PROPERTIES_PROPERTY_QUEUE_KEY = "queue";

    private Properties engines;
    private Queue queue;

    private static EngineFactory instance = new EngineFactoryImpl();

    public static EngineFactory getInstance() {
        return instance;
    }

    private EngineFactoryImpl() {
        engines = PropertiesUtil.load(PROPERTIES_FILE, null);

        String queueClassString = engines.getProperty(PROPERTIES_PROPERTY_QUEUE_KEY);
        try {
            Class queueClass = Class.forName(queueClassString);
            queue = (Queue)queueClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Engine getEngine(String engineName) throws Exception {
        String defaultClass = engines.getProperty(PROPERTIES_DEFAULT_ENGINE_KEY);
        String engineClass = engines.getProperty(PROPERTIES_ENGINE_KEY_PREFIX + engineName, defaultClass);

        BaseEngine engine;

        Class clazz = Class.forName(engineClass);
        engine = (BaseEngine)clazz.newInstance();
        engine.setQueue(queue);
        if (DynamicEngine.class.isAssignableFrom(clazz)) {
            ((DynamicEngine)engine).setEngineName(engineName);
        }
        return engine;
    }
}
