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

import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Unroll

@Unroll
class JmhWithShadowPluginSpec extends AbstractFuncSpec {

    def "Run #language benchmarks that are packaged with Shadow plugin"() {

        given:
        usingSample("${language.toLowerCase()}-shadow-project")

        when:
        def result = build("-S", "clean", "jmh")

        then:
        result.task(":jmh").outcome == TaskOutcome.SUCCESS
        file("build/reports/benchmarks.csv").text.contains(language + 'Benchmark.sqrtBenchmark')

        where:
        language << ['Java', 'Scala']
    }
}
