package com.example.rolin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.rolin.data.DatabaseHelper
import com.example.rolin.data.UserContract
/**
 * Actividad principal del rol Jugador que gestiona la ficha de personaje digital,
 * incluyendo datos básicos, estadísticas, atributos, barras de estado dinámicas
 * y la navegación hacia las demás actividades de la partida (Inventario, Dado, etc).
 *
 * @author MarcoAllendesG
 */
class JugadorAc : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var userEmail = ""
    private var sessionCode = ""
    private lateinit var imgAvatarPersonaje: ImageView
    private lateinit var etVidaActual: EditText
    private lateinit var etVidaMax: EditText
    private lateinit var etManaActual: EditText
    private lateinit var etManaMax: EditText
    private lateinit var etAguanteActual: EditText
    private lateinit var etAguanteMax: EditText
    private lateinit var pbVida: ProgressBar
    private lateinit var pbMana: ProgressBar
    private lateinit var pbAguante: ProgressBar

    private val tomarFotoLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            imgAvatarPersonaje.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    private val solicitarPermisoLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { esConcedido: Boolean ->
        if (esConcedido) {
            tomarFotoLauncher.launch(null)
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugador)

        imgAvatarPersonaje = findViewById(R.id.imgAvatarPersonaje)

        etVidaActual = findViewById(R.id.etVidaActual)
        etVidaMax = findViewById(R.id.etVidaMax)
        etManaActual = findViewById(R.id.etManaActual)
        etManaMax = findViewById(R.id.etManaMax)
        etAguanteActual = findViewById(R.id.etAguanteActual)
        etAguanteMax = findViewById(R.id.etAguanteMax)

        pbVida = findViewById(R.id.pbVida)
        pbMana = findViewById(R.id.pbMana)
        pbAguante = findViewById(R.id.pbAguante)

        vincularBarraYValidar(etVidaActual, etVidaMax, pbVida, "Vida")
        vincularBarraYValidar(etManaActual, etManaMax, pbMana, "Maná")
        vincularBarraYValidar(etAguanteActual, etAguanteMax, pbAguante, "Aguante")

        dbHelper = DatabaseHelper(this)
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        sessionCode = intent.getStringExtra("SESSION_CODE") ?: ""
        cargarFichaPersonaje()

        val btnDados = findViewById<Button>(R.id.btnDados)
        val btnCalculadora = findViewById<Button>(R.id.btnCalculadora)
        val btnNotas = findViewById<Button>(R.id.btnNotas)
        val btnInventario = findViewById<Button>(R.id.btnInventario)
        val btnHabilidades = findViewById<Button>(R.id.btnHabilidades)

        imgAvatarPersonaje.setOnClickListener {
            verificarPermisoYTomarFoto()
        }

        btnHabilidades.setOnClickListener {
            val intent = Intent(this, HabilidadesAc::class.java).apply {
                putExtra("USER_EMAIL", userEmail)
                putExtra("SESSION_CODE", sessionCode)
            }
            startActivity(intent)
        }

        btnDados.setOnClickListener {
            val intent = Intent(this, DadoAc::class.java)
            startActivity(intent)
        }

        btnInventario.setOnClickListener {
            val intent = Intent(this, InventarioAc::class.java).apply {
                putExtra("USER_EMAIL", userEmail)
                putExtra("SESSION_CODE", sessionCode)
            }
            startActivity(intent)
        }

        btnCalculadora.setOnClickListener {
            val intent = Intent(this, CalculadoraAc::class.java)
            startActivity(intent)
        }

        btnNotas.setOnClickListener {
            val intent = Intent(this, NotasAc::class.java).apply {
                putExtra("USER_EMAIL", userEmail)
                putExtra("SESSION_CODE", sessionCode)
            }
            startActivity(intent)
        }

    }
    /**
     * Verifica si la aplicación cuenta con permisos de cámara concedidos.
     * Si los tiene, abre la cámara para capturar el avatar; de lo contrario, pide permiso.
     */
    private fun verificarPermisoYTomarFoto() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                tomarFotoLauncher.launch(null)
            }
            else -> {
                solicitarPermisoLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    /**
     * Vincula dos campos de texto (valor actual y máximo) con una barra de progreso en tiempo real
     * además de impedir que el valor actual supere el límite máximo.
     *
     * @param etActual Campo de entrada para el valor actual del recurso.
     * @param etMax Campo de entrada para el límite máximo del recurso.
     * @param progressBar Componente visual que refleja el porcentaje del recurso.
     * @param nombreAtributo Etiqueta descriptiva utilizada en los mensajes de alerta.
     */
    private fun vincularBarraYValidar(etActual: EditText, etMax: EditText, progressBar: ProgressBar, nombreAtributo: String) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val actualStr = etActual.text.toString()
                val maxStr = etMax.text.toString()

                if (actualStr.isNotEmpty() && maxStr.isNotEmpty()) {
                    var actual = actualStr.toIntOrNull() ?: 0
                    val max = maxStr.toIntOrNull() ?: 1

                    if (actual > max) {
                        actual = max
                        etActual.setText(max.toString())
                        etActual.setSelection(etActual.text.length)
                        Toast.makeText(this@JugadorAc, "$nombreAtributo no puede superar el máximo", Toast.LENGTH_SHORT).show()
                    }

                    progressBar.max = max
                    progressBar.progress = actual
                } else {
                    progressBar.progress = 0
                }
            }
        }

        etActual.addTextChangedListener(textWatcher)
        etMax.addTextChangedListener(textWatcher)
    }
    override fun onPause() {
        super.onPause()
        guardarFichaPersonaje()
    }
    /**
     * Guarda toda la información de la ficha del personaje en la base de datos SQLite,
     * vinculando también al correo del usuario y el código de la sesión actual.
     */
    private fun guardarFichaPersonaje() {
        val db = dbHelper.writableDatabase
        val values = android.content.ContentValues().apply {
            put(UserContract.CharacterEntry.COLUMN_USER_EMAIL, userEmail)
            put(UserContract.CharacterEntry.COLUMN_SESSION_CODE, sessionCode)
            put(UserContract.CharacterEntry.COLUMN_NOMBRE, findViewById<EditText>(R.id.etNombre).text.toString())
            put(UserContract.CharacterEntry.COLUMN_RAZA, findViewById<EditText>(R.id.etRaza).text.toString())
            put(UserContract.CharacterEntry.COLUMN_CLASE, findViewById<EditText>(R.id.etClase).text.toString())
            put(UserContract.CharacterEntry.COLUMN_NIVEL, findViewById<EditText>(R.id.etNivel).text.toString())
            put(UserContract.CharacterEntry.COLUMN_DADOS, findViewById<EditText>(R.id.etDados).text.toString())
            // Estadísticas
            put(UserContract.CharacterEntry.COLUMN_STR, findViewById<EditText>(R.id.etFuerza).text.toString())
            put(UserContract.CharacterEntry.COLUMN_INT, findViewById<EditText>(R.id.etInteligencia).text.toString())
            put(UserContract.CharacterEntry.COLUMN_BLO, findViewById<EditText>(R.id.etBloqueo).text.toString())
            put(UserContract.CharacterEntry.COLUMN_CON, findViewById<EditText>(R.id.etContrataque).text.toString())
            put(UserContract.CharacterEntry.COLUMN_DEX, findViewById<EditText>(R.id.etDestreza).text.toString())
            put(UserContract.CharacterEntry.COLUMN_SAB, findViewById<EditText>(R.id.etSabiduría).text.toString())
            // Atributos
            put(UserContract.CharacterEntry.COLUMN_VOL, findViewById<EditText>(R.id.etVoluntad).text.toString())
            put(UserContract.CharacterEntry.COLUMN_SIG, findViewById<EditText>(R.id.etSigilo).text.toString())
            put(UserContract.CharacterEntry.COLUMN_INTI, findViewById<EditText>(R.id.etIntimidacion).text.toString())
            put(UserContract.CharacterEntry.COLUMN_PER, findViewById<EditText>(R.id.etPercepcion).text.toString())
            put(UserContract.CharacterEntry.COLUMN_SUE, findViewById<EditText>(R.id.etSuerte).text.toString())
            put(UserContract.CharacterEntry.COLUMN_CHA, findViewById<EditText>(R.id.etCarisma).text.toString())
            // Estado
            put(UserContract.CharacterEntry.COLUMN_VIDA_ACT, etVidaActual.text.toString())
            put(UserContract.CharacterEntry.COLUMN_VIDA_MAX, etVidaMax.text.toString())
            put(UserContract.CharacterEntry.COLUMN_MANA_ACT, etManaActual.text.toString())
            put(UserContract.CharacterEntry.COLUMN_MANA_MAX, etManaMax.text.toString())
            put(UserContract.CharacterEntry.COLUMN_AGUANTE_ACT, etAguanteActual.text.toString())
            put(UserContract.CharacterEntry.COLUMN_AGUANTE_MAX, etAguanteMax.text.toString())
        }

        val updated = db.update(
            UserContract.CharacterEntry.TABLE_NAME,
            values,
            "${UserContract.CharacterEntry.COLUMN_USER_EMAIL} = ? AND ${UserContract.CharacterEntry.COLUMN_SESSION_CODE} = ?",
            arrayOf(userEmail, sessionCode)
        )
        if (updated == 0) {
            db.insert(UserContract.CharacterEntry.TABLE_NAME, null, values)
        }
    }
    /**
     * Consulta la base de datos local para recuperar y rellenar los campos de la ficha
     * previamente guardada para el usuario y sala activos.
     */
    private fun cargarFichaPersonaje() {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            UserContract.CharacterEntry.TABLE_NAME,
            null,
            "${UserContract.CharacterEntry.COLUMN_USER_EMAIL} = ? AND ${UserContract.CharacterEntry.COLUMN_SESSION_CODE} = ?",
            arrayOf(userEmail, sessionCode),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            fun getSafeText(columnName: String): String {
                val index = cursor.getColumnIndex(columnName)
                return if (index != -1) cursor.getString(index) ?: "" else ""
            }

            findViewById<EditText>(R.id.etNombre).setText(getSafeText(UserContract.CharacterEntry.COLUMN_NOMBRE))
            findViewById<EditText>(R.id.etRaza).setText(getSafeText(UserContract.CharacterEntry.COLUMN_RAZA))
            findViewById<EditText>(R.id.etClase).setText(getSafeText(UserContract.CharacterEntry.COLUMN_CLASE))
            findViewById<EditText>(R.id.etNivel).setText(getSafeText(UserContract.CharacterEntry.COLUMN_NIVEL))
            findViewById<EditText>(R.id.etDados).setText(getSafeText(UserContract.CharacterEntry.COLUMN_DADOS))

            findViewById<EditText>(R.id.etFuerza).setText(getSafeText(UserContract.CharacterEntry.COLUMN_STR))
            findViewById<EditText>(R.id.etInteligencia).setText(getSafeText(UserContract.CharacterEntry.COLUMN_INT))
            findViewById<EditText>(R.id.etBloqueo).setText(getSafeText(UserContract.CharacterEntry.COLUMN_BLO))
            findViewById<EditText>(R.id.etContrataque).setText(getSafeText(UserContract.CharacterEntry.COLUMN_CON))
            findViewById<EditText>(R.id.etDestreza).setText(getSafeText(UserContract.CharacterEntry.COLUMN_DEX))
            findViewById<EditText>(R.id.etSabiduría).setText(getSafeText(UserContract.CharacterEntry.COLUMN_SAB))

            findViewById<EditText>(R.id.etVoluntad).setText(getSafeText(UserContract.CharacterEntry.COLUMN_VOL))
            findViewById<EditText>(R.id.etSigilo).setText(getSafeText(UserContract.CharacterEntry.COLUMN_SIG))
            findViewById<EditText>(R.id.etIntimidacion).setText(getSafeText(UserContract.CharacterEntry.COLUMN_INTI))
            findViewById<EditText>(R.id.etPercepcion).setText(getSafeText(UserContract.CharacterEntry.COLUMN_PER))
            findViewById<EditText>(R.id.etSuerte).setText(getSafeText(UserContract.CharacterEntry.COLUMN_SUE))
            findViewById<EditText>(R.id.etCarisma).setText(getSafeText(UserContract.CharacterEntry.COLUMN_CHA))

            etVidaMax.setText(getSafeText(UserContract.CharacterEntry.COLUMN_VIDA_MAX))
            etVidaActual.setText(getSafeText(UserContract.CharacterEntry.COLUMN_VIDA_ACT))
            etManaMax.setText(getSafeText(UserContract.CharacterEntry.COLUMN_MANA_MAX))
            etManaActual.setText(getSafeText(UserContract.CharacterEntry.COLUMN_MANA_ACT))
            etAguanteMax.setText(getSafeText(UserContract.CharacterEntry.COLUMN_AGUANTE_MAX))
            etAguanteActual.setText(getSafeText(UserContract.CharacterEntry.COLUMN_AGUANTE_ACT))
        }
        cursor.close()
    }
}