package com.example.rolin.data;

import android.provider.BaseColumns;

/**
 * Clase contrato utilizada para contener las constantes públicas
 * de la base de datos SQLite para My DnD Pal.
 *
 * @author MarcoAllendesG
 * @version 1.0
 */
public final class UserContract {
    private UserContract() {}

    /**
     * De aquí en adelante se define la estructura que ocupará cada tabla
     */

    /**
     *Tabla que contiene los datos de inicio de sesión
     */
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuarios";
        public static final String COLUMN_EMAIL = "correo";
        public static final String COLUMN_PASSWORD = "contraseña";
    }
    /**
     *Tabla de creación de sala del Dungeon Master
     */
    public static class SessionEntry implements BaseColumns {
        public static final String TABLE_NAME = "sesiones";
        public static final String COLUMN_CODE = "codigo_sala";
        public static final String COLUMN_DM_EMAIL = "dm_correo";
    }
    /**
     *Aqui empieza la hoja del jugador, la cual posee
     * bastantes atributos, empezando por los datos básicos
     */
    public static class CharacterEntry implements BaseColumns {
        public static final String TABLE_NAME = "personajes";
        public static final String COLUMN_USER_EMAIL = "usuario_correo";
        public static final String COLUMN_SESSION_CODE = "codigo_sala";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_RAZA = "raza";
        public static final String COLUMN_CLASE = "clase";
        public static final String COLUMN_NIVEL = "nivel";
        public static final String COLUMN_DADOS = "dados";
        /**
         *Estadisticas base
         */
        public static final String COLUMN_STR = "fuerza";
        public static final String COLUMN_INT = "inteligencia";
        public static final String COLUMN_BLO = "bloqueo";
        public static final String COLUMN_CON = "contrataque";
        public static final String COLUMN_DEX = "destreza";
        public static final String COLUMN_SAB = "sabiduria";
        /**
         *Puntos de aptitud
         */
        public static final String COLUMN_VOL = "voluntad";
        public static final String COLUMN_SIG = "sigilo";
        public static final String COLUMN_INTI = "intimidacion";
        public static final String COLUMN_PER = "percepcion";
        public static final String COLUMN_SUE = "suerte";
        public static final String COLUMN_CHA = "carisma";
        /**
         *Estadisticas maximas y actuales de la sesión
         */
        public static final String COLUMN_VIDA_ACT = "vida_act";
        public static final String COLUMN_VIDA_MAX = "vida_max";
        public static final String COLUMN_MANA_ACT = "mana_act";
        public static final String COLUMN_MANA_MAX = "mana_max";
        public static final String COLUMN_AGUANTE_ACT = "aguante_act";
        public static final String COLUMN_AGUANTE_MAX = "aguante_max";
        /**
         *Por último la sección de notas hechas por el jugador
         */
        public static final String COLUMN_NOTAS = "notas";
    }
    /**
     *Tabla de inventario con los objetos registrados por el jugador
     */
    public static class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventario";
        public static final String COLUMN_ITEM_UUID = "item_uuid";
        public static final String COLUMN_USER_EMAIL = "usuario_correo";
        public static final String COLUMN_SESSION_CODE = "codigo_sala";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_CANTIDAD = "cantidad";
    }
    /**
     *Tabla de habilidades de personaje creadas por el jugador
     */
    public static class SkillEntry implements BaseColumns {
        public static final String TABLE_NAME = "habilidades";
        public static final String COLUMN_USER_EMAIL = "usuario_correo";
        public static final String COLUMN_SESSION_CODE = "codigo_sala";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_COSTE = "coste";
        public static final String COLUMN_DESCRIPCION = "descripcion";
    }
}
