package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.NewsReader;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Piotr on 28.03.2017.
 */
//edu.iis.mto.staticmock.ConfigurationLoader oraz edu.iis.mto.staticmock.NewsReaderFactory.
@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class})
public class NewsLoaderTest {

    private IncomingNews incomingNews = new IncomingNews();

    @Before
    public void setUp() {
        Configuration configuration = new Configuration();

        mockStatic(ConfigurationLoader.class);
        ConfigurationLoader mockConfigurationLoader = mock(ConfigurationLoader.class);
        when(mockConfigurationLoader.getInstance()).thenReturn(mockConfigurationLoader);
        when(mockConfigurationLoader.loadConfiguration()).thenReturn(configuration);

        NewsReader mockNewsReader = mock(NewsReader.class);
        when(mockNewsReader.read()).thenReturn(incomingNews);


        String readerTypeValue = "testedReader";
        Whitebox.setInternalState(configuration, "readerType", readerTypeValue);
        when(mockConfigurationLoader.loadConfiguration()).thenReturn(configuration);

        mockStatic(NewsReaderFactory.class);
        when(NewsReaderFactory.getReader(readerTypeValue)).thenReturn(mockNewsReader);
    }

    @Test
    public void test() {
    }


}
