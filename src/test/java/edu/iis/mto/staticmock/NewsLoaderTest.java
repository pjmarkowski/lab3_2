package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.NewsReader;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Piotr on 28.03.2017.
 */
//edu.iis.mto.staticmock.ConfigurationLoader oraz edu.iis.mto.staticmock.NewsReaderFactory.
@RunWith(PowerMockRunner.class)
@PrepareForTest(ConfigurationLoader.class)
public class NewsLoaderTest {

    private IncomingNews incomingNews = new IncomingNews();

    @Before
    public void setUp() {
        Configuration configuration = new Configuration();

        mockStatic(ConfigurationLoader.class);
        ConfigurationLoader mockConfigurationLoader = mock(ConfigurationLoader.class);
        when(mockConfigurationLoader.getInstance()).thenReturn(mockConfigurationLoader);
        when(mockConfigurationLoader.loadConfiguration()).thenReturn(configuration);

        NewsReader mockedNewsReader = mock(NewsReader.class);
        when(mockedNewsReader.read()).thenReturn(incomingNews);
    }

    @Test
    public void test() {
    }


}
