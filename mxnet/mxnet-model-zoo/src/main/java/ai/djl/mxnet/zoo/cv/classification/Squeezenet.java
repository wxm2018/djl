/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.mxnet.zoo.cv.classification;

import ai.djl.repository.Repository;

/**
 * Model loader for Squeezenet Symbolic models.
 *
 * <p>The model was trained on Gluon and loaded in DJL in MXNet Symbol Block. See <a
 * href="https://arxiv.org/pdf/1602.07360.pdf">Squeezenet</a>.
 *
 * @see ai.djl.mxnet.nn.MxSymbolBlock
 */
public class Squeezenet extends ImageClassificationModelLoader {

    private static final String ARTIFACT_ID = "squeezenet";
    private static final String VERSION = "0.0.1";

    public Squeezenet(Repository repository) {
        super(repository, ARTIFACT_ID, VERSION);
    }
}
