/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
package ai.djl.modality.audio;

import ai.djl.ndarray.NDArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * {@code AudioFactory} contains audio creation mechanism on top of different platforms like PC and
 * Android. System will choose appropriate Factory based on the supported audio type.
 */
public abstract class AudioFactory {

    private static final Logger logger = LoggerFactory.getLogger(AudioFactory.class);

    private static final String[] FACTORIES = {
        "ai.djl.audio.FFmpegAudioFactory", "ai.djl.modality.audio.SampledAudioFactory"
    };

    private static AudioFactory factory = newInstance();

    private static AudioFactory newInstance() {
        for (String f : FACTORIES) {
            try {
                Class<? extends AudioFactory> clazz =
                        Class.forName(f).asSubclass(AudioFactory.class);
                return clazz.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                logger.trace("", e);
            }
        }
        throw new IllegalStateException("Failed to create AudioFactory!");
    }

    /**
     * Gets new instance of Audio factory from the provided factory implementation.
     *
     * @return {@link AudioFactory}
     */
    public static AudioFactory getInstance() {
        return factory;
    }

    /**
     * Returns {@link Audio} from file.
     *
     * @param path the path to the audio
     * @return {@link Audio}
     * @throws IOException Audio not found or not readable
     */
    public abstract Audio fromFile(Path path) throws IOException;

    /**
     * Returns {@link Audio} from URL.
     *
     * @param url the URL to load from
     * @return {@link Audio}
     * @throws IOException URL is not valid.
     */
    public Audio fromUrl(URL url) throws IOException {
        try (InputStream is = url.openStream()) {
            return fromInputStream(is);
        }
    }

    /**
     * Returns {@link Audio} from URL.
     *
     * @param url the String represent URL to load from
     * @return {@link Audio}
     * @throws IOException URL is not valid.
     */
    public Audio fromUrl(String url) throws IOException {
        URI uri = URI.create(url);
        if (uri.isAbsolute()) {
            return fromUrl(uri.toURL());
        }
        return fromFile(Paths.get(url));
    }

    /**
     * Returns {@link Audio} from {@link InputStream}.
     *
     * @param is {@link InputStream}
     * @return {@link Audio}
     * @throws IOException image cannot be read from input stream.
     */
    public abstract Audio fromInputStream(InputStream is) throws IOException;

    /**
     * Returns {@link Audio} from raw data.
     *
     * @param data the raw data in float array form.
     * @return {@link Audio}
     */
    public abstract Audio fromData(float[] data);

    /**
     * Returns {@link Audio} from {@link NDArray}.
     *
     * @param array the NDArray with CHW format
     * @return {@link Audio}
     */
    public abstract Audio fromNDArray(NDArray array);
}
