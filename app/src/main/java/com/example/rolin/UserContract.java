package com.example.rolin;

import android.provider.BaseColumns;

public final class UserContract {
    private UserContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuarios";
        public static final String COLUMN_EMAIL = "correo";
        public static final String COLUMN_PASSWORD = "contraseña";
    }
    public static class SessionEntry implements BaseColumns {
        public static final String TABLE_NAME = "sesiones";
        public static final String COLUMN_CODE = "codigo_sala";
        public static final String COLUMN_DM_EMAIL = "dm_correo";
    }
    public static class CharacterEntry implements BaseColumns {
        public static final String TABLE_NAME = "personajes";
        public static final String COLUMN_USER_EMAIL = "usuario_correo";
        public static final String COLUMN_SESSION_CODE = "codigo_sala";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_RAZA = "raza";
        public static final String COLUMN_CLASE = "clase";
        public static final String COLUMN_NIVEL = "nivel";
        public static final String COLUMN_DADOS = "dados";

        public static final String COLUMN_STR = "fuerza";
        public static final String COLUMN_INT = "inteligencia";
        public static final String COLUMN_BLO = "bloqueo";
        public static final String COLUMN_CON = "contrataque";
        public static final String COLUMN_DEX = "destreza";
        public static final String COLUMN_SAB = "sabiduria";

        public static final String COLUMN_VOL = "voluntad";
        public static final String COLUMN_SIG = "sigilo";
        public static final String COLUMN_INTI = "intimidacion";
        public static final String COLUMN_PER = "percepcion";
        public static final String COLUMN_SUE = "suerte";
        public static final String COLUMN_CHA = "carisma";

        public static final String COLUMN_VIDA_ACT = "vida_act";
        public static final String COLUMN_VIDA_MAX = "vida_max";
        public static final String COLUMN_MANA_ACT = "mana_act";
        public static final String COLUMN_MANA_MAX = "mana_max";
        public static final String COLUMN_AGUANTE_ACT = "aguante_act";
        public static final String COLUMN_AGUANTE_MAX = "aguante_max";

        public static final String COLUMN_NOTAS = "notas";
    }

    public static class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventario";
        public static final String COLUMN_ITEM_UUID = "item_uuid";
        public static final String COLUMN_USER_EMAIL = "usuario_correo";
        public static final String COLUMN_SESSION_CODE = "codigo_sala";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_CANTIDAD = "cantidad";
    }

    public static class SkillEntry implements BaseColumns {
        public static final String TABLE_NAME = "habilidades";
        public static final String COLUMN_USER_EMAIL = "usuario_correo";
        public static final String COLUMN_SESSION_CODE = "codigo_sala";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_COSTE = "coste";
        public static final String COLUMN_DESCRIPCION = "descripcion";
    }
}
