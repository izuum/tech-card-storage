package com.example.tech_card_storage.service.config;

import java.util.Arrays;
import java.util.List;

public final class CardsFileConfig {

    public static final List<String> CARDS_FILE_TYPE_LIST = Arrays.asList("application/pdf");

    public static final long CARDS_FILE_ALLOWED_SIZE = 10 * 1024 * 1024;

}
