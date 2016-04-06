/*
 * Copyright 2015, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.api.gax.grpc;

import com.google.api.gax.core.RetrySettings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import io.grpc.Status;

import java.util.Set;

/**
 * A settings class to configure an API method.
 */
public class ApiCallSettings {

  private final ImmutableSet<Status.Code> retryableCodes;
  private final RetrySettings retrySettings;

  public ImmutableSet<Status.Code> getRetryableCodes() {
    return retryableCodes;
  }

  public RetrySettings getRetrySettings() {
    return retrySettings;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return new Builder(this);
  }

  protected ApiCallSettings(ImmutableSet<Status.Code> retryableCodes,
                            RetrySettings retrySettings) {
    this.retryableCodes = ImmutableSet.<Status.Code>copyOf(retryableCodes);
    this.retrySettings = retrySettings;
  }

  public static class Builder {

    private Set<Status.Code> retryableCodes;
    private RetrySettings.Builder retrySettingsBuilder;

    public Builder() {
      retryableCodes = Sets.newHashSet();
      retrySettingsBuilder = RetrySettings.newBuilder();
    }

    public Builder(ApiCallSettings apiCallSettings) {
      setRetryableCodes(apiCallSettings.retryableCodes);
      setRetrySettingsBuilder(apiCallSettings.getRetrySettings().toBuilder());
    }

    public Builder setRetryableCodes(Set<Status.Code> retryableCodes) {
      this.retryableCodes = Sets.newHashSet(retryableCodes);
      return this;
    }

    public Builder setRetryableCodes(Status.Code... codes) {
      this.setRetryableCodes(Sets.newHashSet(codes));
      return this;
    }

    public Builder setRetrySettingsBuilder(RetrySettings.Builder retrySettingsBuilder) {
      this.retrySettingsBuilder = retrySettingsBuilder;
      return this;
    }

    public Set<Status.Code> getRetryableCodes() {
      return this.retryableCodes;
    }

    public RetrySettings.Builder getRetrySettingsBuilder() {
      return this.retrySettingsBuilder;
    }

    public ApiCallSettings build() {
      return new ApiCallSettings(ImmutableSet.<Status.Code>copyOf(retryableCodes),
                                 retrySettingsBuilder.build());
    }
  }
}