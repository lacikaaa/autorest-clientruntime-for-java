/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.rest.v3.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Replaces the header with the value of its target. The value specified here
 * replaces headers specified statically in the {@link Headers}.
 * If the parameter this annotation is attached to is a Map type, then this will
 * be treated as a header collection. In that case each of the entries in the
 * argument's map will be individual header values that use the value of this
 * annotation as a prefix to their key/header name.
 *
 * Example 1:
 *   {@code @PUT("{functionId}")}
 *   {@code Mono<RestResponse<Headers, Body>>} createOrReplace(@PathParam("functionId", encoded = true) String functionId, @BodyParam FunctionInner function, @HeaderParam("If-Match") String ifMatch);
 *
 *   "If-Match: user passed value" will show up as one of the headers.
 *
 * Example 2:
 *   {@code @}GET("subscriptions/{subscriptionId}/providers/Microsoft.ServiceBus/namespaces")
 *   {@code Mono<RestResponse<Headers, Body>>} list(@Path("subscriptionId") String subscriptionId, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);
 *
 *   "accept-language" generated by the HTTP client will be overwritten by the user passed value.
 *
 * Example 3:
 *   {@code @GET("subscriptions/{subscriptionId}/providers/Microsoft.ServiceBus/namespaces")}
 *   {@code Mono<RestResponse<Headers, Body>>} list(@Path("subscriptionId") String subscriptionId, @Header("Authorization") String token);
 *
 *   The token parameter will replace the effect of any credentials in the HTTP pipeline.
 *
 * Example 4:
 *   {@code @PUT("{containerName}/{blob}")}
 *   {@code @ExpectedResponses({200})}
 *   {@code Mono<RestResponse<BlobSetMetadataHeaders, Void>> setMetadata(@HostParam("url") String url, @QueryParam("timeout") Integer timeout, @HeaderParam("x-ms-meta-") Map<String, String> metadata, @HeaderParam("x-ms-lease-id") String leaseId, @HeaderParam("If-Modified-Since") String ifModifiedSince, @HeaderParam("If-Unmodified-Since") String ifUnmodifiedSince, @HeaderParam("If-Match") String ifMatches, @HeaderParam("If-None-Match") String ifNoneMatch, @HeaderParam("x-ms-version") String version, @HeaderParam("x-ms-client-request-id") String requestId, @QueryParam("comp") String comp);}
 *
 *   The metadata parameter will be expanded out so that each entry becomes "x-ms-meta-{@literal <entryKey>}: {@literal <entryValue>}".
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface HeaderParam {
    /**
     * The name of the variable in the endpoint uri template which will be replaced with the value
     * of the parameter annotated with this annotation.
     * @return The name of the variable in the endpoint uri template which will be replaced with the
     *      value of the parameter annotated with this annotation.
     */
    String value();
}