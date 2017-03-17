/*
 * Copyright 2016 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.java.scalatech.tools;

import static org.apache.http.client.fluent.Request.Get;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import lombok.SneakyThrows;

public final class HttpContentDownloader {

    private HttpContentDownloader() {
      throw new AssertionError();
    }
    @SneakyThrows(value={ClientProtocolException.class,IOException.class})
    public static String fetchAsString(String url){
        return Get(url).execute().returnContent().asString();
    }
}