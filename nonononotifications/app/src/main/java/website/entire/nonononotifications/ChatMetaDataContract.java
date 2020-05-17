package website.entire.nonononotifications;

import android.provider.BaseColumns;

public final class ChatMetaDataContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ChatMetaDataContract() {}

    /* Inner class that defines the table contents */
    public static class Uid implements BaseColumns {
        public static final String TABLE_NAME = "fbdata";
        public static final String COLUMN_NAME_TITLE = "uid";
    }

    public static class Timestamp implements BaseColumns {
        public static final String TABLE_NAME = "fbdata";
        public static final String COLUMN_NAME_TITLE = "timestamp";
    }

    public static class FirstName implements BaseColumns {
        public static final String TABLE_NAME = "fbdata";
        public static final String COLUMN_NAME_TITLE = "first_name";
    }

    public static class ImageLink implements BaseColumns {
        public static final String TABLE_NAME = "fbdata";
        public static final String COLUMN_NAME_TITLE = "image_link";
    }
}
