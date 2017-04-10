package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.NewsReader;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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

    private final IncomingInfo incomingInfo0 = new IncomingInfo("info0",SubsciptionType.A);
    private final IncomingInfo incomingInfo1 = new IncomingInfo("info1",SubsciptionType.B);
    private final IncomingInfo incomingInfo2 = new IncomingInfo("info2",SubsciptionType.C);
    private final IncomingInfo incomingInfo3 = new IncomingInfo("info3",SubsciptionType.NONE);

    @Before
    public void setUp() {
        Configuration configuration = new Configuration();

        mockStatic(ConfigurationLoader.class);
        ConfigurationLoader mockConfigurationLoader = mock(ConfigurationLoader.class);
        when(mockConfigurationLoader.getInstance()).thenReturn(mockConfigurationLoader);
        when(mockConfigurationLoader.loadConfiguration()).thenReturn(configuration);




        String readerTypeValue = "testedReader";
        Whitebox.setInternalState(configuration, "readerType", readerTypeValue);
        when(mockConfigurationLoader.loadConfiguration()).thenReturn(configuration);

        incomingNews.add(incomingInfo0);
        incomingNews.add(incomingInfo1);
        incomingNews.add(incomingInfo2);
        incomingNews.add(incomingInfo3);

        NewsReader mockNewsReader = mock(NewsReader.class);
        when(mockNewsReader.read()).thenReturn(incomingNews);

        mockStatic(NewsReaderFactory.class);
        when(NewsReaderFactory.getReader(readerTypeValue)).thenReturn(mockNewsReader);
    }

    @Test
    public void shouldHaveOnePublicInfo() {
        NewsLoader newsLoader = new NewsLoader();
        PublishableNews publishableNews = newsLoader.loadNews();

        List<String> publicContent = (List<String>) Whitebox.getInternalState(publishableNews, "publicContent");

        assertThat(publicContent.size(), is(equalTo(1)));
    }

}
