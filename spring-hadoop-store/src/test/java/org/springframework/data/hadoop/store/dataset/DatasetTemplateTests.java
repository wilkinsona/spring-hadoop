/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.hadoop.store.dataset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.test.tests.Assume;
import org.springframework.data.hadoop.test.tests.Version;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.kitesdk.data.DatasetRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DatasetTemplateTests extends AbstractDatasetTemplateTests {

	@Test
	public void testReadSavedPojoWithNullValues() {
		//Kite SDK currently uses some Hadoop 2.0 only methods
		Assume.hadoopVersion(Version.HADOOP2X);
		datasetOperations.write(records);
		TestPojo pojo4 = new TestPojo();
		pojo4.setId(33L);
		pojo4.setName(null);
		pojo4.setBirthDate(null);
		datasetOperations.write(Collections.singletonList(pojo4));
		Collection<TestPojo> results = datasetOperations.read(TestPojo.class);
		assertEquals(3, results.size());
		List<TestPojo> sorted = new ArrayList<TestPojo>(results);
		Collections.sort(sorted);
		assertTrue(sorted.get(0).getName().equals("Sven"));
		assertTrue(sorted.get(0).getId().equals(22L));
		assertNull(sorted.get(1).getName());
		assertTrue(sorted.get(1).getId().equals(33L));
		assertTrue(sorted.get(2).getName().equals("Nisse"));
		assertTrue(sorted.get(2).getId().equals(48L));
	}

}
