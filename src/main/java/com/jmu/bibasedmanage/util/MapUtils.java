package com.jmu.bibasedmanage.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ljc on 2017/12/28.
 */
public class MapUtils extends org.apache.commons.collections.MapUtils{

    @SuppressWarnings("unchecked")
    public static Map<String, Object> genMap(Object... array) {
        return putAll(new HashMap<String, Object>(), array);
    }

}
