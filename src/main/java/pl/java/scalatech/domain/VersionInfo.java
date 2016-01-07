/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.java.scalatech.domain;

import java.util.Properties;

import lombok.Data;

@Data
public class VersionInfo {
    private String tags;
    private String branch;
    private String dirty;
    private String remoteOriginUrl;
    private String commitId;
    private String commitIdAbbrev;
    private String describe;
    private String describeShort;
    private String commitUserName;
    private String commitUserEmail;
    private String commitMessageFull;
    private String commitMessageShort;
    private String commitTime;
    private String closestTagName;
    private String closestTagCommitCount;
    private String buildUserName;
    private String buildUserEmail;
    private String buildTime;
    private String buildHost;
    private String buildVersion;

    public VersionInfo(Properties properties)
    {
      this.tags = properties.get("git.tags") + "";
      this.branch = properties.get("git.branch")+ "";
      this.dirty = properties.get("git.dirty")+ "";
      this.remoteOriginUrl = properties.get("git.remote.origin.url")+ "";

      this.commitId = properties.get("git.commit.id")+ "";
      this.commitIdAbbrev = properties.get("git.commit.id.abbrev")+ "";
      this.describe = properties.get("git.commit.id.describe")+ "";
      this.describeShort = properties.get("git.commit.id.describe-short")+ "";
      this.commitUserName = properties.get("git.commit.user.name")+ "";
      this.commitUserEmail = properties.get("git.commit.user.email")+ "";
      this.commitMessageFull = properties.get("git.commit.message.full")+ "";
      this.commitMessageShort = properties.get("git.commit.message.short")+ "";
      this.commitTime = properties.get("git.commit.time")+ "";
      this.closestTagName = properties.get("git.closest.tag.name")+ "";
      this.closestTagCommitCount = properties.get("git.closest.tag.commit.count")+ "";

      this.buildUserName = properties.get("git.build.user.name")+ "";
      this.buildUserEmail = properties.get("git.build.user.email")+ "";
      this.buildTime = properties.get("git.build.time")+ "";
      this.buildHost = properties.get("git.build.host")+ "";
      this.buildVersion = properties.get("git.build.version")+ "";

     // this.jenkinsBuildNumber = properties.get("jenkins.build.number")+ "";
    //  this.jenkinsBuildTimeStamp = properties.get("jenkins.build.time")+ "";

    }
}
