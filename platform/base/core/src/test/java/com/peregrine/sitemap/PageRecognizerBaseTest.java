package com.peregrine.sitemap;

import com.peregrine.SlingResourcesTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.peregrine.commons.util.PerConstants.JCR_PRIMARY_TYPE;
import static com.peregrine.commons.util.PerConstants.SLING_RESOURCE_TYPE;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PageRecognizerBaseTest extends SlingResourcesTest {

    private static final String PRIMARY_TYPE = "test:Page";
    private static final String CONTENT_TYPE = "test:PageContent";
    private static final String EXCLUDE_SITE_MAP_PROPERTY = "exclude";

    protected final Page candidate = new Page(page);
    private final PageRecognizerBase model;

    protected PageRecognizerBaseTest(final PageRecognizerBase model) {
        this.model = model;
    }

    public PageRecognizerBaseTest() {
        this(new PageRecognizerBase(PRIMARY_TYPE, CONTENT_TYPE, EXCLUDE_SITE_MAP_PROPERTY) {
            @Override
            protected boolean isPageImpl(final Page candidate) {
                return true;
            }
        });
    }

    @Test
    public void isPage() {
        assertFalse(model.isPage(candidate));
        parent.putProperty(JCR_PRIMARY_TYPE, model.getPagePrimaryType());
        assertFalse(model.isPage(new Page(parent)));
        page.putProperty(JCR_PRIMARY_TYPE, model.getPagePrimaryType());
        assertFalse(model.isPage(candidate));
        content.putProperty(JCR_PRIMARY_TYPE, model.getPageContentPrimaryType());
        assertFalse(model.isPage(candidate));
        page.putProperty(SLING_RESOURCE_TYPE, RESOURCE_TYPE);
        assertTrue(model.isPage(candidate));
        page.putProperty(model.getExcludeFromSiteMapPropertyName(), true);
        assertFalse(model.isPage(candidate));
    }

}