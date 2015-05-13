/*
 * Copyright 2013 JBoss Inc
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

package org.optaplanner.examples.nqueens.app;

import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;

import static org.junit.Assert.*;

public class NQueensAppTest {

    @Test
    public void createSolverByApi() {
        NQueensApp nQueensApp = new NQueensApp();
        Solver solver = nQueensApp.createSolverByApi();
        assertNotNull(solver);
    }

}
