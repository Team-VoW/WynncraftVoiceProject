/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.api;

import java.io.IOException;

public final class ApiHttpException extends IOException {
    private final int statusCode;
    private final String responseBody;

    ApiHttpException(int statusCode, String responseBody) {
        super("Voices of Wynn API returned HTTP " + statusCode);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int statusCode() {
        return statusCode;
    }

    public String responseBody() {
        return responseBody;
    }
}
