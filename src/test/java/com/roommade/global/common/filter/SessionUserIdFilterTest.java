package com.roommade.global.common.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

class SessionUserIdFilterTest {

    private final SessionUserIdFilter filter = new SessionUserIdFilter();

    @Test
    void injectsUserIdHeaderWhenSessionHoldsLongUserId() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 7L);
        request.setSession(session);
        AtomicReference<ServletRequest> forwarded = new AtomicReference<>();

        filter.doFilter(request, new MockHttpServletResponse(), capturingChain(forwarded));

        assertThat(((javax.servlet.http.HttpServletRequest) forwarded.get()).getHeader("X-User-Id"))
                .isEqualTo("7");
        assertThat(((javax.servlet.http.HttpServletRequest) forwarded.get()).getHeader("x-user-id"))
                .isEqualTo("7");
    }

    @Test
    void passesThroughUnchangedWhenNoSessionExists() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-User-Id", "999");
        AtomicReference<ServletRequest> forwarded = new AtomicReference<>();

        filter.doFilter(request, new MockHttpServletResponse(), capturingChain(forwarded));

        assertThat(forwarded.get()).isSameAs(request);
        assertThat(((javax.servlet.http.HttpServletRequest) forwarded.get()).getHeader("X-User-Id"))
                .isEqualTo("999");
    }

    @Test
    void passesThroughUnchangedWhenSessionHasNoUserIdAttribute() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        AtomicReference<ServletRequest> forwarded = new AtomicReference<>();

        filter.doFilter(request, new MockHttpServletResponse(), capturingChain(forwarded));

        assertThat(forwarded.get()).isSameAs(request);
    }

    @Test
    void passesThroughUnchangedWhenSessionAttributeIsNotALong() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", "not-a-long");
        request.setSession(session);
        AtomicReference<ServletRequest> forwarded = new AtomicReference<>();

        filter.doFilter(request, new MockHttpServletResponse(), capturingChain(forwarded));

        assertThat(forwarded.get()).isSameAs(request);
    }

    private FilterChain capturingChain(AtomicReference<ServletRequest> forwarded) {
        return (ServletRequest req, ServletResponse res) -> forwarded.set(req);
    }
}
