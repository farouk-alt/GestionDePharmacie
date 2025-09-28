package commande.web.constants;

import java.util.Set;

public final class CommandeStatus {
    public static final String ST_CREATED  = "CREATED";
    public static final String ST_PENDING  = "PENDING";
    public static final String ST_ACCEPTED = "ACCEPTED";
    public static final String ST_REFUSED  = "REFUSED";
    public static final String ST_CANCELED = "CANCELED";

    private static final Set<String> CANCELABLE = Set.of(ST_CREATED, ST_PENDING);
    private static final Set<String> EDITABLE   = Set.of(ST_CREATED);

    public static boolean isCancelable(String s) {
        return s != null && CANCELABLE.contains(s.trim().toUpperCase());
    }
    public static boolean isEditable(String s) {
        return s != null && EDITABLE.contains(s.trim().toUpperCase());
    }

    private CommandeStatus() {}
}

