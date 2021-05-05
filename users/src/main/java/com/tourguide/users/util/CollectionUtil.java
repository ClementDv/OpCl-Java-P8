package com.tourguide.users.util;

import java.util.*;

public class CollectionUtil {

    public static <T> List<T> notNullOrEmpty(Collection<T> list) {
        return list == null ? Collections.emptyList() : new ArrayList<>(list);
    }

}
