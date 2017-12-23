package com.simplesync;

import java.io.Serializable;
import java.util.UUID;

public class FullName implements Serializable {

    UUID shareID;
    String name;

    FullName(UUID shareID, String name) {
        this.shareID = shareID;
        this.name = name;
    }
}
