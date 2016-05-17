package com.mobileforming.javaeightretro.net;

/**
 * @author neil
 *         5/12/16
 *         ©2016 Hilton Worldwide Inc. All rights reserved.
 */
public interface JokeResolver {
    static String getBaseUrl() {
        return "http://api.icndb.com/jokes";
    }

    default String getPath() {
        return "/random";
    }

    default String getJokeURL() {
        return getBaseUrl() + getPath();
    }
}
