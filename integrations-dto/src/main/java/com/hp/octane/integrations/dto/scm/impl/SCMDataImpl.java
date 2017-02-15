/*
 *     Copyright 2017 Hewlett-Packard Development Company, L.P.
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.hp.octane.integrations.dto.scm.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hp.octane.integrations.dto.scm.SCMCommit;
import com.hp.octane.integrations.dto.scm.SCMData;
import com.hp.octane.integrations.dto.scm.SCMRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gullery
 * Date: 12/10/14
 * Time: 22:33
 * To change this template use File | Settings | File Templates.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class SCMDataImpl implements SCMData {
	private SCMRepository repository;
	private String builtRevId;
	private List<SCMCommit> commits;

	public SCMRepository getRepository() {
		return repository;
	}

	public SCMData setRepository(SCMRepository repository) {
		this.repository = repository;
		return this;
	}

	public String getBuiltRevId() {
		return builtRevId;
	}

	public SCMData setBuiltRevId(String builtRevId) {
		this.builtRevId = builtRevId;
		return this;
	}

	public List<SCMCommit> getCommits() {
		return commits;
	}

	public SCMData setCommits(List<SCMCommit> commits) {
		this.commits = commits;
		return this;
	}
}
