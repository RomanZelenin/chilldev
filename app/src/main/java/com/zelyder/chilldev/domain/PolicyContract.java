// Copyright 2021 Yandex LLC. All rights reserved.

package com.zelyder.chilldev.domain;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.NonNull;

public class PolicyContract {

    public static final String AUTHORITY = "com.yandex.tv.policy";
    public static final String POLICY_SETTINGS_PATH = "policy_settings";

    public static final String NAME_POLICY_LEVEL_INDEX = "policy_level_index";
    public static final String NAME_SEARCH_MODE_INDEX = "search_mode_index";
    public static final String NAME_KIDS_AGE_LIMIT = "kids_age_limit";
    public static final String NAME_LIMITED_AGE_LIMIT = "limited_age_limit";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VALUE = "value";

    @NonNull
    public static Uri buildPolicySettingsUri() {
        return new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .path(POLICY_SETTINGS_PATH)
                .build();
    }

    @NonNull
    public static Uri buildPolicySettingsUriByName(String name) {
        return Uri.withAppendedPath(buildPolicySettingsUri(), name);
    }
}
