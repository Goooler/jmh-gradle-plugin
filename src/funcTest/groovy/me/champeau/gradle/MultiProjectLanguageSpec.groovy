/*
 * Copyright 2014-2017 the original author or authors.
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
package me.champeau.gradle

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class MultiProjectLanguageSpec extends AbstractFuncSpec {

    def "Should not execute JMH tests from different projects concurrently"() {

        given:
        usingSample('java-multi-project')

        when:
        def result = build("jmh")

        then:
        result.task(":jmh").outcome == SUCCESS
        file("build/reports/benchmarks.csv").text.contains("JavaBenchmark.sqrtBenchmark")

        and:
        result.task(":subproject:jmh").outcome == SUCCESS
        file("subproject/build/reports/benchmarks.csv").text.contains("JavaMultiBenchmark.sqrtBenchmark")
    }
}
