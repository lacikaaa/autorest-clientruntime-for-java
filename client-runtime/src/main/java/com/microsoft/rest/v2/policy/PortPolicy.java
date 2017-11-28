/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.rest.v2.policy;


import com.microsoft.rest.v2.http.HttpRequest;
import com.microsoft.rest.v2.http.HttpResponse;
import com.microsoft.rest.v2.http.UrlBuilder;
import rx.Single;

/**
 * A RequestPolicy that adds the provided port to each HttpRequest.
 */
public class PortPolicy implements RequestPolicy {
    private final RequestPolicy nextPolicy;
    private final int port;
    private final boolean overwrite;

    PortPolicy(RequestPolicy nextPolicy, int port, boolean overwrite) {
        this.nextPolicy = nextPolicy;
        this.port = port;
        this.overwrite = overwrite;
    }

    @Override
    public Single<HttpResponse> sendAsync(HttpRequest request) {
        final UrlBuilder urlBuilder = UrlBuilder.parse(request.url());
        if (overwrite || urlBuilder.port() == null) {
            request.withUrl(urlBuilder.withPort(port).toString());
        }
        return nextPolicy.sendAsync(request);
    }

    /**
     * A RequestPolicy.Factory class that creates PortPolicy objects.
     */
    public static class Factory implements RequestPolicy.Factory {
        private final int port;
        private final boolean overwrite;

        /**
         * Create a new PortPolicy.Factory object.
         * @param port The port to set on every HttpRequest.
         */
        public Factory(int port) {
            this(port, true);
        }

        /**
         * Create a new PortPolicy.Factory object.
         * @param port The port to set.
         * @param overwrite Whether or not to overwrite a HttpRequest's port if it already has one.
         */
        public Factory(int port, boolean overwrite) {
            this.port = port;
            this.overwrite = overwrite;
        }

        @Override
        public RequestPolicy create(RequestPolicy next, Options options) {
            return new PortPolicy(next, port, overwrite);
        }
    }
}